package com.neu.webapp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class BookReqResp {


private Book book;
private String[] image;
public Book getBook() {
	return book;
}
public void setBook(Book book) {
	this.book = book;
}
public String[] getImage() {
	return image;
}
public void setImage(String[] image) {
	this.image = image;
}


}
