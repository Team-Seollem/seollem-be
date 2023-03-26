package com.seollem.server.restdocs.util;

import com.seollem.server.book.Book;
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookDto;
import com.seollem.server.book.BookDto.AbandonResponse;
import com.seollem.server.book.BookDto.BooksHaveMemoResponse;
import com.seollem.server.book.BookDto.CalenderResponse;
import com.seollem.server.book.BookDto.LibraryResponse;
import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.Memo.MemoAuthority;
import com.seollem.server.memo.Memo.MemoType;
import com.seollem.server.memo.MemoDto;
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
              0, 42, 114, BookStatus.YET, 0);
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

    public static List<BooksHaveMemoResponse> getBooksHaveMemoResponse() {
      List<BooksHaveMemoResponse> Responses = new ArrayList<>();
      BooksHaveMemoResponse response1 =
          new BooksHaveMemoResponse(1, "책 제목1", "https://imageurl1.com", 1);
      BooksHaveMemoResponse response2 =
          new BooksHaveMemoResponse(12, "책 제목12", "https://imageurl12.com", 5);
      Responses.add(response1);
      Responses.add(response2);
      return Responses;
    }

    public static BookDto.DetailResponse getBookDetailResponse() {
      return new BookDto.DetailResponse(1, "title", "cover", "author", "publisher",
          LocalDateTime.now(), 1, 107, 833, BookStatus.ING, LocalDateTime.now(),
          LocalDateTime.now(), null, 2);
    }


  }

  public static class MockMember {

    public static Member getMember() {
      return new Member(1, "starrypro@gmail.com", "김형섭", "password", "ROLE_USER", null, null);
    }
  }

  public static class MockMemo {

    public static List<Memo> getMemos() {
      Memo memo1 =
          new Memo(1, MemoType.BOOK_CONTENT, "메모 1의 메모한 내용입니다.", 24, 3, MemoAuthority.PUBLIC, null,
              null,
              null);
      Memo memo2 =
          new Memo(4, MemoType.QUESTION, "메모 4에 메모한 내용입니다.", 223, 0, MemoAuthority.PUBLIC, null,
              null,
              null);
      List<Memo> list = new ArrayList<>();
      list.add(memo1);
      list.add(memo2);
      return list;
    }

    public static List<MemoDto.Response> getMemoResponses() {
      MemoDto.Response memoResponse1 =
          new MemoDto.Response(1, MemoType.BOOK_CONTENT, "메모 1의 메모한 내용입니다.", 24,
              MemoAuthority.PUBLIC, 3, LocalDateTime.now(), LocalDateTime.now()
          );
      MemoDto.Response memoResponse2 =
          new MemoDto.Response(4, MemoType.QUESTION, "메모 4에 메모한 내용입니다.", 223,
              MemoAuthority.PUBLIC, 3, LocalDateTime.now(), LocalDateTime.now()
          );
      List<MemoDto.Response> list = new ArrayList<>();
      list.add(memoResponse1);
      list.add(memoResponse2);
      return list;
    }

    public static PageImpl<Memo> getMemoPage() {
      Memo memo =
          new Memo(1, MemoType.BOOK_CONTENT, "메모4의 내용입니다.", 42, 0, MemoAuthority.PUBLIC,
              null, null, null);
      return new PageImpl<>(List.of(memo), PageRequest.of(1, 10), 0);
    }
  }
}
