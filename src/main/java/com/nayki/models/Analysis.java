package com.nayki.models;

import java.util.UUID;

public class Analysis {
    private String id;
    private String summaryId;
    private String detailId;
    private String url;

    public Analysis(String url) {
        this.id = UUID.randomUUID().toString();
        this.summaryId = UUID.randomUUID().toString();
        this.detailId = UUID.randomUUID().toString();
        this.url = url;
    }

    public Analysis(String id, String summaryId, String detailId, String url) {
        this.id = id;
        this.summaryId = summaryId;
        this.detailId = detailId;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(String summaryId) {
        this.summaryId = summaryId;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Analysis{" +
                "id='" + id + '\'' +
                ", summaryId='" + summaryId + '\'' +
                ", detailId='" + detailId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
