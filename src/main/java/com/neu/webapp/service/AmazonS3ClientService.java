package com.neu.webapp.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.neu.webapp.model.Book;

public interface AmazonS3ClientService
{

	

	void uploadImagesToS3Bucket(Map<String,String> images, Book book);

	List<String> downloadImagesFromS3Bucket(Book book);

}
