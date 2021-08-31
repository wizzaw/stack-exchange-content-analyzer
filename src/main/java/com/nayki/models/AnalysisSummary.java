package com.nayki.models;

import java.util.Date;

public class AnalysisSummary {
    private String id;
    private AnalysisState state;
    private Date analyzeDate;
    private String failedSummary;
    private long duration;
    private Date firstPostDate;
    private Date lastPostDate;
    private int totalPostCount;
    private int totalAcceptedPostCount;
    private double averageScore;

    public AnalysisSummary(String id) {
        this.id = id;
        this.state = AnalysisState.ANALYZING;
    }

    public AnalysisSummary(String id, AnalysisState state, Date analyzeDate, String failedSummary, long duration, Date firstPostDate, Date lastPostDate, int totalPostCount, int totalAcceptedPostCount, double averageScore) {
        this.id = id;
        this.state = state;
        this.analyzeDate = analyzeDate;
        this.failedSummary = failedSummary;
        this.duration = duration;
        this.firstPostDate = firstPostDate;
        this.lastPostDate = lastPostDate;
        this.totalPostCount = totalPostCount;
        this.totalAcceptedPostCount = totalAcceptedPostCount;
        this.averageScore = averageScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getAnalyzeDate() {
        return analyzeDate;
    }

    public void setAnalyzeDate(Date analyzeDate) {
        this.analyzeDate = analyzeDate;
    }

    public AnalysisState getState() {
        return state;
    }

    public void setState(AnalysisState state) {
        this.state = state;
    }

    public String getFailedSummary() {
        return failedSummary;
    }

    public void setFailedSummary(String failedSummary) {
        this.failedSummary = failedSummary;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getFirstPostDate() {
        return firstPostDate;
    }

    public void setFirstPostDate(Date firstPostDate) {
        this.firstPostDate = firstPostDate;
    }

    public Date getLastPostDate() {
        return lastPostDate;
    }

    public void setLastPostDate(Date lastPostDate) {
        this.lastPostDate = lastPostDate;
    }

    public int getTotalPostCount() {
        return totalPostCount;
    }

    public void setTotalPostCount(int totalPostCount) {
        this.totalPostCount = totalPostCount;
    }

    public int getTotalAcceptedPostCount() {
        return totalAcceptedPostCount;
    }

    public void setTotalAcceptedPostCount(int totalAcceptedPostCount) {
        this.totalAcceptedPostCount = totalAcceptedPostCount;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }
}
