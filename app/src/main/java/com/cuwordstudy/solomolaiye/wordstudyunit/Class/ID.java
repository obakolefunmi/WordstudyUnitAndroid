package com.cuwordstudy.solomolaiye.wordstudyunit.Class;

import com.google.gson.annotations.SerializedName;

public class ID {

    @SerializedName("$oid")
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
