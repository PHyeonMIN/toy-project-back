package com.example.toyproject.exception.CustomException;

import com.example.toyproject.exception.GlobalException;

/**
 * status -> 404
 */
public class PostNotFound extends GlobalException {

    private static final String MESSAGE = "존재하지 않는 페이지입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
