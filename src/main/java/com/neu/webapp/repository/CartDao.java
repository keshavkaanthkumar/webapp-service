package com.neu.webapp.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neu.webapp.model.Book;
import com.neu.webapp.model.Cart;
import com.neu.webapp.model.CartBook;
import com.neu.webapp.model.User;

public interface CartDao extends CrudRepository<Cart, Integer> {

//	@Modifying
//	@Query(value = "INSERT INTO cart_book VALUES (:cart_id,:book_id,:quantity)", nativeQuery = true)  
//	@Transactional
//  void addBooktoCart(@Param("cart_id") int cart_id, @Param("book_id") int book_id);
//	@Modifying
//	@Query(value = "Delete FROM cart_book where cart_cart_id=:cart_id AND books_book_id=:book_id", nativeQuery = true)  
//	@Transactional
//    void removeBookFromCart(@Param("cart_id") int cart_id, @Param("book_id") int book_id);
//	@Query(value = "select books_book_id from cart_book where cart_cart_id = :cart_id", nativeQuery = true)  
//	ArrayList<Integer>  getCart(@Param("cart_id") int cart_id);
}
