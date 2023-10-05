package com.cava.examples.services.book.repostory;


import com.cava.examples.common.entitis.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Book, Long> {

	List<Book> findByTitle(String title);
}
