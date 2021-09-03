package com.nayki.analyzer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nayki.analyzer.models.Analysis;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ErrorUtils {

    @Inject
    CacheUtils cacheUtils;

    public void setError(Analysis analysis) throws JsonProcessingException {
        cacheUtils.set(analysis.getId(), analysis);
    }
}
