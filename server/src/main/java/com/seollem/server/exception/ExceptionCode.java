package com.seollem.server.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(400, "Member already exists"),
    BOOK_NOT_FOUND(404, "Book not found"),
    QUESTION_EXISTS(409,"Question already exists"),
    ANSWER_NOT_FOUND_BY_ID(404, "Answer not found"),
//    ANSWER_NOT_FOUND_BY_BODY(404, "Answer not found, by Body"),
    ANSWER_EXISTS(409, "Answer already exists"),
    TAG_NOT_FOUND(404,"Tag not found");



    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
