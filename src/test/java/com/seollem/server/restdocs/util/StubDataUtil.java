package com.seollem.server.restdocs.util;

import com.seollem.server.book.Book;
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookDto;
import com.seollem.server.book.BookDto.AbandonResponse;
import com.seollem.server.book.BookDto.BooksHaveMemoResponse;
import com.seollem.server.book.BookDto.CalenderResponse;
import com.seollem.server.book.BookDto.LibraryResponse;
import com.seollem.server.externallibrary.AladdinResponseDto;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberDto;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.Memo.MemoAuthority;
import com.seollem.server.memo.Memo.MemoType;
import com.seollem.server.memo.MemoDto;
import com.seollem.server.memo.MemoDto.RandomResponse;
import com.seollem.server.memo.MemoDto.Response;
import com.seollem.server.memolikes.MemoLikes;
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

    public static BookDto.PostResponse getBookPostResponse() {
      return new BookDto.PostResponse(1, "미움받을 용기", "아들러", "https://imageURL.com", BookStatus.YET);
    }

    public static BookDto.PatchResponse getBookPatchResponse() {
      return new BookDto.PatchResponse("김지준", "한빛출판사", 221, LocalDateTime.now(),
          LocalDateTime.now(), BookStatus.YET, 5, 220);
    }


  }

  public static class MockMember {

    public static Member getMember() {
      return new Member(1, "starrypro@gmail.com", "김형섭", "password", "ROLE_USER",
          new ArrayList<Book>(), new ArrayList<MemoLikes>());
    }

    public static MemberDto.GetResponse getMemberGetResponse() {
      return new MemberDto.GetResponse("starrypro@gmail.com", "김형섭");
    }

    public static MemberDto.PatchResponse getMemberPatchResponse() {
      return new MemberDto.PatchResponse("starrypro@gmail.com", "이슬", LocalDateTime.now());
    }
  }


  public static class MockMemo {

    public static List<Memo> getMemos() {
      Memo memo1 =
          new Memo(1, MemoType.BOOK_CONTENT, "메모 1의 메모한 내용입니다.", 24, 3, MemoAuthority.PUBLIC, null,
              null, null);
      Memo memo2 =
          new Memo(4, MemoType.QUESTION, "메모 4에 메모한 내용입니다.", 223, 0, MemoAuthority.PUBLIC, null,
              null, null);
      List<Memo> list = new ArrayList<>();
      list.add(memo1);
      list.add(memo2);
      return list;
    }

    public static List<MemoDto.Response> getMemoResponses() {
      MemoDto.Response memoResponse1 =
          new MemoDto.Response(1, MemoType.BOOK_CONTENT, "메모 1의 메모한 내용입니다.", 24,
              MemoAuthority.PUBLIC, 3, LocalDateTime.now(), LocalDateTime.now());
      MemoDto.Response memoResponse2 =
          new MemoDto.Response(4, MemoType.QUESTION, "메모 4에 메모한 내용입니다.", 223, MemoAuthority.PUBLIC,
              3, LocalDateTime.now(), LocalDateTime.now());
      List<MemoDto.Response> list = new ArrayList<>();
      list.add(memoResponse1);
      list.add(memoResponse2);
      return list;
    }

    public static PageImpl<Memo> getMemoPage() {
      Memo memo =
          new Memo(1, MemoType.BOOK_CONTENT, "메모4의 내용입니다.", 42, 0, MemoAuthority.PUBLIC, null, null,
              null);
      return new PageImpl<>(List.of(memo), PageRequest.of(1, 10), 0);
    }

    public static MemoDto.RandomResponse getRandomMemoResponse() {
      return new RandomResponse(1, "1번 메모의 내용입니다.", MemoType.BOOK_CONTENT, 15, LocalDateTime.now(),
          LocalDateTime.now());
    }

    public static MemoDto.Response getMemoResponse() {
      return new Response(1, MemoType.BOOK_CONTENT, "1번 메모의 내용입니다.", 14, MemoAuthority.PUBLIC, 3,
          LocalDateTime.now(), LocalDateTime.now());
    }
  }

  public static class MockAladdin {

    public static AladdinResponseDto getBestSeller() {
      String response =
          "[{\"cover\":\"https://image.aladin.co.kr/product/30929/51/cover/s302832892_1.jpg\",\"author\":\"세이노 (지은이)\",\"publisher\":\"데이원\",\"title\":\"세이노의 가르침\",\"itemPage\":\"736\"},{\"cover\":\"https://image.aladin.co.kr/product/31337/5/cover/8960519790_1.jpg\",\"author\":\"장하준 (지은이), 김희정 (옮긴이)\",\"publisher\":\"부키\",\"title\":\"장하준의 경제학 레시피 - 마늘에서 초콜릿까지 18가지 재료로 요리한 경제 이야기\",\"itemPage\":\"376\"},{\"cover\":\"https://image.aladin.co.kr/product/31325/1/cover/k832832683_3.jpg\",\"author\":\"김승호 (지은이)\",\"publisher\":\"스노우폭스북스\",\"title\":\"사장학개론\",\"itemPage\":\"438\"},{\"cover\":\"https://image.aladin.co.kr/product/30876/42/cover/k892831289_1.jpg\",\"author\":\"신카이 마코토 (지은이), 민경욱 (옮긴이)\",\"publisher\":\"대원씨아이(단행본)\",\"title\":\"스즈메의 문단속\",\"itemPage\":\"360\"},{\"cover\":\"https://image.aladin.co.kr/product/30995/11/cover/k672831500_2.jpg\",\"author\":\"김미경 (지은이)\",\"publisher\":\"어웨이크북스\",\"title\":\"김미경의 마흔 수업 - 이미 늦었다고 생각하는 당신을 위한\",\"itemPage\":\"300\"},{\"cover\":\"https://image.aladin.co.kr/product/31132/45/cover/8934965908_1.jpg\",\"author\":\"마이클 슈어 (지은이), 염지선 (옮긴이)\",\"publisher\":\"김영사\",\"title\":\"더 좋은 삶을 위한 철학 - 천사와 악마 사이 더 나은 선택을 위한 안내서\",\"itemPage\":\"408\"},{\"cover\":\"https://image.aladin.co.kr/product/31273/7/cover/k542832564_1.jpg\",\"author\":\"김익한 (지은이)\",\"publisher\":\"다산북스\",\"title\":\"거인의 노트 - 인생에서 무엇을 보고 어떻게 기록할 것인가\",\"itemPage\":\"292\"},{\"cover\":\"https://image.aladin.co.kr/product/31413/4/cover/8932474877_1.jpg\",\"author\":\"박찬욱 (지은이), 전영욱 (사진)\",\"publisher\":\"을유문화사\",\"title\":\"헤어질 결심 포토북\",\"itemPage\":\"240\"},{\"cover\":\"https://image.aladin.co.kr/product/31317/26/cover/k772832988_1.jpg\",\"author\":\"이언 매큐언 (지은이), 한정아 (옮긴이)\",\"publisher\":\"복복서가\",\"title\":\"견딜 수 없는 사랑\",\"itemPage\":\"364\"},{\"cover\":\"https://image.aladin.co.kr/product/31250/53/cover/k262832360_1.jpg\",\"author\":\"류이치 사카모토 (Ryuichi Sakamoto) (지은이), 양윤옥 (옮긴이)\",\"publisher\":\"청미래\",\"title\":\"음악으로 자유로워지다\",\"itemPage\":\"298\"}]".replace(
              "\\", "");
      return new AladdinResponseDto(response);
    }

  }
}
