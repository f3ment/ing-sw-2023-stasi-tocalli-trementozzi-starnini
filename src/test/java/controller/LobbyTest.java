package controller;

import controller.Lobby;
import distributed.Client;
import distributed.ClientImpl;
import distributed.ServerImpl;
import model.Game;
import model.Message;
import org.junit.jupiter.api.Test;
import utils.Event;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {
    //todo da rifare
    @Test
    void lobbyTest() throws IOException {
        String data = "1" +
                "\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        assertFalse(lobby.isFull());
    }

    @Test
    void setidTest() throws RemoteException {

    }
    @Test
    void insertTest() throws RemoteException {
            }

    @Test
    void setModelTest() throws IOException {
        String data = "1" +
                "\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("toky");
        nomi.add("mike");
        lobby.setModel(new Game(nomi));

    }

    @Test
    void getClientUsernameTest() throws IOException {
        String data = "1" +
                "\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        ArrayList<String> nomi = lobby.getClientsUsername();
        assertEquals("toky", nomi.get(0));

    }

    @Test
    void GameInitTest() throws IOException {
        String data = "1" +
                "\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        lobby.insertPlayer(new ClientImpl(new ServerImpl()),"mike");
        lobby.game_init();
        assertNotNull(lobby.getController());
    }

    @Test
    void getClientsTest() throws IOException {
        String data = "1" +
                "\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        lobby.insertPlayer(new ClientImpl(new ServerImpl()),"mike");
        ArrayList<Client> nomi =  lobby.getClients();
        assertTrue(nomi.size() == lobby.getClientsUsername().size());
    }

    @Test
    void notifyObserversTest() throws IOException {
        String data = "1" +
                "\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(2, "toky", client);

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        lobby.insertPlayer(new ClientImpl(new ServerImpl()),"mike");
        lobby.game_init();
        lobby.getController().update(client, new Message(Event.NEW_TURN));
    }

}