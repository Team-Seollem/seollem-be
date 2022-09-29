package com.seollem.server.book.controller;

import com.seollem.server.book.dto.BookDto;
import com.seollem.server.book.entity.Book;
import com.seollem.server.book.mapper.BookMapper;
import com.seollem.server.book.service.BookService;
import com.seollem.server.globaldto.MultiResponseDto;
import com.seollem.server.member.entity.Member;
import com.seollem.server.member.service.MemberService;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.MemoDto;
import com.seollem.server.memo.MemoService;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
@Validated
@SuppressWarnings("unchecked")
public class BookController {

    private final GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
    private final MemberService memberService;
    private final BookService bookService;
    private final BookMapper bookMapper;

    private final MemoService memoService;

    public BookController(GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil, MemberService memberService, BookService bookService, BookMapper bookMapper, MemoService memoService) {
        this.getEmailFromHeaderTokenUtil = getEmailFromHeaderTokenUtil;
        this.memberService = memberService;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.memoService = memoService;
    }

    //서재 뷰 조회
    @GetMapping("/library")
    public ResponseEntity getLibrary(@RequestHeader Map<String, Object> requestHeader,
                                     @Positive @RequestParam int page,
                                     @Positive @RequestParam int size,
                                     @RequestParam Book.BookStatus bookStatus){
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        Member member = memberService.findVerifiedMemberByEmail(email);

        Page<Book> pageBooks = bookService.findVerifiedBooksByMemberAndBookStatus(page-1, size, member, bookStatus, "bookId");
        List<Book> books = pageBooks.getContent();

//        List<Book> classifiedBooks = bookService.classifyByBookStatus(books, bookStatus);

        return new ResponseEntity<>(
                new MultiResponseDto<>(bookMapper.BooksToLibraryResponse(books), pageBooks), HttpStatus.OK);
    }

    // 캘린더 뷰 조회
    @GetMapping("/calender")
    public ResponseEntity getCalender(@RequestHeader Map<String, Object> requestHeader,
                                      @Positive @RequestParam int page,
                                      @Positive @RequestParam int size){
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        Member member = memberService.findVerifiedMemberByEmail(email);

        Page<Book> pageBooks = bookService.findVerifiedBooksByMemberAndBookStatus(page-1, size, member, Book.BookStatus.DONE, "readEndDate");
        List<Book> books = pageBooks.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(bookMapper.BooksToCalenderResponse(books), pageBooks), HttpStatus.OK);

    }

    //오래된 책 조회
    @GetMapping("/abandon")
    public ResponseEntity getAbandon(@RequestHeader Map<String, Object> requestHeader,
                                      @Positive @RequestParam int page,
                                      @Positive @RequestParam int size) {
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        Member member = memberService.findVerifiedMemberByEmail(email);

        Page<Book> pageBooks = bookService.findVerifiedBooksByMember(page-1, size, member);
        List<Book> books = pageBooks.getContent();

//        List<Book> abandonedBooks = bookService.findAbandonedBooks(books);

        return new ResponseEntity<>(
                new MultiResponseDto<>(bookMapper.BooksToAbandonResponse(books), pageBooks), HttpStatus.OK);

    }


    //책 상세페이지 조회
    @GetMapping("/{book-id}")
    public ResponseEntity getBookDetail(@RequestHeader Map<String, Object> requestHeader,
                                        @Positive @PathVariable("book-id") long bookId) {
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        Member member = memberService.findVerifiedMemberByEmail(email);

        bookService.verifyMemberHasBook(bookId, member.getMemberId());

        Book book = bookService.findVerifiedBookById(bookId);

        //Book.setMemo(); 구현 필요
        List<MemoDto.Response> memosList = memoService.getMemos(book);
        BookDto.DetailResponse result = bookMapper.BookToBookDetailResponse(book);
        result.setMemosList(memosList);

        return new ResponseEntity(result, HttpStatus.OK);
    }
    //책 메모 조회 - 리스트로
    @GetMapping("/{book-id}/memos")
    public ResponseEntity getBookMemos(@RequestHeader Map<String, Object> requestHeader,
                                       @Positive @PathVariable("book-id") long bookId,
                                       @RequestParam Memo.MemoType memoType){
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        Member member = memberService.findVerifiedMemberByEmail(email);

        bookService.verifyMemberHasBook(bookId, member.getMemberId());
        Book book = bookService.findVerifiedBookById(bookId);

        List<MemoDto.Response> memoTypeList = memoService.getBookAndMemoTypes(book,memoType);
        BookDto.MemosOfBook response = bookMapper.BookToMemosOfBookResponse(book);
        response.setMemosList(memoTypeList);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    //책 등록
    @PostMapping
    public ResponseEntity postBook(@RequestHeader Map<String, Object> requestHeader,
                                   @Valid @RequestBody BookDto.Post requestBody){
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        Member member = memberService.findVerifiedMemberByEmail(email);
        Book book = bookMapper.BookPostToBook(requestBody);
        Book verifiedBookStatusBook = bookService.verifyBookStatus(book);
        verifiedBookStatusBook.setMember(member);

        Book createdBook = bookService.createBook(verifiedBookStatusBook);

        return new ResponseEntity(bookMapper.BookToBookPostResponse(createdBook), HttpStatus.CREATED);
    }

    //책 수정
    @PatchMapping("/{book-id}")
    public ResponseEntity patchBook(@RequestHeader Map<String, Object> requestHeader,
                                    @Positive @PathVariable("book-id") long bookId,
                                    @Valid @RequestBody BookDto.Patch requestBody){
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        Member member = memberService.findVerifiedMemberByEmail(email);

        Book book = bookMapper.BookPatchToBook(requestBody);

        Book verifiedBookStatusBook = bookService.verifyBookStatus(book);
        bookService.verifyMemberHasBook(bookId, member.getMemberId());

        verifiedBookStatusBook.setBookId(bookId);
        Book updatedBook = bookService.updateBook(verifiedBookStatusBook);

        return new ResponseEntity(bookMapper.BookToBookPatchResponse(updatedBook), HttpStatus.OK);
    }

    //책 삭제
    @DeleteMapping("/{book-id}")
    public ResponseEntity deleteBook(@RequestHeader Map<String, Object> requestHeader,
                                     @Positive @PathVariable("book-id") long bookId){
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        Member member = memberService.findVerifiedMemberByEmail(email);

        bookService.verifyMemberHasBook(bookId, member.getMemberId());

        bookService.deleteBook(bookId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
