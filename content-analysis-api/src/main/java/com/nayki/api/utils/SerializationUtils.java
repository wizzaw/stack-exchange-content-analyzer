package com.nayki.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SerializationUtils {

    @Inject
    ObjectMapper mapper;

    public String toJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    public <T> T toObject(String jsonString, Class<T> cls) throws JsonProcessingException {
        return mapper.readValue(jsonString, cls);
    }
}
