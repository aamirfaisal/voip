package com.abtotest.voiptest.model;

/**
 * Created by root on 12/5/16.
 */

public class InboxMOdel {
    public String text;
    public String name;
    public int userID;
    public boolean left;

    public InboxMOdel(boolean left, String mesg) {
        super();
        this.left = left;
        this.text = mesg;
    }
public InboxMOdel(){

}

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
