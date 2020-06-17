package com.neu.webapp.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.neu.webapp.model.Book;
import com.neu.webapp.model.Image;

@Service
public class AmazonS3ClientServiceImpl implements AmazonS3ClientService{

	   @Autowired
	    private AmazonS3 amazonS3;
	    @Value("${aws.s3.bucket}")
	    private String bucketName;



	@Async
	public void uploadFileToS3Bucket(String fileName, File file, String bucketName,Book book) {
        PutObjectRequest request = new PutObjectRequest(bucketName,fileName , file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.addUserMetadata("book_id", String.valueOf(book.getBook_id()));
        request.setMetadata(metadata);
        this.amazonS3.putObject(request);
		
	}
	
	public S3Object downloadFileFromS3Bucket(String filename, String bucketname) {
		S3Object obj=this.amazonS3.getObject(bucketname, filename);
		return obj;
	}
	@Async
	public void deleteObjectFromS3Bucket(String filename) {
		this.amazonS3.deleteObject(new DeleteObjectRequest(bucketName, filename));
	}

    @Override
	public void uploadImagesToS3Bucket(Map<String,String> images, Book book) {
    	
		Iterator hmIterator = images.entrySet().iterator(); 
	     while (hmIterator.hasNext()) { 
	            Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
	            File file=blobtoimage(mapElement.getKey().toString());
			    uploadFileToS3Bucket(mapElement.getValue().toString(), file, bucketName,book);
		
	        }
	}
    
	public File blobtoimage(String blob) {
		String base64Data = blob.split(",")[1];

				byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
				ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
				BufferedImage image=null;
				try {
					image = ImageIO.read(bis);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				File outputFile = new File("output.png");
				try {
					ImageIO.write(image, "png", outputFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return outputFile;  
	}
	@Override
	public List<String> downloadImagesFromS3Bucket(Book book) {
		Set<Image> images=book.getImages();
		List<String> imageList=new ArrayList<>();
		for(Image image:images) {
			InputStream is =	downloadFileFromS3Bucket(image.getName(), bucketName).getObjectContent();
			try {
				byte[] bytes = IOUtils.toByteArray(is);
				StringBuilder st=new StringBuilder();
				st.append("data:image/png;base64,");
				st.append(Base64.getEncoder().encodeToString(bytes));
				//imageList.add(StreamUtils.copyToString(is, StandardCharsets.UTF_8));
				imageList.add(st.toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return imageList;
		
	}
	@Override
	public void deleteImages(Set<Image> images) {
		for(Image image: images) {
			this.deleteObjectFromS3Bucket(image.getName());
		}
		
	}
	
   
}
