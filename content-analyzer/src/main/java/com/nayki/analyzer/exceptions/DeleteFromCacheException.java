package com.nayki.analyzer.exceptions;

public class DeleteFromCacheException extends RuntimeException {

    private static final long serialVersionUID = -8108147025252352417L;

    public DeleteFromCacheException(String id) {
        super(String.format("Item with the id (%s) cannot be deleted from the cache", id));
    }
}

