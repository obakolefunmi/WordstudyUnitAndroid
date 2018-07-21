package com.cuwordstudy.solomolaiye.wordstudyunit.Class;

public class Notes {
    public int note_id;
    public String note;
    public String note_date;

    public Notes(int note_id, String note, String note_date) {
        this.note_id = note_id;
        this.note = note;
        this.note_date = note_date;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote_date() {
        return note_date;
    }

    public void setNote_date(String note_date) {
        this.note_date = note_date;
    }
}
