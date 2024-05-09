package com.cava.examples.services.book.controller;


import com.cava.examples.common.entitis.Book;
import com.cava.examples.controller.CommonController;
import com.cava.examples.services.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BookController extends CommonController<Book, BookService> {


	@PutMapping("/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id , @RequestBody Book book){
		Optional<Book> bookOptinal = service.findById(id);
		if (!bookOptinal.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		bookOptinal.map(b-> {
			b.setTitle(book.getTitle());
			b.setSubTitle(book.getSubTitle());
			b.setPrice(book.getPrice());
			b.setImage(book.getImage());
			b.setDatePublication(book.getDatePublication());
			b.setCategoria(book.getCategoria());
			return b;
		}).orElseThrow();

		return ResponseEntity.status(HttpStatus.OK).body(service.save(bookOptinal.get()));
	}

}
