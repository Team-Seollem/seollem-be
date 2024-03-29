package com.seollem.server.book;

import com.seollem.server.book.BookDto.BooksHaveMemoResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

  Book BookPostToBook(BookDto.Post requestBody);

  Book BookPatchToBook(BookDto.Patch requestBody);

  BookDto.PostResponse BookToBookPostResponse(Book book);

  BookDto.PatchResponse BookToBookPatchResponse(Book book);

  BookDto.DetailResponse BookToBookDetailResponse(Book book);

  BookDto.MemosOfBook BookToMemosOfBookResponse(Book book);

  @Mapping(target = "memoCount", ignore = true)
  List<BookDto.LibraryResponse> BooksToLibraryResponse(List<Book> books);

  List<BookDto.CalenderResponse> BooksToCalenderResponse(List<Book> books);

  List<BookDto.AbandonResponse> BooksToAbandonResponse(List<Book> books);

  @Mapping(target = "memoCount", ignore = true)
  List<BooksHaveMemoResponse> BooksToMemoBooksResponse(List<Book> books);
}
