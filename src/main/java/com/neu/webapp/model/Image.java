package com.neu.webapp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@JsonIgnoreProperties(value= "book")
public class Image {
@Id
private String name;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "book_id")
private Book book;

public Image(String name){
	this.name=name;
}
public Image() {}
public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
public Book getBook() {
	return book;
}
public void setBook(Book book) {
	this.book = book;
}



}
