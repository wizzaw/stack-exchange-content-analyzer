package com.nayki.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nayki.models.AnalysisDetail;
import com.nayki.utils.CacheUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DetailService {

    @Inject
    CacheUtils cacheUtils;

    public void set(final AnalysisDetail detail) throws JsonProcessingException {
        cacheUtils.set(detail.getId(), detail);
    }

    public AnalysisDetail get(String id) throws JsonProcessingException {
        return cacheUtils.get(id, AnalysisDetail.class);
    }

    public void delete(String id) {
        cacheUtils.delete(id);
    }
}
