package com.abtotest.voiptest.model;

/**
 * Created by root on 12/6/16.
 */

public class ChatMessageModel {
    public String msg;
    public boolean left;
    public ChatMessageModel(String msgText,boolean side){
        this.msg = msgText;
        this.left = side;
    }

    public ChatMessageModel() {

    }
}
