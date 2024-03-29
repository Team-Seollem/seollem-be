package com.seollem.server.restdocs.util;

import com.seollem.server.book.Book;
import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.book.BookDto;
import com.seollem.server.book.BookDto.AbandonResponse;
import com.seollem.server.book.BookDto.BooksHaveMemoResponse;
import com.seollem.server.book.BookDto.CalenderResponse;
import com.seollem.server.book.BookDto.LibraryResponse;
import com.seollem.server.externallibrary.AladdinResponseDto;
import com.seollem.server.globaldto.PageInfo;
import com.seollem.server.member.Member;
import com.seollem.server.member.dto.HallOfFameInnerDto;
import com.seollem.server.member.dto.MemberDto;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookDto;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookMemoDto;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookResponseDto;
import com.seollem.server.member.dto.othermemberprofile.OtherLibraryDto;
import com.seollem.server.member.dto.othermemberprofile.OtherMemberProfileResponseDto;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.Memo.MemoAuthority;
import com.seollem.server.memo.Memo.MemoType;
import com.seollem.server.memo.MemoDto;
import com.seollem.server.memo.MemoDto.PostResponse;
import com.seollem.server.memo.MemoDto.RandomResponse;
import com.seollem.server.memo.MemoDto.Response;
import com.seollem.server.memolike.MemoLike;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;

public class StubDataUtil {


  public static class MockBook {

    public static Book getBook() {
      return new com.seollem.server.book.Book(1, "미움받을용기", "https://imageurl1.com", "아들러", "한빛출판사",
          214, 406, 5, BookStatus.DONE, LocalDateTime.parse("2022-10-02T11:09:10"),
          LocalDateTime.parse("2022-10-13T21:04:32"), new Member(), null);
    }

    public static List<Book> getBookList() {

      List<Book> books = new ArrayList<>();

      Book book1 =
          new com.seollem.server.book.Book(1, "미움받을용기", "https://imageurl1.com", "아들러", "한빛출판사",
              214, 406, 5, BookStatus.DONE, LocalDateTime.parse("2022-10-02T11:09:10"),
              LocalDateTime.parse("2022-10-13T21:04:32"), new Member(), null);
      Book book2 =
          new com.seollem.server.book.Book(12, "차라투스트라는 이렇게 말했다.", "https://imageurl12.com", "니체",
              "원호출판사", 15, 898, 0, BookStatus.DONE, LocalDateTime.parse("2022-09-03T10:15:30"),
              LocalDateTime.parse("2022-10-04T12:15:33"), new Member(), null);

      books.add(book1);
      books.add(book2);

      return books;
    }


    public static PageImpl<Book> getBookPage() {
      List<Book> list = new ArrayList<>();
      Book book1 =
          new com.seollem.server.book.Book(1, "미움받을용기", "https://imageurl1.com", "아들러", "한빛출판사",
              214, 406, 5, BookStatus.DONE, LocalDateTime.parse("2022-10-02T11:09:10"),
              LocalDateTime.parse("2022-10-13T21:04:32"), new Member(), null);
      Book book2 =
          new com.seollem.server.book.Book(12, "차라투스트라는 이렇게 말했다.", "https://imageurl12.com", "니체",
              "원호출판사", 15, 898, 0, BookStatus.DONE, LocalDateTime.parse("2022-09-03T10:15:30"),
              LocalDateTime.parse("2022-10-04T12:15:33"), new Member(), null);
      list.add(book1);
      list.add(book2);

      return new PageImpl<>(list);
    }

    public static List<BookDto.LibraryResponse> getLibraryResponse() {
      List<BookDto.LibraryResponse> libraryResponses = new ArrayList<>();
      BookDto.LibraryResponse libraryResponse1 =
          new LibraryResponse(1, "미움받을용기", "https://imageurl1.com", "아들러",
              LocalDateTime.parse("2022-09-23T19:08:56"), 5, 214, 406, BookStatus.DONE, 2);
      BookDto.LibraryResponse libraryResponse2 =
          new LibraryResponse(12, "차라투스트라는 이렇게 말했다.", "https://imageurl12.com", "니체",
              LocalDateTime.parse("2022-09-01T12:09:11"), 0, 15, 898, BookStatus.DONE, 0);
      libraryResponses.add(libraryResponse1);
      libraryResponses.add(libraryResponse2);
      return libraryResponses;
    }

