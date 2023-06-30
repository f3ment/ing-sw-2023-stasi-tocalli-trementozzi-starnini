package controller;

import distributed.Client;
import distributed.ClientImpl;
import distributed.ServerImpl;
import model.ChatMessage;
import model.Game;
import model.Message;
import model.views.ChatView;
import org.junit.jupiter.api.Test;
import utils.Event;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {
    @Test
    void lobbyTest() throws IOException {

        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(4, "toky", client);
        assertFalse(lobby.isFull());
    }

    @Test
    void setidTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(4, "toky", client);
        lobby.setId("1");
        assertEquals("1" , lobby.getId());
    }
    @Test
    void insertPlayerTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        assertTrue(lobby.isFull());
        lobby.game_init();
        lobby.setClientOffLine("mike");
        lobby.setClientOffLine("toky");
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"toky");
    }

    @Test
    void setModelTest() throws IOException {

        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(4, "toky", client);
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("toky");
        nomi.add("mike");
        lobby.setModel(new Game(nomi));

    }

    @Test
    void getClientUsernameTest() throws IOException {

        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(4, "toky", client);
        ArrayList<String> nomi = lobby.getClientsUsername();
        assertEquals("toky", nomi.get(0));

    }

    @Test
    void GameInitTest() throws IOException {

        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(4, "toky", client);

        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        lobby.game_init();
        assertNotNull(lobby.getController());
    }

    @Test
    void getClientsTest() throws IOException {


        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(4, "toky", client);

        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        ArrayList<Client> nomi =  lobby.getClients();
        assertTrue(nomi.size() == lobby.getClientsUsername().size());
    }

    @Test
    void notifyObserversTest() throws IOException {


        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);

        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        lobby.game_init();
        lobby.getController().update(client, new Message(Event.NEW_TURN));
    }

    @Test
    void setToRemoveTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.setToRemove(true);
        assertEquals(lobby.getnPlayers(),2);
        assertTrue(lobby.isToRemove());
    }

    @Test
    void resetTimerTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        lobby.resetTimer("mike");
    }

    @Test
    void getServerTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        ServerImpl server = new ServerImpl();
        lobby.setServer(server);
        lobby.getChangePosition();
        assertEquals(server, lobby.getServer());
    }

    @Test
    void getChatTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        assertTrue(lobby.getChatView() instanceof ChatView);
    }

    @Test
    void getChatControllerTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        assertTrue(lobby.getChatController() instanceof ChatController);
    }

    @Test
    void getUsernameByClientTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        Client client1 = new ClientImpl(new ServerImpl(),true);
        lobby.insertPlayer(client1,"mike");
        assertTrue(lobby.getUsernameByClient(client1).equals("mike"));
    }

    @Test
    void setForcedEndTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        Client client1 = new ClientImpl(new ServerImpl(),true);
        lobby.insertPlayer(client1,"mike");
        lobby.game_init();
        lobby.setForcedEnd();
        lobby.getWinner();
    }

    @Test
    void isUsernameContainedTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        Client client1 = new ClientImpl(new ServerImpl(),true);
        lobby.insertPlayer(client1,"mike");
        assertTrue(lobby.isUsernameContained("mike"));
    }

    @Test
    void getClientByUsernameTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        Client client1 = new ClientImpl(new ServerImpl(),true);
        lobby.insertPlayer(client1,"mike");
        assertEquals(client1, lobby.getClientByUsername("mike"));
    }

    @Test
    void getCurrentPositionTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        Client client1 = new ClientImpl(new ServerImpl(),true);
        lobby.insertPlayer(client1,"mike");
        lobby.game_init();
        assertTrue(lobby.getCurrentPlayer().equals("mike") || lobby.getCurrentPlayer().equals("toky"));
    }

    @Test
    void getStatusLobbyTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        Client client1 = new ClientImpl(new ServerImpl(),true);
        lobby.insertPlayer(client1,"mike");
        assertTrue(lobby.getStatusLobby());
    }

    @Test
    void getOnlinePLayersTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        Client client1 = new ClientImpl(new ServerImpl(),true);
        lobby.insertPlayer(client1,"mike");
        assertEquals(lobby.getOnlinePlayers(),2);
    }

    @Test
    void getOneLeftTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        Client client1 = new ClientImpl(new ServerImpl(),true);
        lobby.insertPlayer(client1,"mike");
        lobby.setOne(true);
        assertTrue(lobby.onlyOne());
    }

    @Test
    void resetFinalTimerTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.resetFinalTimer();
        assertTrue(lobby.getFinalFlag());
    }

    @Test
    void validateLobbyTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        assertTrue(lobby.validateLobby());
    }

    @Test
    void getModelTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        lobby.game_init();
        assertNotNull(lobby.getModel());
    }

    @Test
    void getStatusPlayerTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        assertTrue(lobby.getStatusPlayers().get("mike"));
        lobby.game_init();
        assertEquals(lobby.getFirstPlayer(),lobby.getCurrentPlayer());
        assertTrue(lobby.getStatusCurrentPlayer());
        lobby.setClientOffLine(lobby.getFirstPlayer());
        lobby.checkStartMatch();
    }

    @Test
    void getEndGameTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        lobby.game_init();
        assertFalse(lobby.getEndGame());
        assertNull(lobby.getFirstFinisher());
    }

    @Test
    void changeCurrentPositionTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl(),true),"mike");
        lobby.game_init();
        String first = lobby.getCurrentPlayer();
        lobby.ChangeCurrentPosition();
        assertNotEquals(first,lobby.getCurrentPlayer());
    }

    @Test
    public void insertNotFull() throws RemoteException{
        Client client = new ClientImpl(new ServerImpl(),true);
        ServerImpl g = new ServerImpl();
        Client c= new ClientImpl(g,true);
        Client v= new ClientImpl(g,true);
        Client a= new ClientImpl(g,true);
        g.update(v,new Message(Event.LOGIN,3,"arrivederci"));
        g.update(a,new Message(Event.LOGIN,3,"arrivederci"));
        Lobby lobby = new Lobby(2, "toky", client);
        lobby.getChat().setChangedAndNotifyObservers(new Message(Event.SEND_MESSAGE,new ChatMessage("sdf","dfgh","dfg")));
        lobby.getChat().setChangedAndNotifyObservers(new Message(Event.SEND_MESSAGE,new ChatMessage("sdf","toky","dfg")));
        lobby.insertPlayer(c,"tok");
        lobby.getChat().setChangedAndNotifyObservers(new Message(Event.SEND_MESSAGE,new ChatMessage("sdf","tok","dfg")));
        lobby.getChat().setChangedAndNotifyObservers(new Message(Event.SEND_MESSAGE,new ChatMessage("sdf","234567","dfg")));

    }



}