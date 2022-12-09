package com.seollem.server.memo;


import com.seollem.server.book.entity.Book;
import com.seollem.server.book.service.BookService;
import com.seollem.server.exception.BusinessLogicException;
import com.seollem.server.exception.ExceptionCode;
import com.seollem.server.member.entity.Member;
import com.seollem.server.util.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    public final MemoRepository memoRepository;

    private final CustomBeanUtils<Memo> beanUtils;

    private final BookService bookService;

    private final MemoMapper mapper;

    public Memo createMemo(Memo memo){
        verifyMemo(memo);
        Memo saveMemo = memoRepository.save(memo);
        return saveMemo;
    }

    public Memo updateMemo(Memo memo){
            Memo findMemo = findVerifiedMemo(memo.getMemoId());
            Memo updatingMemo = beanUtils.copyNonNullProperties(memo, findMemo);
            return memoRepository.save(updatingMemo);
    }

    public List<Memo> randomMemo(Member member){
        List<Memo> random = memoRepository.findAllByMember(member);
        return random;
    }

    public List<MemoDto.Response> getMemos(Book book){
        List<Memo> memoList = memoRepository.findALLByBook(book);
        List<MemoDto.Response> result = mapper.memoToMemoResponses(memoList);
        return result;
    }

    public Page<Memo> getBookAndMemoTypes(int page, int size, Book book, Memo.MemoType memoType){
        Page<Memo> memoTypeList = memoRepository.findAllByBookAndMemoType(PageRequest.of(page, size,
                Sort.by("memoId").descending()),book,memoType);
//        List<MemoDto.Response> typeList = mapper.memoToMemoResponses(memoTypeList);
        return memoTypeList;
    }

    public Page<Memo> getBookAndMemo(int page, int size, Book book){
        Page<Memo> memoTypeList = memoRepository.findAllByBook(PageRequest.of(page, size,
                Sort.by("memoId").descending()),book);
        return memoTypeList;
    }


    public void deleteMemo(long memoId){
        Memo memo = findVerifiedMemo(memoId);
        Book book = memo.getBook();
        book.setMemoCount(book.getMemoCount()-1);
        memoRepository.delete(memo);
    }


    //메모가 있는지 확인
    @Transactional(readOnly = true)
    public Memo findVerifiedMemo(long memoId){
        Optional<Memo> optionalMemo = memoRepository.findById(memoId);
        Memo findMemo = optionalMemo.orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMO_NOT_FOUND));
        return findMemo;
    }

    //책이 있는지 확인
    private void verifyMemo(Memo memo){
        Book book = bookService.findVerifiedBookById(memo.getBook().getBookId());
        book.setMemoCount(book.getMemoCount()+1);
        bookService.updateBook(book);

    }


    public void verifyMemberHasMemo(long memoId, long memberId){
        Memo memo = findVerifiedMemo(memoId);
        if(memo.getMember().getMemberId()!=memberId){
            throw new BusinessLogicException(ExceptionCode.NOT_MEMBER_MEMO);
        }
    }




    //    public Page<Memo> findAllByMemoType(int page,int size, Memo.MemoType memoType){
//        Page<Memo> memos = memoRepository.findAllByMemoType(memoType, PageRequest.of(page, size,
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
