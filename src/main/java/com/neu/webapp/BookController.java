package com.neu.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neu.webapp.model.Book;
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
		
		book.setSeller(userExtractor.getUserFromtoken(token).getEmail());
		bookService.AddBook(book);
		return ResponseEntity.ok(book);
	
		
	}
	@RequestMapping(value = "/cart/book/{book_id}", method = RequestMethod.POST)
	public ResponseEntity<?> saveBooktoCart(@PathVariable("book_id") int book_id,@RequestHeader("Authorization") String token) throws Exception {
		User user=userExtractor.getUserFromtoken(token);
		cartService.AddBooktoCart(user.getCart().getId(), book_id);
		return ResponseEntity.ok("Added successfully");
	
		
	}
	@RequestMapping(value = "/cart/book/{book_id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeBookFromCart(@PathVariable("book_id") int book_id,@RequestHeader("Authorization") String token) throws Exception {
		User user=userExtractor.getUserFromtoken(token);
		cartService.RemovefromCart(user.getCart().getId(), book_id);
		return ResponseEntity.ok("Removed successfully");
	
		
	}
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ResponseEntity<?> getallBooks() throws Exception {
		
		return ResponseEntity.ok(bookService.GetAllBooks());
	
		
	}
	

}
