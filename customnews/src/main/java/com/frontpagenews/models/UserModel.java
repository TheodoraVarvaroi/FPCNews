package com.frontpagenews.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection="users")
public class UserModel {
    @Id
    private String id; // username

    private String password;

    private String email;

    private List<String> preferred_tags;

    private List<String> preferred_sources;

    public UserModel() {}

    public UserModel(String username, String password, String email) {
        this.id = username;
        this.password = password;
        this.email = email;
        this.preferred_tags = new ArrayList<String>();
        this.preferred_sources = new ArrayList<String>();
    }

    public String getUsername() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getPreferred_tags() {
        return preferred_tags;
    }

    public List<String> getPreferred_sources() {
        return preferred_sources;
    }

    public void setUsername(String username) {
        this.id = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPreferred_tags(List<String> preferred_tags) {
        this.preferred_tags = preferred_tags;
    };

    public void setPreferred_sources(List<String> preferred_sources) {
        this.preferred_sources = preferred_sources;
    }
}

