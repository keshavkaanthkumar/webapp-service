package com.neu.webapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.neu.webapp.model.Book;
import com.neu.webapp.repository.BookDao;
import com.timgroup.statsd.StatsDClient;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookDao bookdao;
	@Autowired
	AmazonS3ClientService awsclient;
	@Autowired
	private StatsDClient statsDClient;
	private final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
	@Override
	public Book AddBook(Book book) throws Exception {
		// TODO Auto-generated method stub
		if (book.getQuantity() > 999 || book.getQuantity() < 0) {
			throw new Exception("Quantity cannot be more than 999 or less than 0");
		}

		if (book.getPrice() < 0.01 || book.getPrice() > 9999.99) {
			throw new Exception("Price cannot be more than 9999.99 or less than 0.01");
		}
		if (book.getPublication_date() == null) {
			throw new Exception("publication date cannot be empty");
		}
		if (book.getISBN().trim().isEmpty() || book.getTitle().trim().isEmpty()) {
			throw new Exception("Please Enter all details");
		}
		long startTime = System.currentTimeMillis();
		Book bookres = bookdao.save(book);
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		statsDClient.recordExecutionTime("Save book query time", duration);
        LOGGER.info("Book saved");
        LOGGER.info("Add book query time:" +duration);
		return bookres;
	}

	@Override
	public Book DeleteBook(int book_id) throws Exception {
		// TODO Auto-generated method stub
		Book book = bookdao.findById(book_id).get();
		book.setAvailable(false);
		long startTime = System.currentTimeMillis();
		bookdao.save(book);
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		statsDClient.recordExecutionTime("Delete book query time", duration);
		return book;
	}

	@Override
	public Book UpdateBook(Book book) throws Exception {
		// TODO Auto-generated method stub
		if (bookdao.existsById(book.getBook_id())) {
			if (book.getQuantity() > 999 || book.getQuantity() < 0) {
				throw new Exception("Quantity cannot be more than 999 or less than 0");
			}
			if (book.getPrice() < 0.01 || book.getPrice() > 9999.99) {
				throw new Exception("Price cannot be more than 9999.99 or less than 0.01");
			}
			if (book.getPublication_date() == null) {
				throw new Exception("publication date cannot be empty");
			}
			if (book.getISBN().trim().isEmpty() || book.getTitle().trim().isEmpty()) {
				throw new Exception("Please Enter all details");
			}

			awsclient.deleteImages(bookdao.findById(book.getBook_id()).get().getImages());
			bookdao.save(book);
		} else {
			throw new Exception("Book not found");
		}
		return book;
	}

	@Override
	public List<Book> GetAllBooks() throws Exception {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		List<Book> books = bookdao.getAllBooks();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		statsDClient.recordExecutionTime("Get book query time", duration);
		List<Book> availableBooks = new ArrayList<>();
		for (Book book : books) {
			if (book.isAvailable()) {
				availableBooks.add(book);
			}
		}
		return availableBooks;
	}

	@Override
	public boolean isBookAvailable(int book_id) throws Exception {
		Book book = bookdao.findById(book_id).get();
		if (book.getQuantity() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Book GetBook(int book_id) throws Exception {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		Book book = bookdao.findById(book_id).get();
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		statsDClient.recordExecutionTime("Get book query time", duration);
		if (book.isAvailable()) {
			return book;
		} else {
			throw new Exception("Book not found");
		}

	}

}
