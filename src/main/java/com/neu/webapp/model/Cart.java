package com.neu.webapp.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@JsonIgnoreProperties(value= {"user","cartSet"})

public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cart_id;
	@OneToOne(fetch = FetchType.EAGER,cascade= CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "", referencedColumnName = "email")
	private User user;
//	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
//	@JoinTable(name = "Cart_book", joinColumns = {
//			@JoinColumn(referencedColumnName = "cart_id") }, inverseJoinColumns = {
//					@JoinColumn(referencedColumnName = "book_id") })
//	private Set<Book> books;
	@JsonBackReference
	 @OneToMany(mappedBy = "cart")
	    private Set<CartBook> cartSet = new HashSet<CartBook>();
	
	public Set<CartBook> getCartSet() {
		return cartSet;
	}

	public void setCartSet(Set<CartBook> cartSet) {
		this.cartSet = cartSet;
	}

	public int getId() {
		return cart_id;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
//	public Set<Book> getBooks() {
//		return books;
//	}
//	public void setBooks(Set<Book> books) {
//		this.books = books;
//	}
	

}
