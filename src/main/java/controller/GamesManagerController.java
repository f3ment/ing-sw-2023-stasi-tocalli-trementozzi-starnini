package controller;
import java.util.*;


import distributed.Client;

//TODO sincronizzare e gestire inizio game(magari farlo tornare)
public class GamesManagerController {


    private HashMap<Client, Lobby> lobbies;

    private ArrayList<Lobby> lobbies_list;

    public GamesManagerController(){
        lobbies_list = new ArrayList<Lobby>();
        lobbies = new HashMap<>();
    }

    public synchronized Lobby addPlayerToLobby(Client client, Integer playerNumber, String userName) {
        for(Lobby a : lobbies_list){
            if(!a.isFull() && a.getnPlayers()==playerNumber){
                a.insertPlayer(client,userName);
                lobbies.put(client, a);
                if(checkStart(a)){
                    return a;
                }else
                    return null;
            }
        }
        Lobby newLobby = new Lobby(playerNumber,userName,client);
        lobbies_list.add(newLobby);
        lobbies.put(client, newLobby);
        if(checkStart(newLobby)){
            return newLobby;
        }else
            return null;
    }

    public synchronized boolean checkStart(Lobby lobby){
        if(lobby.isFull()){
            lobby.game_init();
            return true;
        }
        return false;
    }

    public Lobby getLobbyByClient(Client client) {
        return lobbies.get(client);
    }

}
