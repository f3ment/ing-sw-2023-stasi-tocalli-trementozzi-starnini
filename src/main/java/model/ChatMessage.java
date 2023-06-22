package model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String message;
    private final String sender;

    private final String receiver;

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
