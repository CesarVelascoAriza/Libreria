package com.cava.examples.services.book.service.impl;


import com.cava.examples.common.entitis.Book;
import com.cava.examples.service.impl.CommonServiceImp;
import com.cava.examples.services.book.repostory.LibroRepository;
import com.cava.examples.services.book.service.BookService;

import org.springframework.stereotype.Service;


@Service
public class BookServiceImp extends CommonServiceImp<Book , LibroRepository> implements BookService{



}
