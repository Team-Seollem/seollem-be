package com.seollem.server.restdocs.util;

import com.seollem.server.book.Book;
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookDto;
import com.seollem.server.book.BookDto.LibraryResponse;
import com.seollem.server.member.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class StubDataUtil {

  public static class MockBook {

    public static Book getBook() {
      return new com.seollem.server.book.Book(1, "title", "cover", "author", "publisher", 1, 1, 1,
          BookStatus.YET,
          LocalDateTime.now(), LocalDateTime.now(), 1, new Member(), null);
    }

    public static PageImpl<Book> getBookPage() {
      Book book =
          new com.seollem.server.book.Book(1, "title", "cover", "author", "publisher", 1, 1, 1,
              BookStatus.YET,
              LocalDateTime.now(), LocalDateTime.now(), 1, new Member(), null);
      return new PageImpl<>(List.of(book), PageRequest.of(1, 10), 0);
    }

    public static List<BookDto.LibraryResponse> getLibraryResponse() {
      List<BookDto.LibraryResponse> libraryResponses = new ArrayList<>();
      BookDto.LibraryResponse libraryResponse1 =
          new LibraryResponse(1, "미움받을용기", "https://imageurl.com", "아들러",
              LocalDateTime.now(), 3, 104, 507, BookStatus.YET, 3);
      BookDto.LibraryResponse libraryResponse2 =
          new LibraryResponse(12, "역사란 무엇인가", "https://imageurl.com", "김기명",
              LocalDateTime.now(), 0, 42, 114, BookStatus.YET, 0);
      libraryResponses.add(libraryResponse1);
      libraryResponses.add(libraryResponse2);
      return libraryResponses;
    }
  }
}
