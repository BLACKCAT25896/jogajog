package com.example.chattingapp.model;

public class User {
    private String id;
    private String name;
    private String email;
    private String profileImage;
    private String status;
    private String search;

    public User() {
    }


    public User(String id, String name, String email, String profileImage, String status, String search) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.status = status;
        this.search = search;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getStatus() {
        return status;
    }

    public String getSearch() {
        return search;
    }
}
