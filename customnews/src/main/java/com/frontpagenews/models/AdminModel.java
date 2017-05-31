package com.frontpagenews.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="admins")
public class AdminModel {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private String token;

    private String email;

    public AdminModel() {
        this.username = " ";
        this.password = " ";
        this.email = " ";
        this.token = "";
    }

    public AdminModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.token = "";
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    };
}
