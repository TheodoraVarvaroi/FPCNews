package com.frontpagenews.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="admins")
public class AdminModel {
    @Id
    private String id;

    private String username;

    private String parola;

    private String email;

    public AdminModel() {}

    public AdminModel(String id, String username, String parola, String email) {
        this.id = id;
        this.username = username;
        this.parola = parola;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getParola() {
        return parola;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
