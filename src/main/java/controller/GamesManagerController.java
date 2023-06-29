package controller;
import java.util.*;


import distributed.Client;

/**
 * This class is used to manage multiple lobbies and games.
 * It is used to create a new lobby or to add a player to an existing one.
 * It is also used to check if a lobby is full and if it is, to start the game.
 */
public class GamesManagerController {


    private HashMap<Client, Lobby> lobbies;

    private ArrayList<Lobby> lobbies_list;

    /**
     * Constructor of the class.
     */
    public GamesManagerController(){
        lobbies_list = new ArrayList<>();
        lobbies = new HashMap<>();
    }

    /**
     * This method is used to add a player to a new lobby specifying its size or to an existing one.
     * @param client The client that wants to create a new lobby.
     * @param playerNumber The number of players that the lobby will contain.
     * @param userName The username of the client that wants to create a new lobby.
     * @return The new lobby created.
     */
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

    /**
     * This method is used to check if a lobby is full and if it is, to start the game.
     * @param lobby The lobby to check.
     * @return True if the lobby is full, false otherwise.
     */
    public synchronized boolean checkStart(Lobby lobby){
        if(lobby.isFull()){
            lobby.game_init();
            return true;
        }
        return false;
    }

    /**
     * This method is used to get the lobby reference of a client.
     * @param client The client that wants to know its lobby reference.
     * @return The lobby reference of the client.
     */
    public Lobby getLobbyByClient(Client client) {
        return lobbies.get(client);
    }


    /**
     * This method is used to get the lobby reference of a username.
     * @param username The username of the client that wants to know its lobby reference.
     * @return The lobby reference of the client.
     */
    public Lobby LobbyByUsername(String username){
        Lobby g=null;
        for(Lobby a:lobbies_list){
            if(a.isUsernameContained(username)){
                g=a;
                break;
            }
        }
        return g;
    }

    /**
     * This ,ethod is used to get the status of a client.
     * @param username The username of the client that wants to know its status: online or offline.
     * @param a The lobby reference of the client.
     * @return
     */
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


    /**
     * This method is used to get the list of existing lobbies.
     * @return The list of lobbies.
     */
    public ArrayList<Lobby> getLobbies_list(){
        return lobbies_list;
    }

    /**
     * This method links a client to a lobby.
     * @param client The client that will join the lobby.
     * @param lobby The lobby that the client will to join.
     * @param username The username of the client that will join the lobby.
     */
    public void insertPlayer(Client client,Lobby lobby,String username){
        lobbies.remove(LobbyByUsername(username).getClientByUsername(username));
        lobbies.put(client,lobby);
    }

    /**
     * This method removes a client from the lobby-client map.
     * @param client The client that will be removed from the map.
     */
    public void removePlayer(Client client){
        lobbies.remove(client);
    }

    /**
     * This method removes a lobby from the list of existing lobbies.
     * @param index The index of the lobby that will be removed.
     */
    public void removeLobby(int index){
        try{
            lobbies_list.remove(index);
        }catch (IndexOutOfBoundsException e){
            System.out.println("There aren't lobbies to remove");
        }

    }
}
