package com.nayki.analyzer.exceptions;

public class NotFoundInCacheException extends RuntimeException {

    private static final long serialVersionUID = -8108147025252352419L;

    public NotFoundInCacheException(String obj, String id) {
        super(String.format("%s not found in the cache for the id: %s", obj, id));
    }
}

