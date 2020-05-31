package com.neu.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.webapp.model.Cart;
import com.neu.webapp.repository.BookDao;
import com.neu.webapp.repository.CartDao;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	CartDao cartDao;
	@Autowired
	BookService bookService;

	@Override
	public void AddBooktoCart(int cart_id,int book_id) throws Exception {
		// TODO Auto-generated method stub
		if(bookService.isBookAvailable(book_id)) {
		cartDao.addBooktoCart(cart_id, book_id);
		}
		else
		{
			throw new Exception("Book not available");
		}
	}

	@Override
	public void RemovefromCart(int cart_id,int book_id) {
		// TODO Auto-generated method stub
		cartDao.removeBookFromCart(cart_id, book_id);
	}

	@Override
	public void createCart(Cart cart) {
		cartDao.save(cart);
		
	}

}
