package com.seollem.server.book.service;

import com.seollem.server.book.entity.Book;
import com.seollem.server.book.repository.BookRepository;
import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findVerifiedBookById(long bookId){
        Optional<Book> optionalBookDetail = bookRepository.findById(bookId);
        Book book = optionalBookDetail.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.BOOK_NOT_FOUND));

        return book;
    }

    public Book createBook(Book book){
        verifyExistBookByTitle(book.getTitle());
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    public void verifyExistBookByTitle(String title){
        Optional<Book> optionalBook = bookRepository.findByTitle(title);
        if(optionalBook.isPresent())
            throw new BusinessLogicException(ExceptionCode.BOOK_EXISTS);
    }


}
