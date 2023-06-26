package model;

import model.views.ChatView;
import model.views.GameView;
import utils.Event;

import java.io.Serializable;
import java.util.ArrayList;

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

    public Message(GameView o, Event event){
        this.event = event;
        this.model = o;
    }

    public Message(Event event, ArrayList coords) {
        this.event = event;
        this.coords = coords;
    }

    public Message(Event arg){
        this.event = arg;
    }

    public Message(Event event, int columnNumber, ArrayList coords) {
        this.event = event;
        this.columnNumber = columnNumber;
        this.coords = coords;
    }

    public Message(Event arg, ChatView chat, GameView model){
        this.event = arg;
        this.chat = chat;
        this.model = model;
    }

    public Message(String username, Event arg, ChatView chat){
        this.userName = username;
        this.event = arg;
        this.chat = chat;
    }

    public Message(Event event, int nPlayers, int columnNumber, String userName, ArrayList coords){
        this.event = event;
        this.nPlayers = nPlayers;
        this.columnNumber = columnNumber;
        this.userName = userName;
        this.coords = coords;
    }
    public Message( Event event, ChatMessage chatMessage) {
        this.event = event;
        this.chatMessage = chatMessage;
    }

    public Message(Event event, ArrayList<String> players , int nPlayers) {
        this.event = event;
        this.nicknames = players;
        this.nPlayers = nPlayers;
    }

    public Message(Event event, int nPlayers, String userName) {
        this.event = event;
        this.nPlayers = nPlayers;
        this.userName = userName;
    }

    public Message(Event getChat, String username) {
        this.event = getChat;
        this.userName = username;
    }
    public Message(String userName,Event event){
        this.event=event;
        this.userName=userName;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList getCoords() {
        return coords;
    }

    public Event getEvent() {
        return event;
    }

    public ChatView getChat() {
        return this.chat;
    }

    public GameView getModel() {
        return this.model;
    }

    public ArrayList<String> getNicknames() {
        return this.nicknames;
    }

    public ChatMessage getChatMessage() {
        return this.chatMessage;
    }
}
