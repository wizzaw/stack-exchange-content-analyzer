package com.nayki.api.exceptions;

public class JsonProcessingExceptionInCache extends RuntimeException {

    private static final long serialVersionUID = -8108147025252352422L;

    public JsonProcessingExceptionInCache(String obj, String id) {
        super(String.format("JSON processing for the %s cannot be completed successfully for the id: %s", obj, id));
    }
}
