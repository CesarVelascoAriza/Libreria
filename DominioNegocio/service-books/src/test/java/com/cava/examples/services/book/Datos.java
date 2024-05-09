package com.cava.examples.services.book;

import com.cava.examples.common.entitis.Book;
import com.cava.examples.common.entitis.Categoria;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Set;


public class Datos {
	
	public static Optional<Categoria> categoria(){
		return Optional.of(new Categoria(1L, "Java"));
	}
	
	public static Iterable<Book> bookAll(){
		Set<Book> book= Set.of(boock().get());
		return book;
	}

	public static Optional<Book> boock(){
		return Optional.of(new Book(1L, "Como programar en java", "java programing", new Date(), new BigDecimal("90000") , categoria().get()));
	}
	public static Optional<Book> bookEmty(){
		return Optional.empty();
	}
}
