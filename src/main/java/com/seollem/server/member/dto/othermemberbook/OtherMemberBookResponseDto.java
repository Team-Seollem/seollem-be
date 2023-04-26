package com.seollem.server.member.dto.othermemberbook;

import com.seollem.server.book.Book.BookStatus;
import com.seollem.server.globaldto.PageInfo;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OtherMemberBookResponseDto {

  private String title;
  private String author;
  private String cover;
  private String publisher;
  private int itemPage;
  private int currentPage;
  private int star;
  private BookStatus bookStatus;

  private List<OtherMemberBookMemoDto> memoList;

  private PageInfo pageInfo;


}
