package com.cava.examples.services.book.service;

import com.cava.examples.common.entitis.Book;
import com.cava.examples.common.entitis.Categoria;
import com.cava.examples.services.book.repostory.LibroRepository;
import com.cava.examples.services.book.service.impl.BookServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImpTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private BookServiceImp bookService;

    @Test
    void findByIdRetornaLibroCuandoExiste() {
        ReflectionTestUtils.setField(bookService, "repository", libroRepository);
        Categoria categoria = new Categoria(1L, "Romance");
        Book libro = new Book(5L, "Libro Test", "subtitulo", new Date(), new BigDecimal("15000"), categoria);

        when(libroRepository.findById(5L)).thenReturn(Optional.of(libro));

        Optional<Book> resultado = bookService.findById(5L);

        assertTrue(resultado.isPresent());
        assertEquals("Libro Test", resultado.get().getTitle());
    }

    @Test
    void savePersisteLibro() {
        ReflectionTestUtils.setField(bookService, "repository", libroRepository);
        Categoria categoria = new Categoria(2L, "Drama");
        Book libro = new Book(null, "Nuevo", "descripcion", new Date(), new BigDecimal("30000"), categoria);
        Book guardado = new Book(10L, "Nuevo", "descripcion", new Date(), new BigDecimal("30000"), categoria);

        when(libroRepository.save(libro)).thenReturn(guardado);

        Book resultado = bookService.save(libro);

        assertEquals(10L, resultado.getIsbn());
        assertEquals("Nuevo", resultado.getTitle());
    }
}
