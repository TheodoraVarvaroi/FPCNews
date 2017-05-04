package com.frontpagenews.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="articles")
public class ArticleModel {
    @Id
    private String id;

    private String titlu;

    private String continut;

    private byte[] imagine;

    private List<String> tags;

    private SourceModel sursa;

    private byte[] audio;

    public ArticleModel() {}

    public ArticleModel(String id, String titlu, String continut, byte[] imagine, List<String> tags, SourceModel sursa, byte[] audio) {
        this.id = id;
        this.titlu = titlu;
        this.continut = continut;
        this.imagine = imagine;
        this.tags = tags;
        this.sursa = sursa;
        this.audio = audio;
    }

    public String getId() {
        return id;
    }

    public String getTitlu() {
        return titlu;
    }

    public String getContinut() {
        return continut;
    }

    public byte[] getImagine() {
        return imagine;
    }

    public List<String> getTags() {
        return tags;
    }

    public SourceModel getSursa() {
        return sursa;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }

    public void setImagine(byte[] imagine) {
        this.imagine = imagine;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setSursa(SourceModel sursa) {
        this.sursa = sursa;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }
}
