package com.frontpagenews.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Document
public class SourceModel {
    private String site;

    private String autor;

    private Date data;

    public SourceModel() {}

    public SourceModel(String site, String autor, Date data) {
        this.site = site;
        this.autor = autor;
        this.data = data;
    }

    public String getSite() {
        return site;
    }

    public String getAutor() {
        return autor;
    }

    public Date getData() {
        return data;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
