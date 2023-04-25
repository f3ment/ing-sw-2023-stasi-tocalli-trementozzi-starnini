package model.LobbyManager;

import distributed.Client;
import model.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    private String id;
    private int nPlayers;
    private HashMap<String, Client> usersId;
    private Game game;
    private boolean isFull;
    public Lobby(String username, int nPlayers){
        this.nPlayers = nPlayers;
        usersId = new HashMap<String,Client>(nPlayers);
        this.isFull = false;
    }

    public String getId() {
        return id;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    //returns true if usersId are full;
    public void insertPlayer(Client user,String userId){
        if(!isFull){
            usersId.put(new String(userId),user);
            if(usersId.size() == nPlayers){
                isFull = true;
            }
        }
    }

    public boolean isFull() {
        return isFull;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
