package distributed;

import controller.GamesManagerController;
import controller.Lobby;
import model.Message;
import utils.Event;
import view.Color;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
* This class implements server.
* It links the model and the controller to the communication protocol.
*/
public class ServerImpl extends UnicastRemoteObject implements Server {

    private Lobby currentLobby;

    private GamesManagerController gamesManagerController;

    private ArrayList<Integer> destroy_array = new ArrayList<>();

    /**
     * Initialize the server.
     * @throws RemoteException if the remote object cannot be exported
     */
    public ServerImpl() throws RemoteException {
        super();
        initialize();
    }

    /**
     * Initialize the server.
     * @param port the port number on which the server accepts requests
     * @throws RemoteException if the remote object cannot be exported
     */
    public ServerImpl(int port) throws RemoteException {
        super(port);
        initialize();
    }

    /**
     * Initialize the server.
     * @param port the port number on which the server accepts requests
     * @param csf the client-side socket factory for making calls to the remote object
     * @param ssf the server-side socket factory for receiving remote calls
     * @throws RemoteException if the remote object cannot be exported
     */
    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        initialize();
    }

    /**
     * register a client to the server
     * @param client Client object to be registered
     * @throws RemoteException if the remote object cannot be exported
     */
    @Override
    public void register(Client client) throws RemoteException{
        System.out.println(Color.GREEN_BRIGHT + "Client correctly registered" + Color.RESET);
    }

    /**
     * This method receive clients messages and redirects them
     * to the right game controller based on the client lobby
     * @param client  Client that sends the message
     * @param message Message object
     * @throws RemoteException if the remote object cannot be exported
     */
    @Override
    public void update(Client client, Message message) throws RemoteException {
        currentLobby = this.gamesManagerController.getLobbyByClient(client);
        if (currentLobby != null && (message.getEvent().equals(Event.GET_CHAT) || message.getEvent().equals(Event.EXIT_CHAT) || message.getEvent().equals(Event.SEND_MESSAGE))) {
            currentLobby.getChatController().update(client, message);
        } else if (!message.getEvent().equals(Event.GAME_INIT) && !message.getEvent().equals(Event.LOGIN)) {
            /**
             *different clients notify players game's choices
             */
            if (message.getEvent().equals(Event.PING)) {
                client.update(new Message(Event.PING));
                currentLobby = this.gamesManagerController.getLobbyByClient(client);
                if(currentLobby!=null) {
                    String username = gamesManagerController.getLobbyByClient(client).getUsernameByClient(client);
                    gamesManagerController.getLobbyByClient(client).resetTimer(username);
                }
            } else {
                if (message.getEvent().equals(Event.DELETE_MATCH)) {
                    deleteMatch(client);
                }else {
                    currentLobby.getController().update(client, message);
                }
            }
            /**
             * player sends nickname and number of players to join a lobby
             * */
        } else if (message.getEvent().equals(Event.LOGIN)) {
            boolean correctusername = true;
                synchronized (gamesManagerController) {
                    if (gamesManagerController.getClients(message.getUserName())) {
                        correctusername = false;
                    }
                }

            if (!correctusername) {
                if(gamesManagerController.StatusUsername(message.getUserName(), gamesManagerController.LobbyByUsername(message.getUserName()))) {
                    client.update(new Message(Event.LOGIN, Color.RED_BOLD + message.getUserName()+" Is Already Playing" + Color.RESET));
                }else if(!gamesManagerController.StatusUsername(message.getUserName(), gamesManagerController.LobbyByUsername(message.getUserName()))&&gamesManagerController.LobbyByUsername(message.getUserName()).getnPlayers()!=message.getnPlayers()) {
                    client.update(new Message(Event.LOGIN,Color.RED_BOLD + message.getUserName()+" is playing in a Lobby with a different number of players" + Color.RESET));
                }else if(gamesManagerController.LobbyByUsername(message.getUserName()).isFull()){
                    if(gamesManagerController.LobbyByUsername(message.getUserName()).getStatusLobby()) {
                        if(gamesManagerController.LobbyByUsername(message.getUserName()).getOnlinePlayers()==1){
                            gamesManagerController.LobbyByUsername(message.getUserName()).resetFinalTimer();
                        }

                        gamesManagerController.LobbyByUsername(message.getUserName()).insertPlayer(client, message.getUserName());
                        gamesManagerController.insertPlayer(client,gamesManagerController.LobbyByUsername(message.getUserName()), message.getUserName());

                        if(gamesManagerController.LobbyByUsername(message.getUserName()).getOnlinePlayers()>2){
                            client.update(new Message(Event.RECONNECTION,gamesManagerController.LobbyByUsername(message.getUserName()).getChatView(),gamesManagerController.LobbyByUsername(message.getUserName()).getModel()));
                        }else {
                            if(gamesManagerController.LobbyByUsername(message.getUserName()).getStatusCurrentPlayer()&&!gamesManagerController.LobbyByUsername(message.getUserName()).getCurrentPlayer().equals(message.getUserName())){
                                client.update(new Message(Event.RECONNECTION,gamesManagerController.LobbyByUsername(message.getUserName()).getChatView(),gamesManagerController.LobbyByUsername(message.getUserName()).getModel()));

                            }else {
                                gamesManagerController.LobbyByUsername(message.getUserName()).getController().update(client, new Message(Event.NEW_TURN_RECONNECTED));
                            }

                        }
                    }
                }else{
                    gamesManagerController.LobbyByUsername(message.getUserName()).insertPlayer(client, message.getUserName());
                    gamesManagerController.insertPlayer(client,gamesManagerController.LobbyByUsername(message.getUserName()), message.getUserName());
                    currentLobby = gamesManagerController.getLobbyByClient(client);
                    client.update(new Message(Event.WAIT_START_OF_MATCH, currentLobby.getClientsUsername() , currentLobby.getnPlayers(),currentLobby.getChatView()));
                }
            } else {

                Lobby lobby = this.gamesManagerController.addPlayerToLobby(client, message.getnPlayers(), message.getUserName());
                currentLobby = gamesManagerController.getLobbyByClient(client);
                client.update(new Message(Event.WAIT_START_OF_MATCH, currentLobby.getClientsUsername() , currentLobby.getnPlayers(),currentLobby.getChatView()));

                if (lobby != null) {
                    lobby.checkStartMatch();
                    lobby.getController().update(client, new Message(Event.LOGIN_TRUE));
                    lobby.setServer(this);
                }
            }
            /**
             * hit by the client for server connection and starting of the login procedures
             * */
        } else {
            client.update(new Message(Event.LOGIN));
        }
    }


    /**
     * deletes a lobby
     * @param l lobby to delete
     * @param index index of lobby to delete
     */
    public void Lobbydeletion(Lobby l,ArrayList<Integer> index){
        for(Client c: l.getClients()){
            gamesManagerController.removePlayer(c);
        }
        index.add(gamesManagerController.getLobbies_list().indexOf(l));
    }

    /**
     * initializes the server and starts the thread for the server
     */
    private void initialize(){
        destroy_array=new ArrayList<>();
        gamesManagerController = new GamesManagerController();
        currentLobby = null;
        final ArrayList<Integer> index = new ArrayList<>();

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
                        if(l!=null && l.isFull() && !l.isToRemove()){
                            if(l.validateLobby()){
                                if(l.getOnlinePlayers()==1&&l.getFinalFlag()){
                                    l.setForcedEnd();
                                }
                            }
                            synchronized (l.getChangePosition()){
                                if(l.getCurrentPlayer()!=null && !l.getStatusPlayer(l.getCurrentPlayer()) && l.getOnlinePlayers()>1){
                                    l.getController().update(l.getClientByUsername(l.getCurrentPlayer()), new Message(Event.CONNECTION_PROBLEM));
                                }
                            }
                            if(!l.validateLobby() && l.isFull()){
                                l.getController().update(l.getClientByUsername(l.getWinner()),new Message(l.getWinner(),Event.FORCED_END_MATCH));
                                l.setToRemove(true);
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

        public void deleteMatch(Client client){
            currentLobby.getController().update(client, new Message(Event.CLIENT_CLOSE));
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Lobbydeletion(currentLobby,destroy_array);
            for(Integer i:destroy_array){
                gamesManagerController.removeLobby(i);
            }
            destroy_array.clear();
        }
}
