package distributed;

//import controller.ChatController;
import controller.GamesManagerController;
//import model.Chat;
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
import java.util.*;

public class ServerImpl extends UnicastRemoteObject implements Server {
    //private ChatController chatController;
    //private Chat chatModel;

    private Lobby currentLobby;

    private GamesManagerController gamesManagerController;

    String configFilePath = "./src/main/resources/usernames.properties";
    Properties prop = new Properties();

    private static final Object syncKey = new Object();

    private ArrayList<Integer> destroy_array = new ArrayList<>();

    public ServerImpl() throws RemoteException {
        super();
        initialize();
    }

    public ServerImpl(int port) throws RemoteException {
        super(port);
        initialize();
    }

    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        initialize();
    }

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
                    currentLobby = this.gamesManagerController.getLobbyByClient(client);
                    if(currentLobby!=null) {
                        String username = gamesManagerController.getLobbyByClient(client).getUsernameByClient(client);
                        System.out.println(username);
                        gamesManagerController.getLobbyByClient(client).resetTimer(username);
                    }
                    //client.update(null, Event.PING);
                } else {
                    if (message.getEvent().equals(Event.DELETE_MATCH)) {
                        Lobbydeletion(currentLobby,destroy_array);
                        for(Integer i:destroy_array){
                            gamesManagerController.removeLobby(i);
                        }
                        destroy_array.clear();
                        //todo rimuovere lobby SOLO quando nessuno è in chat e nessuno è in partita
                    }else {
                            currentLobby.getController().update(client, message);
                    }
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
                            System.out.println(ex.getMessage());
                        }
                        prop.setProperty(message.getUserName(), "1");
                        String value = prop.getProperty(message.getUserName()).trim();

                        try {
                            prop.store(new FileOutputStream(configFilePath), null);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                if (!correctusername) {
                    if(gamesManagerController.StatusUsername(message.getUserName(), gamesManagerController.LobbyByUsername(message.getUserName()))) {
                        client.update(new Message(Event.LOGIN));
                    }else if(gamesManagerController.LobbyByUsername(message.getUserName()).isFull()){
                        if(gamesManagerController.LobbyByUsername(message.getUserName()).getStatusLobby()) {
                            if(gamesManagerController.LobbyByUsername(message.getUserName()).getOnlineplayers()==1){
                                gamesManagerController.LobbyByUsername(message.getUserName()).resetFinalTimer();
                            }

                            gamesManagerController.LobbyByUsername(message.getUserName()).insertPlayer(client, message.getUserName());
                            gamesManagerController.insertPlayer(client,gamesManagerController.LobbyByUsername(message.getUserName()), message.getUserName());

                            if(gamesManagerController.LobbyByUsername(message.getUserName()).getOnlineplayers()>2){
                                client.update(new Message(Event.RECONNECTION,gamesManagerController.LobbyByUsername(message.getUserName()).getChat(),gamesManagerController.LobbyByUsername(message.getUserName()).getModel()));
                            }else {
                                if(gamesManagerController.LobbyByUsername(message.getUserName()).getStatusCurrentPlayer()&&!gamesManagerController.LobbyByUsername(message.getUserName()).getCurrentPlayer().equals(message.getUserName())){
                                    client.update(new Message(Event.RECONNECTION,gamesManagerController.LobbyByUsername(message.getUserName()).getChat(),gamesManagerController.LobbyByUsername(message.getUserName()).getModel()));

                                }else {
                                    gamesManagerController.LobbyByUsername(message.getUserName()).getController().update(client, new Message(Event.NEW_TURN_RECONNECTED));
                                }

                            }
                        }
                    }else{
                        gamesManagerController.LobbyByUsername(message.getUserName()).insertPlayer(client, message.getUserName());
                        gamesManagerController.insertPlayer(client,gamesManagerController.LobbyByUsername(message.getUserName()), message.getUserName());
                        currentLobby = gamesManagerController.getLobbyByClient(client);
                        client.update(new Message(Event.WAIT_START_OF_MATCH, currentLobby.getClientsUsername() , currentLobby.getnPlayers()));
                    }
                } else {

                Lobby lobby = this.gamesManagerController.addPlayerToLobby(client, message.getnPlayers(), message.getUserName());
                currentLobby = gamesManagerController.getLobbyByClient(client);
                client.update(new Message(Event.WAIT_START_OF_MATCH, currentLobby.getClientsUsername() , currentLobby.getnPlayers()));

                    if (lobby != null) {
                        lobby.checkStartMatch();
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



    public void Lobbydeletion(Lobby l,ArrayList<Integer> index){
        for (String a : l.getClientsUsername()) {
            //codice per rimuovere parola dal file properties
            FileInputStream ip;
            try {
                ip = new FileInputStream(configFilePath);
                prop.load(ip);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            prop.remove(a);
            try {
                prop.store(new FileOutputStream(configFilePath), null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for(Client c: l.getClients()){
            gamesManagerController.removePlayer(c);
        }
        index.add(gamesManagerController.getLobbies_list().indexOf(l));
    }

    private void initialize(){
        destroy_array=new ArrayList<>();
        gamesManagerController = new GamesManagerController();
        currentLobby = null;
        final ArrayList<Integer> index = new ArrayList<>();
        FileInputStream ip;
        try {
            ip = new FileInputStream(configFilePath);
            prop.load(ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        prop.clear();
        try {
            prop.store(new FileOutputStream(configFilePath), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(){
            @Override
            public void run(){
                while (true){
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    for(Lobby l : gamesManagerController.getLobbies_list()){
                        if(l!=null&&l.isFull()){
                            if(l.validateLobby()){
                                if(l.getOnlineplayers()==1&&l.getFinalFlag()){
                                    l.setForcedEnd();
                                }
                            }
                            if(l.getCurrentPlayer()!=null&&!l.getStatusPlayer(l.getCurrentPlayer())&&l.getOnlineplayers()!=1&&l.getStatusLobby()){
                                l.getController().update(l.getClientByUsername(l.getCurrentPlayer()), new Message(Event.CONNECTION_PROBLEM));
                            }else if(!l.validateLobby()&&l.isFull()){
                                //for(String s:l.getClientsUsername()){
                                    //if(l.getStatusPlayer(s)){
                                        l.getController().update(l.getClientByUsername(l.getWinner()),new Message(l.getWinner(),Event.FORCED_END_MATCH));
                                        //l.getClientByUsername(l.getWinner()).update(l.getClientByUsername(l.getWinner()),new Message(s,Event.FORCED_END_MATCH));
                                        Lobbydeletion(l,index);
                                    //}
                                //}
                            }else if(!l.getStatusLobby()&&l.isFull()){
                                Lobbydeletion(l,index);
                            }
                        }
                    }
                    for(Integer i:index){
                        gamesManagerController.removeLobby(i);
                    }
                    index.clear();

                }
            }
        }.start();
    }


}
