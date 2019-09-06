package com.example.chattingapp.model;

public class User {
    private String id;
    private String name;
    private String email;
    private String profileImage;

    public User() {
    }



    public User(String id, String name, String email, String profileImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
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
}
