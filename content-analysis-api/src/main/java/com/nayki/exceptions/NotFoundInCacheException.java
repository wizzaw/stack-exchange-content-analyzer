package com.nayki.exceptions;

public class NotFoundInCacheException extends RuntimeException {

    private static final long serialVersionUID = -8108147025252352417L;

    public NotFoundInCacheException(String obj, String id) {
        super(String.format("%s not found in the cache for the id: %s", obj, id));
    }
}

