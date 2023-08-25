package com.cava.examples.services.book.repostory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cava.examples.services.book.models.Book;
import java.util.List;


public interface LibroRepository extends JpaRepository<Book, Long> {

	List<Book> findByTitle(String title);
}
