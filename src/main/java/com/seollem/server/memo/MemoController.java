package com.seollem.server.memo;

import com.seollem.server.book.Book;
import com.seollem.server.book.BookService;
import com.seollem.server.file.FileUploadService;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberService;
import com.seollem.server.memo.MemoDto.ImageMemoResponse;
import com.seollem.server.memolikes.MemoLikesService;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/memos")
@Slf4j
@Validated
@RequiredArgsConstructor
public class MemoController {

  private final MemoService memoService;
  private final MemoMapper memoMapper;
  private final MemberService memberService;
  private final GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  private final BookService bookService;
  private final FileUploadService fileUploadService;
  private final MemoLikesService memoLikesService;

  @PostMapping("/{book-id}")
  public ResponseEntity postMemo(@RequestHeader Map<String, Object> requestHeader,
      @Valid @RequestBody MemoDto.Post post, @Positive @PathVariable("book-id") long bookId) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    bookService.verifyMemberHasBook(bookId, member.getMemberId());

    Memo memoOfBook = memoMapper.memoPostToMemo(post);
    Book book = bookService.findVerifiedBookById(bookId);
    memoOfBook.setMember(member);
    memoOfBook.setBook(book);

    Memo memo = memoService.createMemo(memoOfBook);
    MemoDto.Response response = memoMapper.memoToMemoResponse(memo);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  // 이미지 등록
  @PostMapping("/image-memo")
  public ResponseEntity postImageMemo(@RequestHeader Map<String, Object> requestHeader,
      @RequestPart MultipartFile file) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    String url = fileUploadService.createImageMemo(file);

    MemoDto.ImageMemoResponse imageMemoResponse = new ImageMemoResponse(url);

    return new ResponseEntity<>(imageMemoResponse, HttpStatus.CREATED);
  }

  @PatchMapping("/{memo-id}")
  public ResponseEntity patchMemo(@RequestHeader Map<String, Object> requestHeader,
      @PathVariable("memo-id") @Positive long memoId, @Valid @RequestBody MemoDto.Patch patch) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    memoService.verifyMemberHasMemo(memoId, member.getMemberId());

    patch.setMemoId(memoId);
    Memo PatchMemo = memoMapper.memoPatchToMemo(patch);

    Memo memo = memoService.updateMemo(PatchMemo);
    MemoDto.Response response = memoMapper.memoToMemoResponse(memo);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/random")
  public ResponseEntity randomMemo(@RequestHeader Map<String, Object> requestHeader) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    Memo randomMemo = memoService.randomMemo(member);
//    List<MemoDto.RandomResponse> responses =
//        randomMemo.stream().map(memo -> mapper.memoToRandomMemoResponse(memo)).collect(Collectors.toList());

    return new ResponseEntity<>(memoMapper.memoToRandomMemoResponse(randomMemo), HttpStatus.OK);
  }

  @DeleteMapping("/{memo-id}")
  public ResponseEntity deleteMemo(@RequestHeader Map<String, Object> requestHeader,
      @PathVariable("memo-id") @Positive long memoId) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    memoService.verifyMemberHasMemo(memoId, member.getMemberId());

    memoService.deleteMemo(memoId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
