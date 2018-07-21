package com.cuwordstudy.solomolaiye.wordstudyunit.Class;

import java.security.PublicKey;

public class Sender {
    public Notification notification;
    public String to;

    public Sender(String to,Notification notification) {
        this.notification = notification;
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
