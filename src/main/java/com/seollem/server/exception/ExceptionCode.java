package com.seollem.server.exception;

import lombok.Getter;

public enum ExceptionCode {
  MEMBER_NOT_FOUND(404, "Member not found"), MEMBER_EXISTS(400, "Member already exists"),
  MEMBER_AUTHENTICATIONCODE_INVALID(404, "Member authentication code invalid "),
  BOOK_NOT_FOUND(404, "Book not found"), BOOK_EXISTS(400, "Book already exists"),

  BOOK_NOT_FOUND_PERIOD(404, "Books not found in period"),

  BOOK_STATUS_WRONG(400, "Wrong value in bookStatus and readDate"),
  NOT_MEMBER_BOOK(400, "Not member's book"), MEMO_NOT_FOUND(404, "Memo not found"),

  NOT_MEMBER_MEMO(400, "Not member's memo"),

  IMAGE_UPLOAD_FAIL(500, "Image upload failed.");

  @Getter
  private final int status;

  @Getter
  private final String message;

  ExceptionCode(int code, String message) {
    this.status = code;
    this.message = message;
  }
}
