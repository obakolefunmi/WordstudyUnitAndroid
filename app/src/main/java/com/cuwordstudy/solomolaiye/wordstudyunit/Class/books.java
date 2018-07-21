package com.cuwordstudy.solomolaiye.wordstudyunit.Class;

public class books {
    public String book_number;
    public String long_name;

    public books(String book_number, String long_name) {
        this.book_number = book_number;
        this.long_name = long_name;
    }

    public String getBook_number() {
        return book_number;
    }

    public void setBook_number(String book_number) {
        this.book_number = book_number;
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }
}
