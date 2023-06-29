package model;

import model.views.ChatView;
import model.views.GameView;
import utils.Event;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represent message objects that are sent between server and client
 * It contains all the information needed to update the view or the model of the game
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private  ArrayList<String> nicknames;
    private ChatView chat;
    private Event event;
    private int nPlayers;
    private int columnNumber;
    private String userName;
    private ArrayList coords;
    private ChatMessage chatMessage;
    private model.views.GameView model;

    /**
     * Constructor of the class
     * @param o is the model of the game
     * @param event is the event that is sent to the client
     */
    public Message(GameView o, Event event){
        this.event = event;
        this.model = o;
    }

    /**
     * Constructor of the class
     * @param event is the event that is sent to the client
     * @param coords are the coordinates of the tiles drawn
     */
    public Message(Event event, ArrayList coords) {
        this.event = event;
        this.coords = coords;
    }

    /**
     * Constructor of the class
     * @param arg is the event that is sent to the client
     */
    public Message(Event arg){
        this.event = arg;
    }

    /**
     * Constructor of the class
     * @param event is the event that is sent to the client
     * @param columnNumber is the number of the column where the tile is placed
     * @param coords are the coordinates of the tiles drawn
     */
    public Message(Event event, int columnNumber, ArrayList coords) {
        this.event = event;
        this.columnNumber = columnNumber;
        this.coords = coords;
    }

    /**
     * Constructor of the class
     * @param arg is the event that is sent to the client
     * @param chat is the chat of the game
     * @param model is the model of the game
     */
    public Message(Event arg, ChatView chat, GameView model){
        this.event = arg;
        this.chat = chat;
        this.model = model;
    }

    /**
     * Constructor of the class
     * @param username is the username of the player
     * @param arg is the event that is sent to the client
     * @param chat is the chat of the game
     */
    public Message(String username, Event arg, ChatView chat){
        this.userName = username;
        this.event = arg;
        this.chat = chat;
    }

    /**
     * Constructor of the class
     * @param event is the event that is sent to the client
     * @param nPlayers is the number of players in the game
     * @param columnNumber is the number of the column where the tile is placed
     * @param userName is the username of the player
     * @param coords are the coordinates of the tiles drawn
     */
    public Message(Event event, int nPlayers, int columnNumber, String userName, ArrayList coords){
        this.event = event;
        this.nPlayers = nPlayers;
        this.columnNumber = columnNumber;
        this.userName = userName;
        this.coords = coords;
    }


    /**
     * @param event is the event that is sent to the client
     * @param chatMessage is the message sent by the player
     */
    public Message( Event event, ChatMessage chatMessage) {
        this.event = event;
        this.chatMessage = chatMessage;
    }


    /**
     * Constructor of the class
     * @param event is the event that is sent to the client
     * @param players are the players usernames in the game
     * @param nPlayers is the number of players in the game
     */
    public Message(Event event, ArrayList<String> players , int nPlayers) {
        this.event = event;
        this.nicknames = players;
        this.nPlayers = nPlayers;
    }

    /**
     * Constructor of the class
     * @param event is the event that is sent to the client
     * @param nPlayers is the number of players in the game
     * @param userName is the username of the player
     */
    public Message(Event event, int nPlayers, String userName) {
        this.event = event;
        this.nPlayers = nPlayers;
        this.userName = userName;
    }


    /**
     * Constructor of the class
     * @param getChat is the event that is sent to the client
     * @param username is the username of the player
     */
    public Message(Event getChat, String username) {
        this.event = getChat;
        this.userName = username;
    }


    /**
     * Constructor of the class
     * @param userName is the username of the player
     * @param event is the event that is sent to the client
     */
    public Message(String userName,Event event){
        this.event=event;
        this.userName=userName;
    }

    public Message(Event event, ArrayList<String> clientsUsername, int i, ChatView chat) {
        this.event = event;
        this.nicknames = clientsUsername;
        this.nPlayers = i;
        this.chat = chat;
    }


    /**
     * @return the number of players in the game
     */
    public int getnPlayers() {
        return nPlayers;
    }

    /**
     * @return the number of the column where the tile is placed
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * @return the username of the player
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the coordinates of the tiles drawn
     */
    public ArrayList getCoords() {
        return coords;
    }

    /**
     * @return the event that is sent to the client
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @return the chat of the game
     */
    public ChatView getChat() {
        return this.chat;
    }

    /**
     * @return the model of the game
     */
    public GameView getModel() {
        return this.model;
    }

    /**
     * @return the players usernames in the game
     */
    public ArrayList<String> getNicknames() {
        return this.nicknames;
    }

    /**
     * @return the message sent by the player
     */
    public ChatMessage getChatMessage() {
        return this.chatMessage;
    }
}
