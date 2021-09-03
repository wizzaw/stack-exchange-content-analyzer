package com.nayki.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nayki.api.models.AnalysisSummary;
import com.nayki.api.utils.CacheUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SummaryService {

    @Inject
    CacheUtils cacheUtils;

    public void set(final AnalysisSummary summary) throws JsonProcessingException {
        cacheUtils.set(summary.getId(), summary);
    }

    public AnalysisSummary get(String id) throws JsonProcessingException {
        return cacheUtils.get(id, AnalysisSummary.class);
    }

    public void delete(String id) {
        cacheUtils.delete(id);
    }
}