    public static List<BookDto.CalenderResponse> getCalenderResponse() {
      List<BookDto.CalenderResponse> calenderResponses = new ArrayList<>();
      BookDto.CalenderResponse response1 =
          new CalenderResponse(1, LocalDateTime.parse("2022-10-13T21:04:32"),
              "https://imageurl1.com");
      BookDto.CalenderResponse response2 =
          new CalenderResponse(12, LocalDateTime.parse("2022-10-04T12:15:33"),
              "https://imageurl12.com");
      calenderResponses.add(response1);
      calenderResponses.add(response2);
      return calenderResponses;
    }

    public static List<BookDto.AbandonResponse> getAbandonResponse() {
      List<BookDto.AbandonResponse> Responses = new ArrayList<>();
      BookDto.AbandonResponse response1 =
          new AbandonResponse(1, LocalDateTime.parse("2022-04-30T21:04:32"), "미움받을용기",
              "https://imageurl1.com");
      BookDto.AbandonResponse response2 =
          new AbandonResponse(12, LocalDateTime.parse("2022-05-13T01:44:02"), "차라투스트라는 이렇게 말했다.",
              "https://imageurl12.com");
      Responses.add(response1);
      Responses.add(response2);
      return Responses;
    }

    public static List<BooksHaveMemoResponse> getBooksHaveMemoResponse() {
      List<BooksHaveMemoResponse> Responses = new ArrayList<>();
      BooksHaveMemoResponse response1 =
          new BooksHaveMemoResponse(1, "미움받을용기", "https://imageurl1.com", 2);
      BooksHaveMemoResponse response2 =
          new BooksHaveMemoResponse(12, "차라투스트라는 이렇게 말했다.", "https://imageurl12.com", 0);
      Responses.add(response1);
      Responses.add(response2);
      return Responses;
    }

    public static BookDto.DetailResponse getBookDetailResponse() {
      return new BookDto.DetailResponse(1, "미움받을용기", "https://imageurl1.com", "아들러", "한빛출판사",
          LocalDateTime.parse("2022-04-30T21:04:32"), 5, 214, 406, BookStatus.DONE,
          LocalDateTime.parse("2022-10-02T11:09:10"), LocalDateTime.parse("2022-10-13T21:04:32"),
          null, 2);
    }

    public static BookDto.PostResponse getBookPostResponse() {
      return new BookDto.PostResponse(1, "미움받을용기", "아들러", "https://imageurl1.com", BookStatus.DONE);
    }

    public static BookDto.PatchResponse getBookPatchResponse() {
      return new BookDto.PatchResponse("알프레드 아들러", "독립출판", 511,
          LocalDateTime.parse("2022-10-02T11:09:10"), LocalDateTime.parse("2022-10-13T21:04:32"),
          BookStatus.DONE, 5, 371);
    }

    public static OtherMemberBookDto getOtherMemberBookDto() {
      return new OtherMemberBookDto("미움받을용기", "아들러", "https://imageurl1.com", "한빛출판사", 406, 214, 3,
          BookStatus.DONE);
    }

    public static PageImpl<OtherMemberBookMemoDto> getPageOtherMemberBookMemoDto() {
      OtherMemberBookMemoDto dto1 =
          new OtherMemberBookMemoDto(1, MemoType.BOOK_CONTENT, "메모 1의 메모한 내용입니다.", 24);
      OtherMemberBookMemoDto dto2 =
          new OtherMemberBookMemoDto(4, MemoType.QUESTION, "메모 4에 메모한 내용입니다.", 223);

      List<OtherMemberBookMemoDto> list = new ArrayList<>();
      list.add(dto1);
      list.add(dto2);

      return new PageImpl<>(list);

    }


  }

  public static class MockMember {

    public static Member getMember() {
      return new Member(1, "starrypro@gmail.com", "김형섭", "password", "ROLE_USER", "안녕하세요. 김형섭입니다.",
          "https://profileImage.com",
          new ArrayList<Book>(), new ArrayList<MemoLike>());
    }

    public static MemberDto.GetResponse getMemberGetResponse() {
      return new MemberDto.GetResponse("starrypro@gmail.com", "김형섭", "안녕하세요. 김형섭입니다.",
          "https://profileImage.com");
    }

    public static MemberDto.PatchResponse getMemberPatchResponse() {
      return new MemberDto.PatchResponse("starrypro@gmail.com", "이슬", LocalDateTime.now(),
          "안녕하세요. 이슬입니다.", "https://profileImage.com");
    }

