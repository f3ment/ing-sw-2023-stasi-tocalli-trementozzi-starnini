package model;

import java.io.Serializable;


/**
 * ChatMessage class
 * This class is responsible for managing the chat messages.
 * It contains the message, the sender and the receiver.
 */
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String message;
    private final String sender;

    private final String receiver;

    /**
     * Constructor for the ChatMessage class that initializes the message, the sender and the receiver.
     * @param message The message
     * @param sender The sender
     * @param receiver The receiver
     */
    public ChatMessage(String message, String sender, String receiver){
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * This method returns the message.
     * @return The message
     */
    public String getMessage(){
        return message;
    }

    /**
     * This method returns the sender.
     * @return The sender
     */
    public String getSender(){
        return sender;
    }


    /**
     * This method returns the receiver.
     * @return The receiver
     */
    public String getReceiver(){
        return receiver;
    }

}
