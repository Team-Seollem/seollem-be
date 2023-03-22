package com.seollem.server.restdocs.util;

import com.seollem.server.book.Book;
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookDto;
import com.seollem.server.book.BookDto.AbandonResponse;
import com.seollem.server.book.BookDto.CalenderResponse;
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
          BookStatus.YET, LocalDateTime.now(), LocalDateTime.now(), 1, new Member(), null);
    }

    public static PageImpl<Book> getBookPage() {
      Book book =
          new com.seollem.server.book.Book(1, "title", "cover", "author", "publisher", 1, 1, 1,
              BookStatus.YET, LocalDateTime.now(), LocalDateTime.now(), 1, new Member(), null);
      return new PageImpl<>(List.of(book), PageRequest.of(1, 10), 0);
    }

    public static List<BookDto.LibraryResponse> getLibraryResponse() {
      List<BookDto.LibraryResponse> libraryResponses = new ArrayList<>();
      BookDto.LibraryResponse libraryResponse1 =
          new LibraryResponse(1, "미움받을용기", "https://imageurl1.com", "아들러", LocalDateTime.now(), 3,
              104, 507, BookStatus.YET, 3);
      BookDto.LibraryResponse libraryResponse2 =
          new LibraryResponse(12, "역사란 무엇인가", "https://imageurl12.com", "김기명", LocalDateTime.now(),
              0,
              42, 114, BookStatus.YET, 0);
      libraryResponses.add(libraryResponse1);
      libraryResponses.add(libraryResponse2);
      return libraryResponses;
    }

    public static List<BookDto.CalenderResponse> getCalenderResponse() {
      List<BookDto.CalenderResponse> calenderResponses = new ArrayList<>();
      BookDto.CalenderResponse response1 =
          new CalenderResponse(1, LocalDateTime.now(), "https://imageurl1.com");
      BookDto.CalenderResponse response2 =
          new CalenderResponse(12, LocalDateTime.now(), "https://imageurl12.com");
      calenderResponses.add(response1);
      calenderResponses.add(response2);
      return calenderResponses;
    }

    public static List<BookDto.AbandonResponse> getAbandonResponse() {
      List<BookDto.AbandonResponse> Responses = new ArrayList<>();
      BookDto.AbandonResponse response1 =
          new AbandonResponse(1, LocalDateTime.now(), "책 제목1", "https://imageurl1.com");
      BookDto.AbandonResponse response2 =
          new AbandonResponse(12, LocalDateTime.now(), "책 제목12", "https://imageurl12.com");
      Responses.add(response1);
      Responses.add(response2);
      return Responses;
    }


  }

  public static class MockMember {

    public static Member getMember() {
      return new Member(1, "starrypro@gmail.com", "김형섭", "password",
          "ROLE_USER", null, null);
    }
  }
}
