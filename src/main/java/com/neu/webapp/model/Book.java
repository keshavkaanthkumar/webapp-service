package com.neu.webapp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value= {"cartSet"})
public class Book {	
	Book(){
		this.isAvailable=true;
	}
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int book_id;
@Column(nullable = false)
private String ISBN;
@JsonBackReference
@OneToMany(mappedBy = "book")
private Set<CartBook> cartSet = new HashSet<CartBook>();
@Column(nullable = false)
private String title;
@Column
//@Temporal(TemporalType.TIMESTAMP)
private Date publication_date;
@Column(updatable = false)
@Temporal(TemporalType.TIMESTAMP)
@CreatedDate
private Date created_date;

@Column
@Temporal(TemporalType.TIMESTAMP)
@LastModifiedDate
private Date updated_date;
//@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
//@JoinTable(name = "Book_Authors", joinColumns = {
//		@JoinColumn(referencedColumnName = "book_id") }, inverseJoinColumns = {
//				@JoinColumn(referencedColumnName = "name") })
@Column
private String authors;
@Column(nullable = false)
private double price;
@OneToMany(mappedBy = "book",cascade = CascadeType.ALL,
orphanRemoval = true)
private Set<Image> images=new HashSet<Image>();
private String seller;
@Min(0)
@Max(999)
@Column(nullable = false)
private int quantity;
@Column
private boolean isAvailable;

public Set<Image> getImages() {
	return images;
}

public void setImages(Set<Image> images) {
	this.images = images;
}

public Set<CartBook> getCartSet() {
	return cartSet;
}

public void setCartSet(Set<CartBook> cartSet) {
	this.cartSet = cartSet;
}

public boolean isAvailable() {
	return isAvailable;
}

public void setAvailable(boolean isAvailable) {
	this.isAvailable = isAvailable;
}

public String getAuthors() {
	return authors;
}

public void setAuthors(String authors) {
	this.authors = authors;
}

public String getSeller() {
	return seller;
}

public void setSeller(String seller) {
	this.seller = seller;
}

public int getBook_id() {
	return book_id;
}

public void setBook_id(int book_id) {
	this.book_id = book_id;
}

public String getISBN() {
	return ISBN;
}
public void setISBN(String iSBN) {
	ISBN = iSBN;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public Date getPublication_date() {
	return publication_date;
}
public void setPublication_date(Date publication_date) {
	this.publication_date = publication_date;
}
public Date getCreated_date() {
	return created_date;
}
public void setCreated_date(Date created_date) {
	this.created_date = created_date;
}
public Date getUpdated_date() {
	return updated_date;
}
public void setUpdated_date(Date updated_date) {
	this.updated_date = updated_date;
}
//public Set<Author> getAuthors() {
//	return authors;
//}
//public void setAuthors(Set<Author> authors) {
//	authors = authors;
//}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}



}
