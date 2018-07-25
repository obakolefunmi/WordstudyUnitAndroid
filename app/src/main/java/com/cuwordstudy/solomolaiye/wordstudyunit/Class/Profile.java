package com.cuwordstudy.solomolaiye.wordstudyunit.Class;

public class Profile {
    public ID _id ;
    public String fitst_name;
    public String last_name;
    public String user_email;
    public String user_phone_number;
    public String hall_of_residence;
    public String room_number;
    public String user_gender;

    public ID get_id() {
        return _id;
    }

    public void set_id(ID _id) {
        this._id = _id;
    }

    public String getFitst_name() {
        return fitst_name;
    }

    public void setFitst_name(String fitst_name) {
        this.fitst_name = fitst_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public String getHall_of_residence() {
        return hall_of_residence;
    }

    public void setHall_of_residence(String hall_of_residence) {
        this.hall_of_residence = hall_of_residence;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }
}
