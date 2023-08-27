package com.cava.examples.services.book.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cava.examples.services.book.models.Book;
import com.cava.examples.services.book.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService<Book> bookService;

	@GetMapping
	public ResponseEntity<?> getAllBooks(){
		/*List<Book> lisBooks = (List<Book>) bookService.findAll();
		if(lisBooks.isEmpty())
			return ResponseEntity.noContent().build();
		*/
		return ResponseEntity.ok().body(bookService.findAll());

	}
	
	@GetMapping("/searchname")
	public ResponseEntity<?> getByName(@RequestParam String name){
		List<Book> bookOfName=bookService.findByTitle(name);
		if(bookOfName.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok().body(bookOfName);
	}
	@PostMapping
	public ResponseEntity<?> saveBook(@RequestBody Book book){
		return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id , @RequestBody Book book){
		Optional<Book> bookOptinal = bookService.findById(id);
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
		
		return ResponseEntity.status(HttpStatus.OK).body(bookService.save(bookOptinal.get()));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id){
		bookService.deleteById(id);
	 	return ResponseEntity.ok().build();
	}
}
