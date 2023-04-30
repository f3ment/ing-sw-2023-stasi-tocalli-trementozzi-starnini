package distributed.rmi;

import controller.GameController;
import controller.GamesManagerController;
import distributed.Client;
import distributed.Server;
import model.Game;
import model.GameView;
import model.LobbyManager.Lobby;
import utils.Event;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private GameController controller;
    private Game model;

    private Lobby currentLobby;

    private GamesManagerController gamesManagerController;

    public ServerImpl() throws RemoteException {
        super();
        gamesManagerController = new GamesManagerController();
        currentLobby = null;
    }

    public ServerImpl(int port) throws RemoteException {
        super(port);
        gamesManagerController = new GamesManagerController();
        currentLobby = null;


    }

    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        gamesManagerController = new GamesManagerController();
        currentLobby = null;

    }


    //ci permette di acquisire un nuovo client
    // damiani fa un 1to1 client server e model, cioè ad ogni client è associato un nuovo model e un nuovo controller
    // la mia idea è di usare la lobby prima del model, il client si collega ad un server e con la funzione
    // register si collega alla lobby
    // todo metodo da rifare
    @Override
    public void register(Client client) throws RemoteException{
        System.out.println("Client correctly registered");
    }

    @Override
    public void update(Client client, Event event, Integer columnNumber, ArrayList coords, String UserName) throws RemoteException{
        currentLobby = this.gamesManagerController.getLobbyByClient(client);
        /*
        *different clients notify players game's choices
        */
        if(!event.equals(Event.GAME_INIT) && !event.equals(Event.LOGIN)){
            currentLobby.getController().update(client,event,columnNumber, coords , UserName);
        /*
        * player sends nickname and number of players to join a lobby
        * */
        }else if(event.equals(Event.LOGIN)){
            Lobby lobby = this.gamesManagerController.addPlayerToLobby(client,columnNumber , UserName);
            if(lobby != null) {
                    lobby.getController().update(client, Event.LOGIN_TRUE, null, null, null);
            }else{
                client.update(null, Event.WAIT_START_OF_MATCH);
            }
        /*
        * hit by the client for server connection and starting of the login procedures
        * */
        }else{
            client.update(null, Event.LOGIN);
        }

    }

    //TODO GESTIONE USERNAMES






}
