package com.seollem.server.book.service;

import com.seollem.server.book.entity.Book;
import com.seollem.server.book.repository.BookRepository;
import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import com.seollem.server.member.entity.Member;
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

    public Book createBook(Book book){
        verifyExistBookByTitle(book.getTitle());
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    public Book updateBook(Book book){
        Book findBook = findVerifiedBookById(book.getBookId());

        Optional.ofNullable(book.getAuthor())
                .ifPresent(author -> findBook.setAuthor(author));
        Optional.ofNullable(book.getPublisher())
                .ifPresent(publisher -> findBook.setPublisher(publisher));
        Optional.ofNullable(book.getItemPage())
                .ifPresent(itemPage -> findBook.setItemPage(itemPage));
        Optional.ofNullable(book.getReadStartDate())
                .ifPresent(readStartDate -> findBook.setReadStartDate(readStartDate));
        Optional.ofNullable(book.getReadEndDate())
                .ifPresent(readEndDate -> findBook.setReadEndDate(readEndDate));
        Optional.ofNullable(book.getBookStatus())
                .ifPresent(bookStatus -> findBook.setBookStatus(bookStatus));
        Optional.ofNullable(book.getStar())
                .ifPresent(star -> findBook.setStar(star));
        Optional.ofNullable(book.getCurrentPage())
                .ifPresent(currentPage -> findBook.setCurrentPage(currentPage));

        return bookRepository.save(findBook);
    }

    public Book findVerifiedBookById(long bookId){
        Optional<Book> optionalBookDetail = bookRepository.findById(bookId);
        Book book = optionalBookDetail.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.BOOK_NOT_FOUND));

        return book;
    }

    public Page<Book> findVerifiedBooksByMember(int page, int size, Member member){
      Page<Book> books = bookRepository.findAllByMember(PageRequest.of(page, size,
                Sort.by("bookId").descending()), member);

        return books;
    }

    public void verifyExistBookByTitle(String title){
        Optional<Book> optionalBook = bookRepository.findByTitle(title);
        if(optionalBook.isPresent())
            throw new BusinessLogicException(ExceptionCode.BOOK_EXISTS);
    }

    // bookStatus에 따른 readStartDate, readEndDate 처리
    public Book verifyBookStatus(Book book){
        if(book.getBookStatus()==null){
            book.setReadStartDate(null);
            book.setReadEndDate(null);
        } else if(book.getBookStatus()== Book.BookStatus.YET){
            book.setReadStartDate(null);
            book.setReadEndDate(null);
        } else if (book.getBookStatus()== Book.BookStatus.ING) {
            book.setReadEndDate(null);
            if(book.getReadStartDate()==null)
                throw new BusinessLogicException(ExceptionCode.BOOK_STATUS_WRONG);
        } else if (book.getBookStatus()== Book.BookStatus.DONE) {
            if(book.getReadStartDate()==null||book.getReadEndDate()==null)
                throw new BusinessLogicException(ExceptionCode.BOOK_STATUS_WRONG);
        } else throw new BusinessLogicException(ExceptionCode.BOOK_STATUS_WRONG);
        return book;
    }


}
