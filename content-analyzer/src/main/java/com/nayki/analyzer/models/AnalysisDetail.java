package com.nayki.analyzer.models;

import java.util.HashMap;
import java.util.Map;

public class AnalysisDetail {
    private String id;
    private AnalysisState state;
    private String failedSummary;
    private final Map<String, Post> posts;

    public AnalysisDetail(String id) {
        this.id = id;
        this.posts = new HashMap<>();
    }

    public AnalysisDetail(String id, AnalysisState state, String failedSummary) {
        this.id = id;
        this.state = state;
        this.failedSummary = failedSummary;
        this.posts = new HashMap<>();
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

    public void addPost(Post post) {
        this.posts.put(post.getId(), post);
    }

    public Post getPost(String postId) {
        return posts.get(postId);
    }

    public Map<String, Post> getPosts() {
        return posts;
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
