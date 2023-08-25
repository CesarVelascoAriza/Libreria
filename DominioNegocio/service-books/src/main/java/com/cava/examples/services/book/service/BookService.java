package com.cava.examples.services.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookService<E> {
	
	public Iterable<E> findAll();
	public List<E> findByTitle(String name);
	public Page<E> findAll(Pageable pageable);
	public Optional<E> findById(Long id);
	public E save(E entity);
	public void deleteById(Long id );

}
