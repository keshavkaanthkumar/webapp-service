package com.neu.webapp.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.neu.webapp.model.Book;
import com.neu.webapp.model.Image;

public interface AmazonS3ClientService
{

	

	void uploadImagesToS3Bucket(Map<String,String> images, Book book);

	List<String> downloadImagesFromS3Bucket(Book book);

	void deleteImages(Set<Image> set);

}
