package com.frontpagenews.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="articles")
public class ArticleModel {
    @Id
    private String id; //title

    private String content;

    private String imageUrl;

    private List<String> tags;

    private SourceModel source;

    private String videoUrl;

    public ArticleModel() {}

    public ArticleModel(String title, String content, String imageUrl, List<String> tags, SourceModel source, String videoUrl) {
        this.id = title;
        this.content = content;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
