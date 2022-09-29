package com.seollem.server.memo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MemoDto {

    @Getter
    @Setter
    // @AllArgsConstructor
    public static class Post {

        @NotBlank(message = "메모는 공백이 아니어야 합니다.")
        private String  memoContent;

        private int memoBookPage;

        private Memo.MemoType memoType;
        //@Positive
//        private long bookId;
//
//
//        public Book getBook(){
//            Book book = new Book();
//            book.setBookId(bookId);
//            return book;
//        }


    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch{
        private long memoId;

        @NotBlank(message = "메모는 공백이 아니어야 합니다.")
        private String  memoContent;

        private Memo.MemoType memoType;

        private int memoBookPage;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RandomResponse{
        private long memoId;
        private String memoContent;
        private Memo.MemoType memoType;
        private int memoBookPage;
//        private long bookId;
//
//        public void setBook(Book book){
//            this.bookId = book.getBookId();
//        }

    }



    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
      //  private long memoId;
        private Memo.MemoType memoType;
        private String memoContent;
        private int memoBookPage;
//        private LocalDateTime createdAt;
//        private LocalDateTime updateAt;
//        private long bookId;
//
//        public void setBook(Book book){
//            this.bookId = book.getBookId();
//        }

    }
}
