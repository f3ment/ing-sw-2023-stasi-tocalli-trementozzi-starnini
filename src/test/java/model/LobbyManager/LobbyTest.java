package model.LobbyManager;

import controller.GameController;
import controller.LobbyManager.Lobby;
import distributed.Client;
import distributed.ClientImpl;
import distributed.ServerImpl;
import model.Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {
    @Test
    void lobbyTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        assertFalse(lobby.isFull());
    }

    @Test
    void setidTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        lobby.setId("lobbyTest");
        assertEquals("lobbyTest", lobby.getId());

    }
    @Test
    void insertTest() throws RemoteException {
        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl()),"mike");
        lobby.insertPlayer(new ClientImpl(new ServerImpl()),"tony");
        lobby.insertPlayer(new ClientImpl(new ServerImpl()),"roky");
        assertEquals(4, lobby.getnPlayers());
    }

    @Test
    void setModelTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        ArrayList<String> nomi = new ArrayList<String>();
        nomi.add("toky");
        nomi.add("mike");
        lobby.setModel(new Game(nomi));

    }

    @Test
    void getClientUsernameTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        ArrayList<String> nomi = lobby.getClientsUsername();
        assertEquals("toky", nomi.get(0));

    }

    @Test
    void GameInitTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl()),"mike");
        lobby.game_init();
        assertNotNull(lobby.getController());
    }

    @Test
    void getClientsTest() throws IOException {
        Client client = new ClientImpl(new ServerImpl());
        Lobby lobby = new Lobby(4, "toky", client);
        lobby.insertPlayer(new ClientImpl(new ServerImpl()),"mike");
        ArrayList<Client> nomi =  lobby.getClients();
        assertTrue(nomi.size() == lobby.getClientsUsername().size());
    }




}