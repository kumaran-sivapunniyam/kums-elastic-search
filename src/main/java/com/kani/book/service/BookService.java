package com.kani.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.kani.book.model.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    void delete(Book book);

    Book findOne(String id);

    Iterable<Book> findAll();

    Page<Book> findByAuthor(String author, PageRequest pageRequest);

    List<Book> findByTitle(String title);

}