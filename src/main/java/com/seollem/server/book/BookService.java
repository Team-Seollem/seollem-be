package com.seollem.server.book;

import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import com.seollem.server.member.Member;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book createBook(Book book) {
    verifyExistBook(book);
    Book savedBook = bookRepository.save(book);
    return savedBook;
  }

  public Book updateBook(Book book) {
    Book findBook = findVerifiedBookById(book.getBookId());

    Optional.ofNullable(book.getAuthor()).ifPresent(author -> findBook.setAuthor(author));
    Optional.ofNullable(book.getPublisher())
        .ifPresent(publisher -> findBook.setPublisher(publisher));
    Optional.ofNullable(book.getReadStartDate())
        .ifPresent(readStartDate -> findBook.setReadStartDate(readStartDate));
    Optional.ofNullable(book.getReadEndDate())
        .ifPresent(readEndDate -> findBook.setReadEndDate(readEndDate));
    Optional.ofNullable(book.getBookStatus())
        .ifPresent(bookStatus -> findBook.setBookStatus(bookStatus));
    if (book.getCurrentPage() != 0) {
      findBook.setCurrentPage(book.getCurrentPage());
    }
    if (book.getItemPage() != 0) {
      findBook.setItemPage(book.getItemPage());
    }
    if (book.getStar() != 0) {
      findBook.setStar(book.getStar());
    }
    return bookRepository.save(findBook);
  }

  public void deleteBook(long bookId) {
    Book book = findVerifiedBookById(bookId);
    bookRepository.delete(book);
  }

  public Book findVerifiedBookById(long bookId) {
    Optional<Book> optionalBookDetail = bookRepository.findById(bookId);
    Book book =
        optionalBookDetail.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.BOOK_NOT_FOUND));

    return book;
  }

  public Page<Book> findVerifiedBooksByMemberAndBookStatus(
      int page, int size, Member member, Book.BookStatus bookStatus, String sort) {
    Page<Book> books =
        bookRepository.findAllByMemberAndBookStatus(
            member, bookStatus, PageRequest.of(page, size, Sort.by(sort).descending()));

    return books;
  }

  public Page<Book> findCalenderBooks(
      int page, int size, Member member, LocalDateTime before, LocalDateTime after,
      Book.BookStatus bookStatus, String sort) {
    Optional<Page<Book>> optionalBooks =
        bookRepository.findCalender(
            member, bookStatus, before, after,
            PageRequest.of(page, size, Sort.by(sort).descending()));
    Page<Book> books = optionalBooks.orElseThrow(
        () -> new BusinessLogicException(ExceptionCode.BOOK_NOT_FOUND_PERIOD));

    return books;
  }

  public Page<Book> findAbandonedBooks(int page, int size, Member member) {
    Page<Book> books =
        bookRepository.findAbandon(
            member,
            PageRequest.of(page, size, Sort.by("BOOK_ID").descending()));

    return books;
  }

  public Page<Book> findMemoBooks(int page, int size, Member member) {
    Page<Book> books =
        bookRepository.findBooksHaveMemo(
            member, PageRequest.of(page, size, Sort.by("BOOK_ID").descending()));

    return books;
  }

  public void verifyExistBook(Book book) {
    Optional<Book> optionalBook =
        bookRepository.findByTitle(book.getTitle(), book.getMember().getMemberId());
    if (optionalBook.isPresent()) {
      throw new BusinessLogicException(ExceptionCode.BOOK_EXISTS);
    }
  }

  public void verifyMemberHasBook(long bookId, long memberId) {
    Book book = findVerifiedBookById(bookId);
    if (book.getMember().getMemberId() != memberId) {
      throw new BusinessLogicException(ExceptionCode.NOT_MEMBER_BOOK);
    }
  }

  // bookStatus에 따른 readStartDate, readEndDate 처리
  public Book verifyBookStatus(Book book) {
    if (book.getBookStatus() == null) {
      book.setBookStatus(Book.BookStatus.YET);
      book.setReadStartDate(null);
      book.setReadEndDate(null);
    } else if (book.getBookStatus() == Book.BookStatus.YET) {
      book.setReadStartDate(null);
      book.setReadEndDate(null);
    } else if (book.getBookStatus() == Book.BookStatus.ING) {
      book.setReadEndDate(null);
      if (book.getReadStartDate() == null) {
        throw new BusinessLogicException(ExceptionCode.BOOK_STATUS_WRONG);
      }
    } else if (book.getBookStatus() == Book.BookStatus.DONE) {
      if (book.getReadStartDate() == null || book.getReadEndDate() == null) {
        throw new BusinessLogicException(ExceptionCode.BOOK_STATUS_WRONG);
      }
    } else {
      throw new BusinessLogicException(ExceptionCode.BOOK_STATUS_WRONG);
    }
    return book;
  }

  public void modifyCreateDate(LocalDateTime time, long bookId) {
    bookRepository.modifyCreateDate(time, bookId);
  }

}
