package com.seollem.server.memo;

import com.seollem.server.book.Book;
import com.seollem.server.book.BookService;
import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import com.seollem.server.member.Member;
import com.seollem.server.memo.Memo.MemoAuthority;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoService {

  public final MemoRepository memoRepository;


  private final BookService bookService;

  private final MemoMapper mapper;

  public Memo createMemo(Memo memo) {
    verifyMemo(memo);
    Memo saveMemo = memoRepository.save(memo);
    return saveMemo;
  }

  public Memo updateMemo(Memo patchMemo) {
    Memo findMemo = findVerifiedMemo(patchMemo.getMemoId());

    Optional.ofNullable(patchMemo.getMemoType())
        .ifPresent(memoType -> findMemo.setMemoType(memoType));
    Optional.ofNullable(patchMemo.getMemoContent())
        .ifPresent(memoContent -> findMemo.setMemoContent(memoContent));
    Optional.ofNullable(patchMemo.getMemoBookPage())
        .ifPresent(memoBookPage -> findMemo.setMemoBookPage(memoBookPage));
    Optional.ofNullable(patchMemo.getMemoLikesCount())
        .ifPresent(memoLikesCount -> findMemo.setMemoLikesCount(memoLikesCount));
    Optional.ofNullable(patchMemo.getMemoAuthority())
        .ifPresent(memoAuthority -> findMemo.setMemoAuthority(memoAuthority));

    return memoRepository.save(findMemo);
  }

  public Memo randomMemo(Member member) {
    Memo random = memoRepository.findRandomMemo(member);
    return random;
  }

  public List<Memo> getMemos(Book book) {
    List<Memo> memoList = memoRepository.findAllByBook(book);
    return memoList;
  }

  public Page<Memo> getBookAndMemoTypes(int page, int size, Book book, Memo.MemoType memoType) {
    Page<Memo> memoTypeList =
        memoRepository.findAllByBookAndMemoType(
            PageRequest.of(page, size, Sort.by("memoId").descending()), book, memoType);
    //        List<MemoDto.Response> typeList = mapper.memoToMemoResponses(memoTypeList);
    return memoTypeList;
  }

  public Page<Memo> getBookAndMemo(int page, int size, Book book) {
    Page<Memo> memoTypeList =
        memoRepository.findAllByBook(
            PageRequest.of(page, size, Sort.by("memoId").descending()), book);
    return memoTypeList;
  }

  public List<Memo> getMemoWithAuthority(MemoAuthority memoAuthority) {
    List<Memo> list =
        memoRepository.findAllByMemoAuthority(memoAuthority);
    return list;
  }

  public void deleteMemo(long memoId) {
    Memo memo = findVerifiedMemo(memoId);
    Book book = memo.getBook();

    memoRepository.delete(memo);
  }

  // 메모가 있는지 확인
  @Transactional(readOnly = true)
  public Memo findVerifiedMemo(long memoId) {
    Optional<Memo> optionalMemo = memoRepository.findById(memoId);
    Memo findMemo =
        optionalMemo.orElseThrow(
            () -> new BusinessLogicException(ExceptionCode.MEMO_NOT_FOUND));
    return findMemo;
  }

  // 책이 있는지 확인
  private void verifyMemo(Memo memo) {
    Book book = bookService.findVerifiedBookById(memo.getBook().getBookId());
    bookService.updateBook(book);
  }

  public void verifyMemberHasMemo(long memoId, long memberId) {
    Memo memo = findVerifiedMemo(memoId);
    if (memo.getMember().getMemberId() != memberId) {
      throw new BusinessLogicException(ExceptionCode.NOT_MEMBER_MEMO);
    }
  }

  //    public Page<Memo> findAllByMemoType(int page,int size, Memo.MemoType memoType){
  //        Page<Memo> memos = memoRepository.findAllByMemoType(memoType, PageRequest.of(page,
  // size,
  //                        Sort.by("memoId").descending()));
  //    }

  //    public void verifyMemberHasBook(long memoId,long bookId, long memberId){
  //
  //        Book book = findVerifiedBookById(bookId);
  //        if(book.getMember().getMemberId()!=memberId){
  //            throw new BusinessLogicException(ExceptionCode.NOT_MEMBER_BOOK);
  //        }
  //    }

}
