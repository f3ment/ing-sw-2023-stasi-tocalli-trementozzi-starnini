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
    private HashMap<String, Client> usersId;
    private HashMap<String,Timer> timerPlayers;
    private HashMap<String,Boolean> status;
    private Timer timerOneLeftPlayer=new Timer();
    private boolean isFull;
    private boolean on;
    private int onlineplayers=0;
    private boolean oneleft;
    private boolean valid=true;

    private String winner;

    public Lobby(int nPlayers, String userName, Client client){
        this.nPlayers = nPlayers;
        usersId = new HashMap<String,Client>(nPlayers);
        status=new HashMap<String,Boolean>(nPlayers);
        status.put(userName,true);
        usersId.put(userName,client);
        on=true;
        oneleft=false;
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setClientOffLine(userName);
            }
        },8000);
        timerPlayers=new HashMap<String,Timer>(nPlayers);
        timerPlayers.put(userName,timer);
        if(usersId.size() == nPlayers){
            isFull = true;
        }else{
            isFull = false;
        }
        onlineplayers++;

        chat = new Chat();
        chatController = new ChatController(chat);
        this.chat.addObserver((o, message) -> {
            try {
                if(client.equals(getClientByUsername(message.getUserName()))) {
                    client.update(new Message(message.getUserName(), (Event) message.getEvent(), new ChatView(chat)));
                }else if(message.getEvent().equals(Event.SEND_MESSAGE)){
                    if( message.getChatMessage().getReceiver()!= null && (client.equals(getClientByUsername(message.getChatMessage().getReceiver()))
                            || client.equals(getClientByUsername(message.getChatMessage().getSender())))){
                        client.update(new Message(message.getUserName(), (Event) message.getEvent(), new ChatView(chat)));
                    }else if (message.getChatMessage().getReceiver() == null){
                        client.update(new Message(message.getUserName(), (Event) message.getEvent(), new ChatView(chat)));
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

    public synchronized boolean insertPlayer(Client user,String userId){
        if(isFull&&!getStatusPlayer(userId)) {
            on=true;
            status.put(userId,true);
            usersId.put(userId,user);
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setClientOffLine(userId);
                }
            },8000);
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
            },8000);
            timerPlayers.put(userId,timer);
            if(usersId.size() == nPlayers){
                isFull = true;
            }
            onlineplayers++;
        }

        this.chat.addObserver((o, message) -> {
            try {
                if(user.equals(getClientByUsername(message.getUserName()))) {
                    user.update(new Message(message.getUserName(), (Event) message.getEvent(), new ChatView(chat)));
                }else if(message.getEvent().equals(Event.SEND_MESSAGE)){
                    if( message.getChatMessage().getReceiver()!= null && (user.equals(getClientByUsername(message.getChatMessage().getReceiver()))
                            || user.equals(getClientByUsername(message.getChatMessage().getSender())))){
                        user.update(new Message(message.getUserName(), (Event) message.getEvent(), new ChatView(chat)));
                    }else if (message.getChatMessage().getReceiver() == null){
                        user.update(new Message(message.getUserName(), (Event) message.getEvent(), new ChatView(chat)));
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
        },8000);
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
            this.model = new Game(new ArrayList<String>(usersId.keySet()));
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
                            System.out.println("inoltra");
                            System.out.println(message.getEvent().toString());
                            usersId.get(s).update(new Message(new GameView(model), (Event) message.getEvent()));
                        }else{
                            System.out.println("death");
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

    public synchronized String getUsernameByclient(Client client){
        return getClientsUsername().get(getClients().indexOf(client));
    }

    public synchronized void setClientOffLine(String username){
        status.put(username,false);
        System.out.println("morto");
        onlineplayers--;
        boolean flag=false;
        for(String s: status.keySet()){
            if(status.get(s)){
                flag=true;
            }
        }
        if(!flag){
            on=false;
            resetFinalTimer();
            oneleft=false;
        }
        if(onlineplayers==1){
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
    public boolean isUsernameContained(String username){
        if(usersId.containsKey(username)){
            return true;
        }
        return false;
    }

    public Client getClientByUsername(String username){
        return usersId.get(username);
    }

    public String getCurrentPlayer(){
        TablePosition position;
        try{
            position=model.getCurrentPosition();
        }catch(NullPointerException e){
            return null;
        }
        return position.getPlayer().getUsername();
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
        timerOneLeftPlayer=new Timer();
        timerOneLeftPlayer.schedule(new TimerTask() {
            @Override
            public void run() {
                valid=false;
            }
        },60000);
    }
    public void resetFinalTimer(){
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
}
