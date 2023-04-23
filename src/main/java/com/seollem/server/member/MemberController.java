package com.seollem.server.member;

import com.seollem.server.file.FileUploadService;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/members")
@Validated
public class MemberController {

  private final MemberMapper memberMapper;
  private final MemberService memberService;
  private final GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;
  private final FileUploadService fileUploadService;

  public MemberController(MemberMapper memberMapper, MemberService memberService,
      GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil,
      FileUploadService fileUploadService) {
    this.memberMapper = memberMapper;
    this.memberService = memberService;
    this.getEmailFromHeaderTokenUtil = getEmailFromHeaderTokenUtil;
    this.fileUploadService = fileUploadService;
  }


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
  public ResponseEntity getHallOfFame() {

    List<HallOfFameInnerDto> dtosWithBook = memberService.getHallOfFameWithBook();
    List<HallOfFameInnerDto> dtosWithMemo = memberService.getHallOfFameWithMemo();

    return new ResponseEntity(new MemberDto.HallOfFameResponse<>(dtosWithBook, dtosWithMemo),
        HttpStatus.OK);
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
