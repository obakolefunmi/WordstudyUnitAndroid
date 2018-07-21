package com.cuwordstudy.solomolaiye.wordstudyunit.Class;

public class topic {
    public ID _id ;
    public String title ;
    public String bible ;
    public String text ;

    public ID get_id() {
        return _id;
    }

    public void set_id(ID _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBible() {
        return bible;
    }

    public void setBible(String bible) {
        this.bible = bible;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
