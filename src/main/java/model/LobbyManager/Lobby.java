package model.LobbyManager;

import controller.GameController;
import distributed.Client;
import model.Game;
import model.GameView;
import utils.Event;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Lobby {

    private Game game;
    private GameController gameController;
    private String id;
    private int nPlayers;
    private HashMap<String, Client> usersId;
    private boolean isFull;
    public Lobby(int nPlayers, String userName, Client client){
        this.nPlayers = nPlayers;
        usersId = new HashMap<String,Client>(nPlayers);
        usersId.put(userName,client);
        this.isFull = false;
    }

    public String getId() {
        return id;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    //returns true if usersId are full;

    public boolean insertPlayer(Client user,String userId){
        if(!isFull){
            if(usersId.containsKey(userId))
                return false;
            usersId.put(new String(userId),user);
            if(usersId.size() == nPlayers){
                isFull = true;
            }
        }
        return true;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Collection<Client> getClients() {
        return usersId.values();
    }

    public void game_init() {
        try {
            this.game = new Game(new ArrayList<String>(usersId.keySet()));
            this.gameController = new GameController(game);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Client c:usersId.values()){
            this.game.addObserver((o,arg, columnNumber,coords,UserName) -> {
                try {
                    c.update(new GameView(game), (Event) arg);
                } catch (RemoteException e) {
                    System.err.println("Error while updating the client : " + e.getMessage() + ". Skipping the update...");

                }
            });
        }
    }

    public GameController getController() {
        return gameController;
    }
}
