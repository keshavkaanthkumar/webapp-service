package com.neu.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.webapp.model.Book;
import com.neu.webapp.model.Cart;
import com.neu.webapp.model.CartBook;
import com.neu.webapp.repository.BookDao;
import com.neu.webapp.repository.CartBookDao;
import com.neu.webapp.repository.CartDao;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	CartDao cartDao;
	@Autowired
	CartBookDao cartbookDao;
	@Autowired
	BookDao bookDao;
	@Autowired
	BookService bookService;

	@Override
	public void AddBooktoCart(int cart_id,int book_id,int quantity) throws Exception {
		// TODO Auto-generated method stub
		
		if(bookService.isBookAvailable(book_id)) {
			CartBook cartBook=new CartBook();
			
			Book book=bookService.GetBook(book_id);
					if(book.getQuantity()<quantity) {
						throw new Exception("Required quantity not available");
					}
			Cart cart=cartDao.findById(cart_id).get();
					if(book.getSeller().equals(cart.getUser().getEmail())) {
						throw new Exception("Cannot buy a book sold by you");
					}
			if(cartbookDao.checkIfBookInExists(cart_id, book_id)>0) {
				throw new Exception("Book already in cart");
			}
			cartBook.setBook(book);
			cartBook.setCart(cart);
			cartBook.setQuantity(quantity);
			cartbookDao.save(cartBook);
		}
		else
		{
			throw new Exception("Book not available");
		}
	}

	@Override
	public void RemovefromCart(int cart_id,int book_id) {
		// TODO Auto-generated method stub
		cartbookDao.removeBookFromCart(cart_id, book_id);
	}


	@Override
	public void createCart(Cart cart) {
		cartDao.save(cart);
		
	}

	@Override
	public List<CartBook> getCart(int cart_id) {
		// TODO Auto-generated method stub
		List<CartBook> books= cartbookDao.getCart(cart_id);
	//	List<Book> books=(List<Book>)bookDao.findAllById(bookids);
		return books;
	}

}
