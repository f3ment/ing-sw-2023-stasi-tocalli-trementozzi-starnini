package model;

import java.util.ArrayList;

public class Lobby {

    private String id;
    private int nPlayers;
    private ArrayList<String> usersId;
    private Game game;
    private boolean isFull;
    public Lobby(String username, int nPlayers){
        this.nPlayers = nPlayers;
        usersId = new ArrayList<String>(nPlayers);
        usersId.add(new String(username));
        this.isFull = false;
    }

    public String getId() {
        return id;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    //returns true if usersId are full;
    public void insertPlayer(String userId){
        if(!isFull){
            usersId.add(new String(userId));
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
