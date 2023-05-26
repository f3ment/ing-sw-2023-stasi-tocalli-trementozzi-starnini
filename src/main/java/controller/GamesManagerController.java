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

    /*public synchronized boolean checkUsername(String username){
        for(Lobby a : lobbies_list){
            if(a.getClientsUsername().contains(username)){
                return false;
            }
        }
        return true;
    }*/
    public Lobby LobbyByUsername(String username){
        boolean flag=false;
        Lobby g=null;
        for(Lobby a:lobbies_list){
            if(a.isUsernameContained(username)){
                flag=true;
                g=a;
                break;
            }
        }
        return g;
    }

    public boolean StatusUsername(String username,Lobby a){
       return a.getStatusPlayer(username);
    }

   /* public void checkClientsConnections(){
        new Thread(){
            @Override
            public void run() {
                String username;
                while(true){
                    for(Lobby a : lobbies_list){
                        for(String user: a.getClientsUsername() ){
                            if(!a.getStatusPlayer(user)){
                                username=new String(user);
                                lobbies.
                            }
                        }
                    }
                }
            }
        }.start();
    }*/
    public ArrayList<Lobby> getLobbies_list(){
        return lobbies_list;
    }

    public void insertPlayer(Client client,Lobby lobby,String username){
        lobbies.remove(LobbyByUsername(username).getClientByUsername(username));
        lobbies.put(client,lobby);
    }
    public void removePlayer(Client client){
        lobbies.remove(client);
    }

    public void removeLobby(int index){
        lobbies_list.remove(index);
    }
}
