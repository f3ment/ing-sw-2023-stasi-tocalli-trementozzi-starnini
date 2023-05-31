package model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private String sender;

    private String receiver;

    public ChatMessage(String message, String sender, String receiver){
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessage(){
        return message;
    }

    public String getSender(){
        return sender;
    }

    public String getReceiver(){
        return receiver;
    }

}