    public static List<HallOfFameInnerDto> getHallOfFameWithBook() {
      HallOfFameInnerDto dto1 = new HallOfFameInnerDto(25, "https://imageUrl25.com", "김형섭", 24);
      HallOfFameInnerDto dto2 = new HallOfFameInnerDto(3, "https://imageUrl3.com", "한성욱", 21);
      HallOfFameInnerDto dto3 = new HallOfFameInnerDto(46, "https://imageUrl46.com", "김주현", 2);
      HallOfFameInnerDto dto4 = new HallOfFameInnerDto(22, "https://imageUrl22.com", "이슬", 1);

      List<HallOfFameInnerDto> list = new ArrayList<>();

      list.add(dto1);
      list.add(dto2);
      list.add(dto3);
      list.add(dto4);

      return list;
    }

    public static List<HallOfFameInnerDto> getHallOfFameWithMemo() {
      HallOfFameInnerDto dto1 = new HallOfFameInnerDto(25, "https://imageUrl25.com", "김형섭", 15);
      HallOfFameInnerDto dto2 = new HallOfFameInnerDto(36, "https://imageUrl36.com", "뽐므", 3);
      HallOfFameInnerDto dto3 = new HallOfFameInnerDto(86, "https://imageUrl86.com", "최민석", 3);
      HallOfFameInnerDto dto4 = new HallOfFameInnerDto(3, "https://imageUrl3.com", "한성욱", 1);

      List<HallOfFameInnerDto> list = new ArrayList<>();

      list.add(dto1);
      list.add(dto2);
      list.add(dto3);
      list.add(dto4);

      return list;
    }

    public static OtherMemberProfileResponseDto getOtherMemberProfile() {
      OtherLibraryDto libraryDto1 = new OtherLibraryDto(1, "미움받을용기", "아들러", "https://imageurl.com");
      OtherLibraryDto libraryDto2 =
          new OtherLibraryDto(12, "차라투스트라는 이렇게 말했다.", "니체", "https://imageurl.com");

      List<OtherLibraryDto> list = new ArrayList<>();
      list.add(libraryDto1);
      list.add(libraryDto2);

      OtherMemberProfileResponseDto response =
          new OtherMemberProfileResponseDto("김형섭", "https://memberimageurl.com",
              "안녕하세요. 저는 김형섭입니다. 반갑습니다.", list, new PageInfo(1, 2, 2, 1));

      return response;
    }

    public static OtherMemberBookResponseDto getOtherMemberBookResponse() {

      OtherMemberBookMemoDto memoDto1 =
          new OtherMemberBookMemoDto(1, MemoType.BOOK_CONTENT, "메모 1의 메모한 내용입니다.", 24, 0, false);
      OtherMemberBookMemoDto memoDto2 =
          new OtherMemberBookMemoDto(4, MemoType.QUESTION, "메모 4에 메모한 내용입니다.", 223, 2, true);

      List<OtherMemberBookMemoDto> list = new ArrayList<>();
      list.add(memoDto1);
      list.add(memoDto2);

      PageInfo pageInfo = new PageInfo(1, 2, 2, 1);

      OtherMemberBookResponseDto result = new OtherMemberBookResponseDto();
      result.setTitle("미움받을용기");
      result.setAuthor("아들러");
      result.setCover("https://imageurl1.com");
      result.setPublisher("한빛출판사");
      result.setItemPage(406);
      result.setCurrentPage(214);
      result.setStar(3);
      result.setBookStatus(BookStatus.DONE);
      result.setMemoList(list);
      result.setPageInfo(pageInfo);

      return result;

    }
  }


  public static class MockMemo {

    public static List<Memo> getMemos() {
      Memo memo1 =
          new Memo(1, MemoType.BOOK_CONTENT, "메모 1의 메모한 내용입니다.", 24, MemoAuthority.PUBLIC, null,
              null, null);
      Memo memo2 =
          new Memo(4, MemoType.QUESTION, "메모 4에 메모한 내용입니다.", 223, MemoAuthority.PUBLIC, null, null,
              null);
      List<Memo> list = new ArrayList<>();
      list.add(memo1);
      list.add(memo2);
      return list;
    }

    public static List<MemoDto.Response> getMemoResponses() {
      MemoDto.Response memoResponse1 =
          new MemoDto.Response(1, MemoType.BOOK_CONTENT, "미움받을 용기 너무 재밌다.!!", 24,
              MemoAuthority.PUBLIC, 0, LocalDateTime.parse("2022-10-12T07:54:32"),
              LocalDateTime.parse("2022-10-12T07:54:32"));
      MemoDto.Response memoResponse2 =
          new MemoDto.Response(4, MemoType.QUESTION, "왜 미움받아도 괜찮은걸까?", 223, MemoAuthority.PUBLIC, 0,
              LocalDateTime.parse("2022-10-13T08:55:01"),
              LocalDateTime.parse("2022-10-13T08:55:01"));
      List<MemoDto.Response> list = new ArrayList<>();
      list.add(memoResponse1);
      list.add(memoResponse2);
      return list;
    }

