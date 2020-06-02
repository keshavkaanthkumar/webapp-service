package com.neu.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_book")
public class CartBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cart_book_id;
	 @ManyToOne
	    @JoinColumn(name = "cart_id")
	    private Cart cart;

	    @ManyToOne
	    @JoinColumn(name = "book_id")
	    private Book book;

	    @Column
	    private int quantity;

		public Cart getCart() {
			return cart;
		}

		public void setCart(Cart cart) {
			this.cart = cart;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	    
}
