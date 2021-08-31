package com.nayki.models;

import java.util.List;

public class AnalysisDetail {
    private String id;
    private AnalysisState state;
    private String failedSummary;
    private List<Post> posts;

    public AnalysisDetail(String id) {
        this.id = id;
    }

    public AnalysisDetail(String id, AnalysisState state, String failedSummary, List<Post> posts) {
        this.id = id;
        this.state = state;
        this.failedSummary = failedSummary;
        this.posts = posts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "AnalysisDetail{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", failedSummary='" + failedSummary + '\'' +
                ", posts=" + posts.toString() +
                '}';
    }
}
