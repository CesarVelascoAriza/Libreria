package com.cava.examples.services.book.controller;

import com.cava.examples.common.entitis.Book;
import com.cava.examples.common.entitis.Categoria;
import com.cava.examples.services.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void updateBookCuandoExisteRetornaOk() {
        ReflectionTestUtils.setField(bookController, "service", bookService);
        Categoria categoria = new Categoria(1L, "Romance");
        Book existente = new Book(7L, "Antes", "Sub", new Date(), new BigDecimal("10000"), categoria);
        Book actualizacion = new Book(7L, "Despues", "Nuevo", new Date(), new BigDecimal("20000"), categoria);

        when(bookService.findById(7L)).thenReturn(Optional.of(existente));
        when(bookService.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<?> response = bookController.updateBook(7L, actualizacion);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(bookService).save(any(Book.class));
        assertEquals("Despues", ((Book) response.getBody()).getTitle());
        assertEquals("Nuevo", ((Book) response.getBody()).getSubTitle());
    }

    @Test
    void updateBookCuandoNoExisteRetornaNoContent() {
        ReflectionTestUtils.setField(bookController, "service", bookService);
        Book actualizacion = new Book(9L, "Titulo", "Sub", new Date(), new BigDecimal("20000"), new Categoria(2L, "Drama"));

        when(bookService.findById(9L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = bookController.updateBook(9L, actualizacion);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
