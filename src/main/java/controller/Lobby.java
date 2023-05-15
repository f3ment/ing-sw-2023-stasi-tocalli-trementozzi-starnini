package controller;

import distributed.Client;
import model.Chat;
import model.Game;
import model.Message;
import model.views.GameView;
import utils.Event;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    private Game model;
    private Chat chat;
    private ChatController chatController;
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

        chat = new Chat();
        chatController = new ChatController(chat);
        this.chat.addObserver((o, message) -> {
            try {
                //todo creare una chatview
                client.update(new Message(message.getUserName(),(Event) message.getEvent(), chat));
            } catch (RemoteException e) {
                System.err.println("Error while updating the client : " + e.getMessage() + ". Skipping the update...");
            }
        });
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

        this.chat.addObserver((o, message) -> {
            try {
                //todo creare una chatview
                user.update(new Message(message.getUserName(),(Event) message.getEvent(), chat));
            } catch (RemoteException e) {
                System.err.println("Error while updating the client : " + e.getMessage() + ". Skipping the update...");
            }
        });
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
            this.model.addObserver((o, message) -> {
                try {
                    c.update(new Message(new GameView(model), (Event) message.getEvent()));
                } catch (RemoteException e) {
                    System.err.println("Error while updating the client : " + e.getMessage() + ". Skipping the update...");
                }
            });


        }
    }

    public GameController getController() {
        return gameController;
    }

    public ChatController getChatController() {
        return this.chatController;
    }
}
