package com.example.chattingapp.model;

public class Chat {
    private String sender;
    private String receiver;
    private String message;
    private boolean isSeen;

    public Chat() {
    }


    public Chat(String sender, String receiver, String message, boolean isSeen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isSeen = isSeen;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSeen() {
        return isSeen;
    }
}
