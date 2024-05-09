package com.cava.examples.services.book.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cava.examples.common.entitis.Book;

import com.cava.examples.common.entitis.Categoria;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import com.cava.examples.services.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;


@WebMvcTest(controllers = BookController.class)
class BookControllerTest {
	
	@Autowired
	private MockMvc mvc;
	@MockBean
	private BookService bookService;
	
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

/*
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
	}*/

	@Test
	void saveBook() throws Exception {

		//Given
		Book book = new Book(null,"Prueba","sub prueba",new Date(),new BigDecimal("50000"),new Categoria(1L,"Romance"));
		when(bookService.save(any())).then(invocation ->{
			Book b = invocation.getArgument(0);
			b.setIsbn(1L);
			return  b;
		});
		//when
		mvc.perform(
			MockMvcRequestBuilders.post("/")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(book))
		)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.isbn").value("1"))
				.andExpect(jsonPath("$.title").value("Prueba"))
				.andDo(print())
		;
		verify(bookService).save(any());
	}

	@Test
	void updateBook() throws Exception {
		//Give
		Book book = new Book(1L,"Prueba","sub prueba",new Date(),new BigDecimal("50000"),new Categoria(1L,"Romance"));
		when(bookService.findById(any())).thenReturn(Datos.boock());
		//when
		mvc.perform(
						MockMvcRequestBuilders.put("/1")
								.accept(MediaType.APPLICATION_JSON)
								.contentType(MediaType.APPLICATION_JSON)
								.content(mapper.writeValueAsString(book))
				)
				.andExpect(status().isOk())
				//.andExpect(jsonPath("$.isbn").value("1"))
				//.andExpect(jsonPath("$.title").value("Prueba"))
				.andDo(print())
		;
		//then
		verify(bookService).findById(any());
		verify(bookService).save(any());
	}

	@Test
	void deleteById() throws Exception {

		//Give
		//when
		mvc.perform(
				MockMvcRequestBuilders.delete("/1")
						.contentType(MediaType.APPLICATION_JSON)

		).andExpect(status().isNoContent());
		//then
		verify(bookService).deleteById(any());
	}
}
