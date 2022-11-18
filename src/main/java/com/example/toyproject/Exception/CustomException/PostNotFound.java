package com.example.toyproject.Exception.CustomException;

import com.example.toyproject.Exception.GlobalException;

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
