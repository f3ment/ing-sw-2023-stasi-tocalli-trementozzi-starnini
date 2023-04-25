package controller;
import model.LobbyManager.Lobby;
import java.util.*;
import java.io.*;


import distributed.Client;
import utils.Event;
//TODO sincronizzare e gestire inizio game(magari farlo tornare)
public class GamesManagerController {
    public void addPlayerToLobby(Client client, Integer playerNumber, String userName) {
        for(Lobby a : lobbies){
            if(a.isFull()==false&&a.getnPlayers()==playerNumber&&a.insertPlayer(client,userName)){
                return;
            }
        }
        lobbies.add(new Lobby(playerNumber,userName,client));

        /*
        *
        * implementare logica controller games manager
        *
        * */
    }
    List<Lobby> lobbies;

}
