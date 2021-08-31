package com.nayki.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nayki.models.Analysis;
import com.nayki.models.AnalysisDetail;
import com.nayki.models.AnalysisSummary;
import com.nayki.utils.CacheUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AnalysisService {

    @Inject
    CacheUtils cacheUtils;

    @Inject
    SummaryService summaryService;

    @Inject
    DetailService detailService;

    public String createAnalysis(String url) throws JsonProcessingException {
        final Analysis analysis = new Analysis(url);
        this.set(analysis);
        summaryService.set(new AnalysisSummary(analysis.getSummaryId()));
        detailService.set(new AnalysisDetail(analysis.getDetailId()));
        return analysis.getId();
    }

    private void set(final Analysis analysis) throws JsonProcessingException {
        cacheUtils.set(analysis.getId(), analysis);
    }

    public Analysis get(String id) throws JsonProcessingException {
        return cacheUtils.get(id, Analysis.class);
    }

    public void delete(String id) {
        cacheUtils.delete(id);
    }
}
