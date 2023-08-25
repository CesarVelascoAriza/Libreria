package com.cava.examples.services.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cava.examples.services.book.models.Book;
import com.cava.examples.services.book.repostory.LibroRepository;
@Service
public class BookServiceImp implements BookService<Book>  {

	@Autowired
	private LibroRepository repository;
	
	@Override
	public Iterable<Book> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<Book> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAll(pageable);
	}

	@Override
	public Optional<Book> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public Book save(Book book) {
		// TODO Auto-generated method stub
		return repository.save(book);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Book> findByTitle(String name) {
		// TODO Auto-generated method stub
		return repository.findByTitle(name);
	}

}
