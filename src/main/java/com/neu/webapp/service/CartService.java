package com.neu.webapp.service;

import java.util.List;

import com.neu.webapp.model.Book;
import com.neu.webapp.model.Cart;
import com.neu.webapp.model.CartBook;

public interface CartService {
public void AddBooktoCart(int cart_id,int book_id,int quantity) throws Exception;
public void RemovefromCart(int cart_id,int book_id);
public void createCart(Cart cart);
public List<CartBook> getCart(int id);
}
