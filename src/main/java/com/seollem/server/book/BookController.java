package com.seollem.server.book;

import com.seollem.server.book.BookDto.BooksHaveMemoResponse;
import com.seollem.server.globaldto.MultiResponseDto;
import com.seollem.server.globaldto.MultiResponseDtoForBooksHaveMemo;
import com.seollem.server.globaldto.PageInfo;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.Memo.MemoAuthority;
import com.seollem.server.memo.MemoDto;
import com.seollem.server.memo.MemoMapper;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolikes.MemoLikesService;
import com.seollem.server.util.GetCalenderBookUtil;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@Validated
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class BookController {

  private final GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  private final MemberService memberService;
  private final BookService bookService;
  private final BookMapper bookMapper;
  private final MemoService memoService;
  private final MemoMapper memoMapper;
  private final MemoLikesService memoLikesService;
  private final GetCalenderBookUtil getCalenderBookUtil;


  // 책 Library 조회
  @GetMapping("/library")
  public ResponseEntity getLibrary(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @RequestParam int page,
      @Positive @RequestParam int size,
      @RequestParam Book.BookStatus bookStatus) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    Page<Book> pageBooks =
        bookService.findVerifiedBooksByMemberAndBookStatus(
            page - 1, size, member, bookStatus, "bookId");
    List<Book> books = pageBooks.getContent();

    List<BookDto.LibraryResponse> responseList = bookMapper.BooksToLibraryResponse(books);

    for (int i = 0; i < books.size(); i++) {
      responseList.get(i).setMemoCount(memoService.getMemoCountWithBook(books.get(i)));
    }

    //        List<Book> classifiedBooks = bookService.classifyByBookStatus(books, bookStatus);

    return new ResponseEntity<>(
        new MultiResponseDto<>(responseList, pageBooks),
        HttpStatus.OK);
  }

  // 캘린더 뷰 조회
  @GetMapping("/calender")
  public ResponseEntity getCalender(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @RequestParam int page,
      @Positive @RequestParam int size,
      @Positive @RequestParam int year, @Min(1) @Max(12) @RequestParam int month) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    ArrayList<LocalDateTime> calenderPeriod =
        getCalenderBookUtil.getCalenderBookPeriod(year, month);

    Page<Book> pageBooks =
        bookService.findCalenderBooks(
            page - 1, size, member, calenderPeriod.get(0), calenderPeriod.get(1),
            Book.BookStatus.DONE, "read_end_date");
    List<Book> books = pageBooks.getContent();

    return new ResponseEntity<>(
        new MultiResponseDto<>(bookMapper.BooksToCalenderResponse(books), pageBooks),
        HttpStatus.OK);
  }

  // 오래된 책 조회
  @GetMapping("/abandon")
  public ResponseEntity getAbandon(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @RequestParam int page,
      @Positive @RequestParam int size
  ) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    Page<Book> pageBooks =
        bookService.findAbandonedBooks(page - 1, size, member);
    List<Book> books = pageBooks.getContent();

    //        List<Book> abandonedBooks = bookService.findAbandonedBooks(books);

    return new ResponseEntity<>(
        new MultiResponseDto<>(bookMapper.BooksToAbandonResponse(books), pageBooks),
        HttpStatus.OK);
  }

  // 메모 존재하는 책 조회
  @GetMapping("/memo-books")
  public ResponseEntity getBooksHaveMemo(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @RequestParam int page,
      @Positive @RequestParam int size) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    List<Book> books = bookService.findVerifiedBooksByMember(member);
    List<Book> filteredBooks =
        books.stream().filter(book -> memoService.getMemoCountWithBook(book) > 0)
            .collect(Collectors.toList());

    List<BooksHaveMemoResponse> responseList = bookMapper.BooksToMemoBooksResponse(filteredBooks);

    for (int i = 0; i < filteredBooks.size(); i++) {
      responseList.get(i).setMemoCount(memoService.getMemoCountWithBook(filteredBooks.get(i)));
    }

    List<BooksHaveMemoResponse> pagedResponseList = new ArrayList<>();
    for (int i = size * (page - 1); i < size * (page - 1) + size; i++) {
      if (i > responseList.size() - 1) {
        break;
      }
      pagedResponseList.add(responseList.get(i));
    }

    PageInfo pageInfo = new PageInfo().builder()
        .page(page)
        .size(size)
        .totalElements(responseList.size())
        .totalPages(responseList.size() / size + 1)
        .build();

    return new ResponseEntity<>(
        new MultiResponseDtoForBooksHaveMemo<>(pagedResponseList, pageInfo),
        HttpStatus.OK);
  }

  // 책 상세페이지 조회
  @GetMapping("/{book-id}")
  public ResponseEntity getBookDetail(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @PathVariable("book-id") long bookId,
      @RequestParam Memo.MemoAuthority memoAuthority) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    bookService.verifyMemberHasBook(bookId, member.getMemberId());

    Book book = bookService.findVerifiedBookById(bookId);

    BookDto.DetailResponse result = bookMapper.BookToBookDetailResponse(book);

    result.setMemoCount(memoService.getMemoCountWithBook(book));

    List<Memo> memos;
    if (memoAuthority == MemoAuthority.ALL) {
      memos = memoService.getMemosWithBook(book);
    } else {
      memos = memoService.getMemoWithBookAndMemoAuthority(book, memoAuthority);
    }

    List<MemoDto.Response> responseList = memoMapper.memoToMemoResponses(memos);

    for (int i = 0; i < memos.size(); i++) {
      responseList.get(i)
          .setMemoLikesCount(memoLikesService.getMemoLikesCountWithMemo(memos.get(i)));
    }

    result.setMemosList(responseList);

    return new ResponseEntity(result, HttpStatus.OK);
  }

  // 책의 메모 조회 - 타입별
  @GetMapping("/{book-id}/memos")
  public ResponseEntity getBookMemos(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @PathVariable("book-id") long bookId,
      @Positive @RequestParam int page,
      @Positive @RequestParam int size,
      @RequestParam Memo.MemoType memoType) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    bookService.verifyMemberHasBook(bookId, member.getMemberId());
    Book book = bookService.findVerifiedBookById(bookId);

    Page<Memo> memoTypeList;
    if (memoType == Memo.MemoType.ALL) {
      memoTypeList = memoService.getMemosWithBook(page - 1, size, book);
    } else {
      memoTypeList = memoService.getMemosWithBookAndMemoTypes(page - 1, size, book, memoType);
    }
    List<Memo> memos = memoTypeList.getContent();
    List<MemoDto.Response> responseList = memoMapper.memoToMemoResponses(memos);
    for (int i = 0; i < memos.size(); i++) {
      responseList.get(i)
          .setMemoLikesCount(memoLikesService.getMemoLikesCountWithMemo(memos.get(i)));
    }
    //        BookDto.MemosOfBook response = bookMapper.BookToMemosOfBookResponse(book);
    //        response.setMemosList(memoTypeList);
    return new ResponseEntity<>(
        new MultiResponseDto<>(responseList, memoTypeList),
        HttpStatus.OK);
  }

  // 책 등록
  @PostMapping
  public ResponseEntity postBook(
      @RequestHeader Map<String, Object> requestHeader,
      @Valid @RequestBody BookDto.Post requestBody) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);
    Book book = bookMapper.BookPostToBook(requestBody);
    Book verifiedBookStatusBook = bookService.verifyBookStatus(book);
    verifiedBookStatusBook.setMember(member);

    Book createdBook = bookService.createBook(verifiedBookStatusBook);

    return new ResponseEntity(
        bookMapper.BookToBookPostResponse(createdBook), HttpStatus.CREATED);
  }

  // 책 수정
  @PatchMapping("/{book-id}")
  public ResponseEntity patchBook(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @PathVariable("book-id") long bookId,
      @Valid @RequestBody BookDto.Patch requestBody) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    Book book = bookMapper.BookPatchToBook(requestBody);

    Book verifiedBookStatusBook = bookService.verifyBookStatus(book);
    bookService.verifyMemberHasBook(bookId, member.getMemberId());

    verifiedBookStatusBook.setBookId(bookId);
    Book updatedBook = bookService.updateBook(verifiedBookStatusBook);

    return new ResponseEntity(bookMapper.BookToBookPatchResponse(updatedBook), HttpStatus.OK);
  }

  // 책 삭제
  @DeleteMapping("/{book-id}")
  public ResponseEntity deleteBook(
      @RequestHeader Map<String, Object> requestHeader,
      @Positive @PathVariable("book-id") long bookId) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    bookService.verifyMemberHasBook(bookId, member.getMemberId());

    bookService.deleteBook(bookId);

    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  // 책 등록일자 수정 -> '오래된 책' 기능 테스트를 위한 api
  @PatchMapping("/created-date/{book-id}")
  public ResponseEntity modifyCreateDate(
      @RequestBody String time, @PathVariable("book-id") long bookId) {

    bookService.modifyCreateDate(LocalDateTime.parse(time), bookId);

    return new ResponseEntity(HttpStatus.OK);
  }
}