    public static PageImpl<Memo> getMemoPage() {
      Memo memo1 =
          new Memo(1, MemoType.BOOK_CONTENT, "미움받을 용기 너무 재밌다.!!", 24, MemoAuthority.PUBLIC, null,
              null, null);
      Memo memo2 =
          new Memo(4, MemoType.QUESTION, "왜 미움받아도 괜찮은걸까?", 223, MemoAuthority.PUBLIC, null, null,
              null);
      List<Memo> list = new ArrayList<>();
      list.add(memo1);
      list.add(memo2);
      return new PageImpl<>(list);
    }

    public static MemoDto.RandomResponse getRandomMemoResponse() {
      return new RandomResponse(3, "랜덤 메모 내용입니다.", MemoType.BOOK_CONTENT, 15, LocalDateTime.now(),
          LocalDateTime.now());
    }

    public static MemoDto.Response getMemoResponse() {
      return new Response(3, MemoType.BOOK_CONTENT, "메모 내용입니다.", 14, MemoAuthority.PUBLIC, 0,
          LocalDateTime.now(), LocalDateTime.now());
    }


    public static MemoDto.PostResponse getMemoPostResponse() {
      return new PostResponse(3, MemoType.BOOK_CONTENT, "새롭게 등록할 메모 내용입니다.", 255,
          MemoAuthority.PUBLIC,
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

    public static AladdinResponseDto getItemNewSpecial() {
      String response =
          "[{\"cover\":\"https://image.aladin.co.kr/product/31448/38/cover/8954448860_2.jpg\",\"author\":\"이서수 (지은이)\",\"publisher\":\"자음과모음\",\"title\":\"엄마를 절에 버리러\",\"itemPage\":\"164\"},{\"cover\":\"https://image.aladin.co.kr/product/31445/71/cover/8960543144_1.jpg\",\"author\":\"다이애나 킴, 에릭 나, 조형민, 김동용, 한지혜 (지은이)\",\"publisher\":\"중앙경제평론사\",\"title\":\"미국 부동산을 알면 투자가 보인다\",\"itemPage\":\"300\"},{\"cover\":\"https://image.aladin.co.kr/product/31445/58/cover/k082832415_1.jpg\",\"author\":\"수전 톰스 (지은이), 장혜인 (옮긴이)\",\"publisher\":\"더퀘스트\",\"title\":\"피아노의 시간 - 100곡으로 듣는 위안과 매혹의 역사\",\"itemPage\":\"532\"},{\"cover\":\"https://image.aladin.co.kr/product/31445/2/cover/k442832413_1.jpg\",\"author\":\"김태평 (지은이)\",\"publisher\":\"문학수첩\",\"title\":\"안녕, 나의 식물 친구\",\"itemPage\":\"212\"},{\"cover\":\"https://image.aladin.co.kr/product/31444/2/cover/k352832411_1.jpg\",\"author\":\"카밀라 팡 (지은이), 김보은 (옮긴이)\",\"publisher\":\"푸른숲\",\"title\":\"자신의 존재에 대해 사과하지 말 것 - 삶, 사랑, 관계에 닿기 위한 자폐인 과학자의 인간 탐구기\",\"itemPage\":\"320\"},{\"cover\":\"https://image.aladin.co.kr/product/31443/82/cover/k652832410_1.jpg\",\"author\":\"박선아 (지은이)\",\"publisher\":\"위즈덤하우스\",\"title\":\"우아한 언어\",\"itemPage\":\"228\"},{\"cover\":\"https://image.aladin.co.kr/product/31443/73/cover/k222832410_1.jpg\",\"author\":\"박상배 (지은이)\",\"publisher\":\"위즈덤하우스\",\"title\":\"부자의 뇌를 훔치는 코어리딩 - 돈과 운을 불러들이는 한 줄 독서 혁명의 기적\",\"itemPage\":\"260\"},{\"cover\":\"https://image.aladin.co.kr/product/31443/64/cover/k772832419_1.jpg\",\"author\":\"다비드 칼리 (지은이), 장 줄리앙 (그림), 윤경희 (옮긴이)\",\"publisher\":\"봄볕\",\"title\":\"나의 작은 아빠\",\"itemPage\":\"36\"},{\"cover\":\"https://image.aladin.co.kr/product/31443/50/cover/k272832419_1.jpg\",\"author\":\"3B2S (지은이), 산지직송 (원작), 윤쓰 (각색)\",\"publisher\":\"문페이스\",\"title\":\"도굴왕 8\",\"itemPage\":\"320\"},{\"cover\":\"https://image.aladin.co.kr/product/31443/42/cover/k932832419_1.jpg\",\"author\":\"백수혜 (지은이)\",\"publisher\":\"세미콜론\",\"title\":\"여기는 '공덕동 식물유치원'입니다 - 재개발 단지에 버려진 식물을 구조하는\",\"itemPage\":\"204\"}]".replace(
              "\\", "");
      return new AladdinResponseDto(response);
    }

    public static AladdinResponseDto getSearchedBook() {
      String response =
          "[{\"cover\":\"https://image.aladin.co.kr/product/16/80/cover/s8937460033_1.jpg\",\"author\":\"윌리엄 셰익스피어 (지은이), 최종철 (옮긴이)\",\"publisher\":\"민음사\",\"title\":\"햄릿\",\"itemPage\":\"226\"},{\"cover\":\"https://image.aladin.co.kr/product/2090/86/cover/s402638427_1.jpg\",\"author\":\"윌리엄 셰익스피어 (지은이), 최종철 (옮긴이)\",\"publisher\":\"민음사\",\"title\":\"셰익스피어 4대 비극 세트 : 햄릿.오셀로.맥베스.리어 왕 - 전4권\",\"itemPage\":\"900\"},{\"cover\":\"https://image.aladin.co.kr/product/25861/77/cover/8968332886_1.jpg\",\"author\":\"대니얼 조슈아 루빈 (지은이), 이한이 (옮긴이)\",\"publisher\":\"블랙피쉬\",\"title\":\"스토리텔링 바이블 - 작가라면 알아야 할 이야기 창작 완벽 가이드\",\"itemPage\":\"520\"},{\"cover\":\"https://image.aladin.co.kr/product/47/66/cover/8937460998_3.jpg\",\"author\":\"윌리엄 셰익스피어 (지은이), 최종철 (옮긴이)\",\"publisher\":\"민음사\",\"title\":\"맥베스\",\"itemPage\":\"151\"},{\"cover\":\"https://image.aladin.co.kr/product/60/14/cover/8937461277_2.jpg\",\"author\":\"윌리엄 셰익스피어 (지은이), 최종철 (옮긴이)\",\"publisher\":\"민음사\",\"title\":\"리어 왕\",\"itemPage\":\"228\"},{\"cover\":\"https://image.aladin.co.kr/product/28485/41/cover/8937416786_1.jpg\",\"author\":\"윌리엄 셰익스피어 (지은이), 최종철 (옮긴이)\",\"publisher\":\"민음사\",\"title\":\"셰익스피어 4대 비극 에디션 세트 - 전4권 (리커버 특별판) - 햄릿 + 오셀로 + 리어 왕 + 맥베스\",\"itemPage\":\"900\"},{\"cover\":\"https://image.aladin.co.kr/product/193/34/cover/8937461730_2.jpg\",\"author\":\"윌리엄 셰익스피어 (지은이), 최종철 (옮긴이)\",\"publisher\":\"민음사\",\"title\":\"로미오와 줄리엣\",\"itemPage\":\"200\"},{\"cover\":\"https://image.aladin.co.kr/product/14954/28/cover/8937475308_1.jpg\",\"author\":\"윌리엄 셰익스피어 (지은이), 피천득 (옮긴이)\",\"publisher\":\"민음사\",\"title\":\"셰익스피어 소네트\",\"itemPage\":\"337\"},{\"cover\":\"https://image.aladin.co.kr/product/857/94/cover/8937462621_2.jpg\",\"author\":\"윌리엄 셰익스피어 (지은이), 최종철 (옮긴이)\",\"publisher\":\"민음사\",\"title\":\"베니스의 상인\",\"itemPage\":\"162\"},{\"cover\":\"https://image.aladin.co.kr/product/29/51/cover/893746053x_3.jpg\",\"author\":\"윌리엄 셰익스피어 (지은이), 최종철 (옮긴이)\",\"publisher\":\"민음사\",\"title\":\"오셀로\",\"itemPage\":\"244\"}]".replace(
              "\\", "");
      return new AladdinResponseDto(response);
    }
  }

  public static class MockMemoLike {

    public static MemoLike getMemoLike() {
      return new MemoLike(1, null, null);
    }
  }
}
