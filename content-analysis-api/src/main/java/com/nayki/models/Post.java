package com.nayki.models;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private int id;
    private int score;
    private String body;
    List<String> childBodies;

    public Post() {
        childBodies = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getChildBodies() {
        return childBodies;
    }

    public void addChildBody(String childBody) {
        this.childBodies.add(childBody);
    }
}
