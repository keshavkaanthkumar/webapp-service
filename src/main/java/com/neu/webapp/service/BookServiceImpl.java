package com.neu.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neu.webapp.model.Book;
import com.neu.webapp.repository.BookDao;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookDao bookdao;
	@Override
	public Book AddBook(Book book) {
		// TODO Auto-generated method stub
		
		return bookdao.save(book);
	}

	@Override
	public void DeleteBook(int book_id) throws Exception {
		// TODO Auto-generated method stub
		if(bookdao.existsById(book_id)){
			bookdao.deleteById(book_id);
		}
		else
		{
			throw new Exception("Book not found");
		}
	}

	@Override
	public Book UpdateBook(Book book) throws Exception {
		// TODO Auto-generated method stub
		if(bookdao.existsById(book.getId())){
			bookdao.save(book);
		}
		else
		{
			throw new Exception("Book not found");
		}
		return book;
	}
	@Override
	public List<Book> GetAllBooks() throws Exception {
		// TODO Auto-generated method stub
	
		return (List<Book>) bookdao.findAll();
	}
	@Override
	public boolean isBookAvailable(int book_id) throws Exception {
		Book book=bookdao.findById(book_id).get();
		if(book.getQuantity()>0) {
			return true;
		}
		else
		{
			return false;
		}
	
		
	}

}
