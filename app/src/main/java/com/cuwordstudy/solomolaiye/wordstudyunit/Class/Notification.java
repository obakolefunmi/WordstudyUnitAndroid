package com.cuwordstudy.solomolaiye.wordstudyunit.Class;

public class Notification {
    public String body;
    public String title;
    public String sound;

    public Notification() {
    }

    public Notification(String body, String title,String sound) {
        this.body = body;
        this.title = title;
        this.sound = "default" ;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
