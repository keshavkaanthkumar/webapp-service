package com.neu.webapp.service;

import java.util.List;

import com.neu.webapp.model.Book;

public interface BookService {
public Book AddBook(Book book) throws Exception;
public Book DeleteBook(int  book_id) throws Exception;
public Book UpdateBook(Book book) throws Exception;
List<Book> GetAllBooks() throws Exception;
boolean isBookAvailable(int book_id) throws Exception;
Book GetBook(int book_id) throws Exception;
}
