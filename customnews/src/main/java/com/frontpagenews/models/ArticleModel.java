package com.frontpagenews.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="articles")
public class ArticleModel {
    @Id
    private String id; //title

    private String content;

    private byte[] image;

    private List<String> tags;

    private SourceModel source;

    private String videoUrl;

    public ArticleModel() {}

    public ArticleModel(String title, String content, byte[] image, List<String> tags, SourceModel source, String videoUrl) {
        this.id = title;
        this.content = content;
        this.image = image;
        this.tags = tags;
        this.source = source;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public byte[] getImage() {
        return image;
    }

    public List<String> getTags() {
        return tags;
    }

    public SourceModel getSource() {
        return source;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setTitle(String title) {
        this.id = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setSource(SourceModel source) {
        this.source = source;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
