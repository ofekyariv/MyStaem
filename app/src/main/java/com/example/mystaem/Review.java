package com.example.mystaem;

import java.util.ArrayList;

public class Review {

    //private String revcode;
    private String gamecode;
    private String username;
    private String comment;





    public Review(String gamecode,String username, String comment ) {
        //this.revcode =revcode;
        this.gamecode =gamecode;
        this.username =username;
        this.comment =comment;

    }
    public Review()
    {

    }

    /*public String getRevcode() {
        return revcode;
    }

    public void setRevcode(String revcode) {
        this.revcode = revcode;
    }*/
    public String getGamecode() {
        return gamecode;
    }
    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }



}
