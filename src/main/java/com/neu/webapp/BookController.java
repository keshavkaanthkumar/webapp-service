package com.neu.webapp;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.AmazonS3Client;
import com.neu.webapp.model.Book;
import com.neu.webapp.model.BookReqResp;
import com.neu.webapp.model.CartBook;
import com.neu.webapp.model.Image;
import com.neu.webapp.model.Message;
import com.neu.webapp.model.User;
import com.neu.webapp.model.UserDTO;
import com.neu.webapp.service.AmazonS3ClientService;
import com.neu.webapp.service.BookService;
import com.neu.webapp.service.CartService;
import com.neu.webapp.service.UserExtractor;

@RestController
public class BookController {
	
	@Autowired
	BookService bookService;
	@Autowired
	UserExtractor userExtractor;
	@Autowired
	CartService cartService;
	@Autowired
	AmazonS3ClientService amazons3client;
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public ResponseEntity<?> saveBook(@RequestBody BookReqResp bookreq,@RequestHeader("Authorization") String token) throws Exception {
		Book book=bookreq.getBook();
		try {
		//amazons3client.uploadImagesToS3Bucket((String[])bookreq.getImage(), book);
		book.setSeller(userExtractor.getUserFromtoken(token).getEmail());
		HashMap<String,String> imagekeymap=new HashMap<>();
		Set<Image> imagekeyset=new HashSet<Image>();
		for(String image:bookreq.getImage()) {
			UUID uuid = UUID.randomUUID();
			Image imagekey=new Image(uuid.toString());
			imagekey.setBook(book);
			imagekeymap.put(image, imagekey.getName());
		    imagekeyset.add(imagekey);
			
			
			
		}
		book.setImages(imagekeyset);
		
		Book bookres=bookService.AddBook(book);
		amazons3client.uploadImagesToS3Bucket(imagekeymap, bookres);
	}
	catch(Exception ex) {
		return ResponseEntity.badRequest().body(ex.getMessage().toString());
	}
	return ResponseEntity.ok(book);
		
	}
	@RequestMapping(value = "/book", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBook(@RequestBody BookReqResp bookreq,@RequestHeader("Authorization") String token) throws Exception {
		
		//book.setSeller(userExtractor.getUserFromtoken(token).getEmail());
		Book book=bookreq.getBook();
		try {
			HashMap<String,String> imagekeymap=new HashMap
					<>();
			Set<Image> imagekeyset=new HashSet<Image>();
			for(String image:bookreq.getImage()) {
				UUID uuid = UUID.randomUUID();
				Image imagekey=new Image(uuid.toString());
				imagekey.setBook(book);
				imagekeymap.put(image, imagekey.getName());
			    imagekeyset.add(imagekey);
				
				
				
			}
			book.setImages(imagekeyset);
	    
		Book bookres=bookService.UpdateBook(book);
		amazons3client.uploadImagesToS3Bucket(imagekeymap, bookres);
	}
	catch(Exception ex) {
		return ResponseEntity.badRequest().body(ex.getMessage().toString());
	}
		return ResponseEntity.ok(book);
	
		
	}
	@RequestMapping(value = "/book/{book_id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBook(@PathVariable("book_id") int book_id,@RequestHeader("Authorization") String token) throws Exception {
		
		
		Book book=bookService.DeleteBook(book_id);
		amazons3client.deleteImages(book.getImages());
		return ResponseEntity.ok(book);
	
		
	}
	@RequestMapping(value = "/cart/book/{book_id}/{quantity}", method = RequestMethod.POST)
	public ResponseEntity<?> saveBooktoCart(@PathVariable("book_id") int book_id,@PathVariable("quantity") int quantity,@RequestHeader("Authorization") String token) throws Exception {
		User user=userExtractor.getUserFromtoken(token);
		try {
		cartService.AddBooktoCart(user.getCart().getId(), book_id,quantity);
		}
		catch(Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage().toString());
		}
		return ResponseEntity.ok(new Message("Book added to cart"));
	
		
	}
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public ResponseEntity<?> getCart(@RequestHeader("Authorization") String token) throws Exception {
		User user=userExtractor.getUserFromtoken(token);
		List<CartBook>books;
		try {
		books=cartService.getCart(user.getCart().getId());
		}
		catch(Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage().toString());
		}
		return ResponseEntity.ok(books);
	
		
	}
	@RequestMapping(value = "/cart/book/{book_id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeBookFromCart(@PathVariable("book_id") int book_id,@RequestHeader("Authorization") String token) throws Exception {
		User user=userExtractor.getUserFromtoken(token);
		try {
		cartService.RemovefromCart(user.getCart().getId(), book_id);
		}
		catch(Exception ex) {
			return ResponseEntity.badRequest().body(ex.toString());
		}
		return ResponseEntity.ok(new Message("Book removed from cart"));
	
		
	}
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ResponseEntity<?> getallBooks() throws Exception {
		
		return ResponseEntity.ok(bookService.GetAllBooks());
	
		
	}
	@RequestMapping(value = "/book/{book_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBook(@PathVariable("book_id") int book_id) throws Exception {
		Book book=bookService.GetBook(book_id);
		List<String>images=amazons3client.downloadImagesFromS3Bucket(book);
		BookReqResp bookResp=new BookReqResp();
		String[] imageArray = new String[images.size()];
	      images.toArray(imageArray);
		bookResp.setBook(book);
		bookResp.setImage(imageArray);
		return ResponseEntity.ok(bookResp);
	
		
	}
	

}