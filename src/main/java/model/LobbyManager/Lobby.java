package model.LobbyManager;

import controller.GameController;
import distributed.Client;
import model.Game;
import model.GameView;
import utils.Event;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Lobby {

    private Game model;
    private GameController gameController;
    private String id;
    private int nPlayers;
    private HashMap<String, Client> usersId;
    private boolean isFull;
    public Lobby(int nPlayers, String userName, Client client){
        this.nPlayers = nPlayers;
        usersId = new HashMap<String,Client>(nPlayers);
        usersId.put(userName,client);
        if(usersId.size() == nPlayers){
            isFull = true;
        }else{
            isFull = false;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    //returns true if usersId are full;

    public boolean insertPlayer(Client user,String userId){
        if(!isFull){
            usersId.put(userId,user);
            if(usersId.size() == nPlayers){
                isFull = true;
            }
        }
        return true;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setModel(Game model) {
        this.model = model;
    }

    public ArrayList<Client> getClients() {
        return new ArrayList<>(usersId.values());
    }

    public ArrayList<String> getClientsUsername() {
        return new ArrayList<>(usersId.keySet());
    }

    public void game_init() {
        try {
            this.model = new Game(new ArrayList<String>(usersId.keySet()));
            this.gameController = new GameController(model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Client c:usersId.values()){
            this.model.addObserver((o, arg, columnNumber, coords, UserName) -> {
                try {
                    c.update(new GameView(model), (Event) arg);
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
