package com.cava.examples.services.book.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cava.examples.services.book.Datos;
import com.cava.examples.services.book.models.Book;
import com.cava.examples.services.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(controllers = BookController.class)
class BookControllerTest {
	
	@Autowired
	private MockMvc mvc;
	@MockBean
	private BookService<Book> bookService;
	
	ObjectMapper mapper;
	
	@BeforeEach
	void setUp() {
		mapper = new ObjectMapper();
	}

	@Test
	void testGetAllBooks() throws Exception {
		//Given
		when(bookService.findAll()).thenReturn(Datos.bookAll());
		//when
		mvc.perform(
				MockMvcRequestBuilders.get("/")
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].title").value("Como programar en java"))
		.andDo(print())
		;
		//then
		verify(bookService).findAll();
	}


	@Test
	void getByName() throws Exception {
		//Given
		when(bookService.findByTitle("Como programar en java")).thenReturn(Datos.boock().stream().toList());
		//when
		mvc.perform(
				MockMvcRequestBuilders.get("/searchname")
						.param("name","Como programar en java")
						.accept(MediaType.APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Como programar en java"))
				.andDo(print())
				;
		//then
		verify(bookService).findByTitle(anyString());
	}
	@Test
	void getByNameEmty() throws Exception {
		//Given
		when(bookService.findByTitle(anyString())).thenReturn(Datos.bookEmty().stream().toList());
		//when
		mvc.perform(
						MockMvcRequestBuilders.get("/searchname")
								.param("name",".net")
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isNoContent())
				.andDo(print())
		;
		//then
		verify(bookService).findByTitle(anyString());
	}

	@Test
	void saveBook() {

	}

	@Test
	void updateBook() {
	}

	@Test
	void deleteById() {
	}
}
