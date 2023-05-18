package distributed;

//import controller.ChatController;
import controller.GameController;
import controller.GamesManagerController;
//import model.Chat;
import model.Game;
import controller.Lobby;
//import model.Message;
import model.Message;
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

    //private ChatController chatController;
    //private Chat chatModel;

    private Lobby currentLobby;

    private GamesManagerController gamesManagerController;

    String configFilePath = "./src/main/resources/usernames.properties";
    Properties prop = new Properties();

    private static Object syncKey = new Object();

    public ServerImpl() throws RemoteException {
        super();
        gamesManagerController = new GamesManagerController();
        currentLobby = null;
        new Thread(){
                    @Override
                    public void run(){
                        while (true){
                            try {
                                System.out.println("thread va");
                                sleep(5000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            for(Lobby l : gamesManagerController.getLobbies_list()){
                                if(l!=null&&l.isFull()){
                                    if(l.getCurrentPlayer()!=null&&!l.getStatusPlayer(l.getCurrentPlayer())){
                                        System.out.println("thread entra");
                                        l.getController().update(l.getClientByUsername(l.getCurrentPlayer()), new Message(Event.CONNECTION_PROBLEM));;
                                    }
                                }
                            }
                        }
                    }
                }.start();
    }

    public ServerImpl(int port) throws RemoteException {
        super(port);
        gamesManagerController = new GamesManagerController();
        currentLobby = null;
        new Thread(){
            @Override
            public void run(){
                while (true){
                    try {
                        System.out.println("thread va");
                        sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    for(Lobby l : gamesManagerController.getLobbies_list()){
                        if(l.isFull()&&l!=null){
                            if(!l.getStatusPlayer(l.getCurrentPlayer())){
                                System.out.println("thread condizione");
                                l.getController().update(l.getClientByUsername(l.getCurrentPlayer()), new Message(Event.CONNECTION_PROBLEM));;
                            }
                        }
                    }
                }
            }
        }.start();
    }

    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        gamesManagerController = new GamesManagerController();
        currentLobby = null;
        new Thread(){
            @Override
            public void run(){
                while (true){
                    try {
                        System.out.println("thread va");
                        sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    for(Lobby l : gamesManagerController.getLobbies_list()){
                        if(l!=null&&l.isFull()){
                            if(!l.getStatusPlayer(l.getCurrentPlayer())){
                                System.out.println("thread condizione");
                                l.getController().update(l.getClientByUsername(l.getCurrentPlayer()), new Message(Event.CONNECTION_PROBLEM));;
                            }
                        }
                    }
                }
            }
        }.start();
    }


    //ci permette di acquisire un nuovo client
    // damiani fa un 1to1 client server e model, cioè ad ogni client è associato un nuovo model e un nuovo controller
    // la mia idea è di usare la lobby prima del model, il client si collega ad un server e con la funzione
    // register si collega alla lobby
    // todo metodo da rifare
    @Override
    public void register(Client client) throws RemoteException{
        System.out.println(Color.GREEN_BRIGHT + "Client correctly registered" + Color.RESET);
    }

    @Override
    public void update(Client client, Message message) throws RemoteException {
        currentLobby = this.gamesManagerController.getLobbyByClient(client);
            //CHAT UPDATE
            if (currentLobby != null && (message.getEvent().equals(Event.GET_CHAT) || message.getEvent().equals(Event.EXIT_CHAT) || message.getEvent().equals(Event.SEND_MESSAGE))) {
                currentLobby.getChatController().update(client, message);
            } else if (!message.getEvent().equals(Event.GAME_INIT) && !message.getEvent().equals(Event.LOGIN)) {
                //GAME UPDATE
                /*
                 *different clients notify players game's choices
                 */
                if (message.getEvent().equals(Event.PING)) {
                    String username = gamesManagerController.getLobbyByClient(client).getUsernameByclient(client);
                    gamesManagerController.getLobbyByClient(client).resetTimer(username);
                    //client.update(null, Event.PING);
                } else {
                    if (message.getEvent().equals(Event.FINISH_MATCH)) {
                        for (String a : currentLobby.getClientsUsername()) {
                            prop.remove(a);
                        }
                    }
                    currentLobby.getController().update(client, message);
                }
                /*
                 * player sends nickname and number of players to join a lobby
                 * */
            } else if (message.getEvent().equals(Event.LOGIN)) {
                boolean correctusername = true;
                synchronized (syncKey) {
                    FileInputStream ip;
                    {
                        try {
                            ip = new FileInputStream(configFilePath);
                            prop.load(ip);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (prop.containsKey(message.getUserName())) {
                        //client.update(null, Event.LOGIN);
                        correctusername = false;
                    } else {
                        try {
                            InputStream in = new FileInputStream(configFilePath);
                            prop.load(in);
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                        prop.setProperty(message.getUserName(), "1");
                        String value = prop.getProperty(message.getUserName()).trim();

                        try {
                            prop.store(new FileOutputStream(configFilePath), null);
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }
                }
                if (!correctusername) {
                    //if (gamesManagerController.StatusUsername(message.getUserName(), gamesManagerController.LobbyByUsername(message.getUserName()))) {
                        client.update(new Message(Event.LOGIN));
                    //} else {
                        //gamesManagerController.LobbyByUsername(message.getUserName()).insertPlayer(client, message.getUserName());
                    //}
                } else {

                    Lobby lobby = this.gamesManagerController.addPlayerToLobby(client, message.getnPlayers(), message.getUserName());

                    client.update(new Message(Event.WAIT_START_OF_MATCH));

                    if (lobby != null) {
                        lobby.getController().update(client, new Message(Event.LOGIN_TRUE));
                    }
                }
                /*
                 * hit by the client for server connection and starting of the login procedures
                 * */
            } else {
                //System.out.println(Color.RED_BRIGHT + "Username NOT valid" + Color.RESET);
                client.update(new Message(Event.LOGIN));
            }
    }

    //TODO GESTIONE USERNAMES






}
