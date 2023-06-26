package controller;

import distributed.Client;
import model.Chat;
import model.Game;
import model.Message;
import model.views.ChatView;
import model.views.GameView;
import utils.Event;
import model.TablePosition;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Lobby {

    private Game model;
    private Chat chat;
    private ChatController chatController;
    private GameController gameController;
    private String id;
    private int nPlayers;
    private HashMap<String, Client> usersId;  // link clients and their usernames
    private HashMap<String,Timer> timerPlayers; //timers for every ping's player
    private HashMap<String,Boolean> status;  //status online/offline players
    private Timer timerOneLeftPlayer=new Timer();  //timer which starts when there is only one left player online
    private boolean isFull;  //true if the match has beginned
    private boolean on;    //there is at least one player online
    private int onlineplayers=0;  // number of players online
    private boolean oneleft;  //true if there is only one player online
    private boolean valid=true; // when it becomes false, the match is forced to end
    private boolean flagfinal;  // true when timerOneLeftPlayer starts
    private boolean flagFirstPlayer;




    private String winner;

    public Lobby(int nPlayers, String userName, Client client){
        this.nPlayers = nPlayers;
        usersId = new HashMap<>(nPlayers);
        status=new HashMap<>(nPlayers);
        status.put(userName,true);
        usersId.put(userName,client);
        on=true;
        flagfinal=true;
        oneleft=false;
        flagFirstPlayer=false;
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setClientOffLine(userName);
            }
        },4000);
        timerPlayers=new HashMap<>(nPlayers);
        timerPlayers.put(userName,timer);
        isFull = usersId.size() == nPlayers;
        onlineplayers++;

        chat = new Chat();
        chatController = new ChatController(chat);
        this.chat.addObserver((o, message) -> {
            try {
                if(client.equals(getClientByUsername(message.getUserName()))) {
                    client.update(new Message(message.getUserName(), message.getEvent(), new ChatView(chat)));
                }else if(message.getEvent().equals(Event.SEND_MESSAGE)){
                    if( message.getChatMessage().getReceiver()!= null && (client.equals(getClientByUsername(message.getChatMessage().getReceiver()))
                            || client.equals(getClientByUsername(message.getChatMessage().getSender())))){
                        client.update(new Message(message.getUserName(), message.getEvent(), new ChatView(chat)));
                    }else if (message.getChatMessage().getReceiver() == null){
                        client.update(new Message(message.getUserName(), message.getEvent(), new ChatView(chat)));
                    }
                }
            } catch (RemoteException e) {
                System.err.println("Error while updating the client : " + e.getMessage() + ". Skipping the update...");
            }
        });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    //returns true if usersId are full;
    // insert a player in the lobby. if he already exists turn him online
    public synchronized boolean insertPlayer(Client user,String userId){
        if(isFull&&!getStatusPlayer(userId)) {
            on=true;
            status.put(userId,true);
            usersId.put(userId,user);
            model.getPlayerByNickname().get(userId).setStatus(true);
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setClientOffLine(userId);
                }
            },4000);
            timerPlayers.put(userId,timer);
            onlineplayers++;
            if(onlineplayers>1){
                oneleft=false;
            }
        }else if(!isFull){
            status.put(userId,true);
            usersId.put(userId,user);
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setClientOffLine(userId);
                }
            },4000);
            timerPlayers.put(userId,timer);
            if(usersId.size() == nPlayers){
                isFull = true;
            }
            onlineplayers++;
        }

        this.chat.addObserver((o, message) -> {
            try {
                if(user.equals(getClientByUsername(message.getUserName()))) {
                    user.update(new Message(message.getUserName(), message.getEvent(), new ChatView(chat)));
                }else if(message.getEvent().equals(Event.SEND_MESSAGE)){
                    if( message.getChatMessage().getReceiver()!= null && (user.equals(getClientByUsername(message.getChatMessage().getReceiver()))
                            || user.equals(getClientByUsername(message.getChatMessage().getSender())))){
                        user.update(new Message(message.getUserName(), message.getEvent(), new ChatView(chat)));
                    }else if (message.getChatMessage().getReceiver() == null){
                        user.update(new Message(message.getUserName(), message.getEvent(), new ChatView(chat)));
                    }
                }
            } catch (RemoteException e) {
                System.err.println("Error while updating the client : " + e.getMessage() + ". Skipping the update...");
            }
        });
        return true;
    }

    public void resetTimer(String username){
        timerPlayers.get(username).cancel();
        Timer timero=new Timer();
        timero.schedule(new TimerTask() {
            @Override
            public void run() {
                setClientOffLine(username);
            }
        },4000);
        timerPlayers.put(username,timero);
    }

    public Boolean getStatusPlayer(String username){
        return status.get(username);
    }
    public boolean isFull() {
        return isFull;
    }

    public void setModel(Game model) {
        this.model = model;
    }

    public ArrayList<Client> getClients() {
        return new ArrayList<>(usersId.values());
    }

    public ArrayList<String> getClientsUsername() {
        return new ArrayList<>(usersId.keySet());
    }

    public void game_init() {
        try {
            this.model = new Game(new ArrayList<>(usersId.keySet()));
            for(String s:usersId.keySet()){
                if(getStatusPlayer(s)){
                    model.getPlayerByNickname().get(s).setStatus(true);
                }
            }
            this.gameController = new GameController(model, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*for(Client c:usersId.values()){
            this.model.addObserver((o, message) -> {
                try {
                    c.update(new Message(new GameView(model), (Event) message.getEvent()));
                } catch (RemoteException e) {
                    System.err.println("Error while updating the client : " + e.getMessage() + ". Skipping the update...");
                }
            });
        }*/
       for (String s : usersId.keySet()) {
                this.model.addObserver((o, message) -> {
                    try {
                        if(getStatusPlayer(s)) {
                            System.out.println(message.getEvent().toString());
                            usersId.get(s).update(new Message(new GameView(model), message.getEvent()));
                        }else{
                        }
                    } catch (RemoteException e) {
                        System.err.println("Error while updating the client : " + e.getMessage() + ". Skipping the update...");

                    }
                });
        }
    }

    public GameController getController() {
        return gameController;
    }

    public ChatController getChatController() {
        return this.chatController;
    }

    public synchronized String getUsernameByClient(Client client){
        return getClientsUsername().get(getClients().indexOf(client));
    }

    public synchronized void setClientOffLine(String username){
        status.put(username,false);
        System.out.println("disconnected");
        if(isFull){
            model.getPlayerByNickname().get(username).setStatus(false); //set player offline
        }
        onlineplayers--;
        boolean flag=false;
        for(String s: status.keySet()){
            if(status.get(s)){
                flag=true;
            }
        }
        if(!flag&&isFull){
            on=false;
            resetFinalTimer();
            oneleft=false;
        }
        if(onlineplayers==1&&isFull){
            oneleft=true;
            setFinalTimer();
            for(String s : usersId.keySet()){
                if(getStatusPlayer(s)){
                    winner=s;
                    break;
                }
            }
        }
    }

    public void setForcedEnd(){
        oneleft=true;
        setFinalTimer();
        for(String s : usersId.keySet()){
            if(getStatusPlayer(s)){
                winner=s;
                break;
            }
        }
    }
    public boolean isUsernameContained(String username){
        return usersId.containsKey(username);
    }

    public Client getClientByUsername(String username){
        return usersId.get(username);
    }

    public String getCurrentPlayer(){
        TablePosition position;
        try{
            position=model.getCurrentPosition();
            return position.getPlayer().getUsername();
        }catch(NullPointerException e){
            return null;
        }
    }
    public boolean getStatusLobby(){
        return on;
    }

    public synchronized int getOnlineplayers(){
        return onlineplayers;
    }
    public synchronized boolean onlyOne(){
        return oneleft;
    }
    public synchronized void setOne(boolean value){
        oneleft=value;
    }

    public void setFinalTimer(){
        flagfinal=false;
        timerOneLeftPlayer=new Timer();
        timerOneLeftPlayer.schedule(new TimerTask() {
            @Override
            public void run() {
                valid=false;
            }
        },30000);
    }
    public void resetFinalTimer(){
        flagfinal=true;
        timerOneLeftPlayer.cancel();
        if(onlineplayers!=1){
            oneleft=false;
            winner=null;
        }
    }
    public boolean validateLobby(){
        return valid;
    }

    public String getWinner() {
        return winner;
    }

    public GameView getModel() {
        return new GameView(model);
    }

    public HashMap<String, Boolean> getStatusPlayers() {
        return status;
    }

    public void checkStartMatch(){
        boolean flag=false;
        for(String s:status.keySet()){
            if(status.get(s)==false){
                flag=true;
                break;
            }
        }
        if(flag){
            for(String s: status.keySet()){
                if(status.get(s)){
                    model.setCurrentPlayer(s);
                    break;
                }
            }
        }
    }
    public String getFirstPlayer(){
        return model.getFirstPlayer();
    }

    public boolean getFinalFlag(){
        return flagfinal;
    }

    public boolean getStatusCurrentPlayer(){
        return status.get(getCurrentPlayer());
    }

    public boolean getEndGame(){
        return model.getEndGame();
    }

    public String getFirstFinisher(){
        return model.getFirstFinisher();
    }

    public boolean getFlagFirstPlayer(){
        return flagFirstPlayer;
    }

    public void setFlagFirstPlayer(){
        flagFirstPlayer=true;
    }

    public void updateLastScore(){
        model.updateLastScore();
    }
}