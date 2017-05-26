package com.frontpagenews.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection="articles")
public class ArticleModel {
    @Id
    private String id;

    @Indexed(unique = true)
    private String title;

    private String content;

    private int contentLength;

    private String summary;

    private String imageUrl;

    private int imageWidth;

    private int imageHeight;

    private String tag;

    private List<String> sourceTags;

    private SourceModel source;

    private String videoUrl;

    private String language;

    public ArticleModel() {
        this.title = " ";
        this.content = " ";
        this.contentLength = 1;
        this.summary = "";
        this.imageUrl = " ";
        this.imageWidth = 0;
        this.imageHeight = 0;
        this.tag = "";
        this.sourceTags = new ArrayList<String>();
        this.source = new SourceModel();
        this.videoUrl = " ";
        this.language = "ENGLISH";
    }

    public ArticleModel(String title, String content, int contentLength, String imageUrl, int imageHeight, int imageWidth, String tag, List<String> sourceTags, SourceModel source, String videoUrl, String language) {
        this.title = title;
        this.content = content;
        this.contentLength = contentLength;
        this.summary = "";
        this.imageUrl = imageUrl;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.tag = tag;
        this.sourceTags = sourceTags;
        this.source = source;
        this.videoUrl = videoUrl;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getSummary() {
        return summary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getTag() {
        return tag;
    }

    public List<String> getSourceTags() {
        return sourceTags;
    }

    public SourceModel getSource() {
        return source;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getLanguage() { return language; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setSourceTags(List<String> sourceTags) {
        this.sourceTags = sourceTags;
    }

    public void setSource(SourceModel source) {
        this.source = source;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setLanguage(String language) { this.language = language; }

    public String toString(){
        String ret =  "Title: " + title + "\nContent: " + content + "\nContent length: " +  contentLength + "\nImageUrl: " + imageUrl + "\nImage heaight: " + imageHeight + "\nImage width: " + imageWidth + "\nTag: " + tag + "\nSource tags: ";
        for(int i = 0; i < sourceTags.size(); i ++) {
            ret += sourceTags.get(i)+ " ";
        }
        ret += "\nSource: " + source + "\nVideoUrl: " + videoUrl + "\nLanguage: " + language.toString();
        return ret;
    }
}
