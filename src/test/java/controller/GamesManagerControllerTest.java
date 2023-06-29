package controller;

import distributed.Client;
import distributed.ClientImpl;
import distributed.ServerImpl;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class GamesManagerControllerTest {

    @Test
    void addPLayerToLobbyTest() throws RemoteException {
        GamesManagerController gamesManagerController = new GamesManagerController();
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = gamesManagerController.addPlayerToLobby(client, 2, "toky");
        assertNull(lobby);
        lobby = gamesManagerController.addPlayerToLobby(client, 2, "mike");
        assertNotNull(lobby);
        assertTrue(gamesManagerController.checkStart(lobby));
    }

    @Test
    void getLobbyByClientTest() throws RemoteException {
        GamesManagerController gamesManagerController = new GamesManagerController();
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = gamesManagerController.addPlayerToLobby(client, 2, "toky");
        assertNull(lobby);
        lobby = gamesManagerController.addPlayerToLobby(client, 2, "mike");
        Lobby lobby2 = gamesManagerController.LobbyByUsername("toky");
        assertEquals(lobby2,lobby);
        Lobby lobby3 = gamesManagerController.getLobbyByClient(client);
        assertEquals(lobby3,lobby);
    }

    @Test
    void statusUsernameTest() throws RemoteException {
        GamesManagerController gamesManagerController = new GamesManagerController();
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = gamesManagerController.addPlayerToLobby(client, 2, "toky");
        assertNull(lobby);
        lobby = gamesManagerController.addPlayerToLobby(client, 2, "mike");
        assertTrue(gamesManagerController.StatusUsername("toky",lobby));
    }

    @Test
    void getLobbiesListTest() throws RemoteException {
        GamesManagerController gamesManagerController = new GamesManagerController();
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = gamesManagerController.addPlayerToLobby(client, 2, "toky");
        assertNull(lobby);
        lobby = gamesManagerController.addPlayerToLobby(client, 2, "mike");
        assertTrue(gamesManagerController.getLobbies_list().contains(lobby));
    }

    @Test
    void insertPLayerTest() throws RemoteException {
        GamesManagerController gamesManagerController = new GamesManagerController();
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = gamesManagerController.addPlayerToLobby(client, 2, "toky");
        assertNull(lobby);
        lobby = gamesManagerController.addPlayerToLobby(client, 2, "mike");
        gamesManagerController.insertPlayer(client,lobby,"toky");
        assertTrue(lobby.isUsernameContained("toky"));
    }

    @Test
    void removePLayerTest() throws RemoteException {
        GamesManagerController gamesManagerController = new GamesManagerController();
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = gamesManagerController.addPlayerToLobby(client, 2, "toky");
        assertNull(lobby);
        lobby = gamesManagerController.addPlayerToLobby(client, 2, "mike");
        gamesManagerController.removePlayer(client);
        assertNull(gamesManagerController.getLobbyByClient(client));
    }


    @Test
    void removeLobbyTest() throws RemoteException {
        GamesManagerController gamesManagerController = new GamesManagerController();
        Client client = new ClientImpl(new ServerImpl(),true);
        Lobby lobby = gamesManagerController.addPlayerToLobby(client, 2, "toky");
        assertNull(lobby);
        lobby = gamesManagerController.addPlayerToLobby(client, 2, "mike");
        gamesManagerController.removeLobby(0);
        assertTrue(gamesManagerController.getLobbies_list().isEmpty());
    }

}