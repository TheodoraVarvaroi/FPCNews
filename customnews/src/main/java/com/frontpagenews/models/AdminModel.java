package com.frontpagenews.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="admins")
public class AdminModel {
    @Id
    private String id; //username

    private String password;

    private String email;

    public AdminModel() {}

    public AdminModel(String username, String password, String email) {
        this.id = username;
        this.password = password;
        this.email = email;
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

    public void setUsername(String username) {
        this.id = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
