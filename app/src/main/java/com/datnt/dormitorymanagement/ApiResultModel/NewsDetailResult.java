package com.datnt.dormitorymanagement.ApiResultModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsDetailResult {

    @SerializedName("newsId")
    @Expose
    private Integer newsId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("attachedFileUrl")
    @Expose
    private String attachedFileUrl;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getAttachedFileUrl() {
        return attachedFileUrl;
    }

    public void setAttachedFileUrl(String attachedFileUrl) {
        this.attachedFileUrl = attachedFileUrl;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}