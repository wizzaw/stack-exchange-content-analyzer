package com.nayki.api.exceptions;

public class SetInCacheException extends RuntimeException {

    private static final long serialVersionUID = -8108147025252352420L;

    public SetInCacheException(String obj, String id) {
        super(String.format("%s cannot be set in to the cache for the id: %s", obj, id));
    }
}
