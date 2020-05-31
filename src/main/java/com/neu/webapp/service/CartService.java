package com.neu.webapp.service;

import com.neu.webapp.model.Book;
import com.neu.webapp.model.Cart;

public interface CartService {
public void AddBooktoCart(int cart_id,int book_id) throws Exception;
public void RemovefromCart(int cart_id,int book_id);
public void createCart(Cart cart);
}
