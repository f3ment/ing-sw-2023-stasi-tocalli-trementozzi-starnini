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
    //private ChatController chatController;
    //private Chat chatModel;

    private Lobby currentLobby;

    private GamesManagerController gamesManagerController;

    String configFilePath = "./src/main/resources/usernames.properties";
    Properties prop = new Properties();

    private static final Object syncKey = new Object();

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
        System.out.println(Color.GREEN_BRIGHT + "Client correctly registered" + Color.RESET);
    }

    @Override
    public void update(Client client, Message message) throws RemoteException {
        currentLobby = this.gamesManagerController.getLobbyByClient(client);

        //CHAT UPDATE
        if(currentLobby!=null &&( message.getEvent().equals(Event.GET_CHAT) || message.getEvent().equals(Event.EXIT_CHAT) || message.getEvent().equals(Event.SEND_MESSAGE))){
            currentLobby.getChatController().update(client,message);
        }else
        //GAME UPDATE
        /*
         *different clients notify players game's choices
         */
        if(!message.getEvent().equals(Event.GAME_INIT) && !message.getEvent().equals(Event.LOGIN)){
            //prop.remove(UserName);
            if(message.getEvent().equals(Event.FINISH_MATCH)){
                for(String a: currentLobby.getClientsUsername()){
                    prop.remove(a);
                }
            }
            currentLobby.getController().update(client, message);
            /*
             * player sends nickname and number of players to join a lobby
             * */
        }else if(message.getEvent().equals(Event.LOGIN)){
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
                client.update(new Message(Event.LOGIN));
            }else {

                Lobby lobby = this.gamesManagerController.addPlayerToLobby(client, message.getnPlayers(), message.getUserName());
                currentLobby = gamesManagerController.getLobbyByClient(client);
                client.update(new Message(Event.WAIT_START_OF_MATCH, currentLobby.getClientsUsername() , currentLobby.getnPlayers()));

                if (lobby != null) {
                    lobby.getController().update(client, new Message(Event.LOGIN_TRUE));
                }
            }
            /*
             * hit by the client for server connection and starting of the login procedures
             * */
        }else{
            //System.out.println(Color.RED_BRIGHT + "Username NOT valid" + Color.RESET);
            client.update(new Message(Event.LOGIN));

        }
    }







}
