package com.seollem.server.member;

import com.seollem.server.book.Book;
import com.seollem.server.book.BookService;
import com.seollem.server.file.FileUploadService;
import com.seollem.server.member.dto.HallOfFameInnerDto;
import com.seollem.server.member.dto.MemberDto;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookDto;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookMemoDto;
import com.seollem.server.member.dto.othermemberbook.OtherMemberBookResponseDto;
import com.seollem.server.member.dto.othermemberprofile.OtherMemberProfileResponseDto;
import com.seollem.server.memo.Memo;
import com.seollem.server.memo.Memo.MemoAuthority;
import com.seollem.server.memo.MemoService;
import com.seollem.server.memolike.MemoLike;
import com.seollem.server.memolike.MemoLikeService;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/members")
@Validated
@RequiredArgsConstructor
public class MemberController {

  private final MemberMapper memberMapper;
  private final MemberService memberService;
  private final MemoService memoService;
  private final BookService bookService;
  private final MemoLikeService memoLikeService;
  private final GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  private final FileUploadService fileUploadService;


  @PostMapping("/member-image")
  public ResponseEntity postMemberImage(@RequestHeader Map<String, Object> requestHeader,
      @RequestPart MultipartFile file) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    String url = fileUploadService.createImage(file);
    memberService.updateMemberImage(member, url);

    MemberDto.ImageMemberResponse imageMemberResponse = new MemberDto.ImageMemberResponse(url);

    return new ResponseEntity<>(imageMemberResponse, HttpStatus.CREATED);
  }


  @GetMapping(path = "/me")
  public ResponseEntity getMember(@RequestHeader Map<String, Object> requestHeader) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    return new ResponseEntity<>(memberMapper.memberToMemberGetResponse(member), HttpStatus.OK);
  }


  @GetMapping(path = "/hall-of-fame")
  public ResponseEntity getHallOfFame(@RequestHeader Map<String, Object> requestHeader) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    List<HallOfFameInnerDto> dtosWithBook = memberService.getHallOfFameWithBook();
    List<HallOfFameInnerDto> dtosWithMemo = memberService.getHallOfFameWithMemo();

    return new ResponseEntity(new MemberDto.HallOfFameResponse<>(dtosWithBook, dtosWithMemo),
        HttpStatus.OK);
  }


  @GetMapping(path = "/other/{member-id}")
  public ResponseEntity getOtherMemberProfile(@RequestHeader Map<String, Object> requestHeader,
      @Positive @PathVariable("member-id") long memberId, @Positive @RequestParam int page,
      @Positive @RequestParam int size) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);

    Member findMember = memberService.findVerifiedMemberById(memberId);

    OtherMemberProfileResponseDto result =
        memberService.getOtherMemberProfile(page - 1, size, findMember);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }


  @GetMapping(path = "/other/books/{book-id}")
  public ResponseEntity getOtherMemberBook(@RequestHeader Map<String, Object> requestHeader,
      @Positive @PathVariable("book-id") long bookId, @Positive @RequestParam int page,
      @Positive @RequestParam int size) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    Member member = memberService.findVerifiedMemberByEmail(email);
    Book book = bookService.findVerifiedBookById(bookId);

    OtherMemberBookDto otherMemberBookDto = bookService.getOtherMemberBook(bookId);

    Page<OtherMemberBookMemoDto> pageOtherMemberBookMemoDtos =
        memoService.getOtherMemberBookMemosWithBook(page - 1, size, book);
    List<OtherMemberBookMemoDto> otherMemberBookMemoDtos = pageOtherMemberBookMemoDtos.getContent();

    Page<Memo> pageMemoList = memoService.getPageMemosWithBookAndMemoAuthority(page - 1, size, book,
        MemoAuthority.PUBLIC);
    List<Memo> memoList = pageMemoList.getContent();
    List<MemoLike> doneMemoLikesList = memoLikeService.findMemoLikesDone(memoList, member);
    List<Integer> memoLikesCountList = memoLikeService.getMemoLikesCountWithMemos(memoList);

    OtherMemberBookResponseDto result =
        memberMapper.toOtherMemberBookResponseDto(otherMemberBookDto, otherMemberBookMemoDtos,
            doneMemoLikesList,
            memoLikesCountList, pageOtherMemberBookMemoDtos);

    return new ResponseEntity(result, HttpStatus.OK);
  }


  @PatchMapping("/me")
  public ResponseEntity patchMember(@RequestHeader Map<String, Object> requestHeader,
      @Valid @RequestBody MemberDto.Patch requestBody) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
    if (email.equals("starrypro@gmail.com")) {
      return new ResponseEntity<>("게스트용 아이디는 수정할 수 없어요", HttpStatus.BAD_REQUEST);
    } else {
      Member findMember = memberService.findVerifiedMemberByEmail(email);
      Member patchMember = memberMapper.memberPatchToMember(requestBody);

      patchMember.setEmail(findMember.getEmail());

      Member member = memberService.updateMember(patchMember);

      return new ResponseEntity<>(memberMapper.memberToMemberPatchResponse(member), HttpStatus.OK);
    }
  }

  @DeleteMapping("/me")
  public ResponseEntity patchMember(@RequestHeader Map<String, Object> requestHeader) {
    String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);

    if (email.equals("starrypro@gmail.com")) {
      return new ResponseEntity<>("게스트용 아이디는 삭제할 수 없어요", HttpStatus.BAD_REQUEST);
    } else {
      memberService.deleteMember(email);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }
}
