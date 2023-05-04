package distributed;

import controller.GameController;
import controller.GamesManagerController;
import distributed.Client;
import distributed.Server;
import model.Game;
import model.GameView;
import model.LobbyManager.Lobby;
import utils.Event;
import view.Color;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Properties;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private GameController controller;
    private Game model;

    private Lobby currentLobby;

    private GamesManagerController gamesManagerController;

    String configFilePath = "./src/main/resources/usernames.properties";
    Properties prop = new Properties();

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
        System.out.print(Color.GREEN);
        System.out.println("Client correctly registered");
        System.out.print(Color.RESET);
    }

    @Override
    public void update(Client client, Event event, Integer columnNumber, ArrayList coords, String UserName) throws RemoteException{
        currentLobby = this.gamesManagerController.getLobbyByClient(client);
        /*
        *different clients notify players game's choices
        */
        if(!event.equals(Event.GAME_INIT) && !event.equals(Event.LOGIN)){
            //prop.remove(UserName);
            if(event.equals(Event.FINISH_MATCH)){
                for(String a: currentLobby.getClientsUsername()){
                    prop.remove(a);
                }
            }
            currentLobby.getController().update(client,event,columnNumber, coords , UserName);
        /*
        * player sends nickname and number of players to join a lobby
        * */
        }else if(event.equals(Event.LOGIN)){
            FileInputStream ip;

            {
                try {
                    ip = new FileInputStream(configFilePath);
                    prop.load(ip);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(prop.containsKey(UserName)){
                client.update(null, Event.LOGIN);
            }
            try {
                InputStream in = new FileInputStream(configFilePath);
                prop.load(in);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            prop.setProperty(UserName, "1");
            String value = prop.getProperty(UserName).trim();

            try {
                prop.store(new FileOutputStream(configFilePath), null);
            } catch (IOException ex) {
                System.out.println(ex);
            }
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
