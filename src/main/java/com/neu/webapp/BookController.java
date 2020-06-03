package com.neu.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neu.webapp.model.Book;
import com.neu.webapp.model.CartBook;
import com.neu.webapp.model.Message;
import com.neu.webapp.model.User;
import com.neu.webapp.model.UserDTO;
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
	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public ResponseEntity<?> saveBook(@RequestBody Book book,@RequestHeader("Authorization") String token) throws Exception {
	try {
		book.setSeller(userExtractor.getUserFromtoken(token).getEmail());
		bookService.AddBook(book);
	}
	catch(Exception ex) {
		return ResponseEntity.badRequest().body(ex.getMessage().toString());
	}
	return ResponseEntity.ok(book);
		
	}
	@RequestMapping(value = "/book", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBook(@RequestBody Book book,@RequestHeader("Authorization") String token) throws Exception {
		
		//book.setSeller(userExtractor.getUserFromtoken(token).getEmail());
		try {
		bookService.UpdateBook(book);
	}
	catch(Exception ex) {
		return ResponseEntity.badRequest().body(ex.getMessage().toString());
	}
		return ResponseEntity.ok(book);
	
		
	}
	@RequestMapping(value = "/book/{book_id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBook(@PathVariable("book_id") int book_id,@RequestHeader("Authorization") String token) throws Exception {
		
		
		Book book=bookService.DeleteBook(book_id);
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
		
		return ResponseEntity.ok(bookService.GetBook(book_id));
	
		
	}
	

}
