package com.francotte.go4lunch_opc.models.NOTIFICATION;

import java.io.Serializable;

public class Result implements Serializable {

    private String messageId;

    public Result() {
        super();
    }

    public Result(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}