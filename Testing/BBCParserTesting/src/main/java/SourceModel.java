/**
 * Created by Onu on 5/15/2017.
 */

import java.util.Date;

public class SourceModel {
    private String site;

    private String author;

    private Date date;

    public SourceModel() {
        this.site = " ";
        this.author = " ";
        this.date = new Date();
    }

    public SourceModel(String site, String author, Date date) {
        this.site = site;
        this.author = author;
        this.date = date;
    }

    public String getSite() {
        return site;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return "Site: " + site + "  Author: " + author + "  Date: " + date;
    }
}