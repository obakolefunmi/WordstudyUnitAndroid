package com.cuwordstudy.solomolaiye.wordstudyunit.Class;

public class questions {
    public ID _id ;
    public String titleid ;
    public String title ;

    public String question ;
    public String user ;

    public ID get_id() {
        return _id;
    }

    public void set_id(ID _id) {
        this._id = _id;
    }

    public String getTitleid() {
        return titleid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleid(String titleid) {
        this.titleid = titleid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
