package controller;

import distributed.ClientImpl;
import distributed.ServerImpl;
import model.Game;
import model.Message;
import org.junit.jupiter.api.Test;
import utils.Event;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    ArrayList<String> playerNames = new ArrayList<>();

    @Test
    void UpdateTest() throws IOException {
        try {
            playerNames.add("Player 1");
            playerNames.add("Player 2");
            playerNames.add("Player 3");
            playerNames.add("Player 4");
            ClientImpl client = new ClientImpl(new ServerImpl(),true);
            Lobby lobby = new Lobby(4,"toky",client);
            Game game= new Game(playerNames);
            game.setEndGame(true);
            GameController gameController = new GameController(game,lobby);
            assertNotNull(gameController);
            ArrayList<ArrayList<Integer>> coords = new ArrayList<>();
            ArrayList<Integer> coord = new ArrayList<>();
            coord.add(0);
            coord.add(3);
            coords.add(coord);
            gameController.update(client,new Message(Event.PLAYER_DRAW_NEGATIVE,coords));
            gameController.update(client,new Message(Event.PLAYER_DRAW_POSITIVE,coords));
            gameController.update(client,new Message(Event.PLAYER_INSERT_POSITIVE,3,coords));
            gameController.update(client,new Message(Event.PLAYER_DRAW_POSITIVE,coords));
            gameController.update(client,new Message(Event.PLAYER_INSERT_POSITIVE,coords));
            gameController.update(client,new Message(Event.CONNECTION_PROBLEM));
            gameController.update(client,new Message(Event.PLAYER_INSERT_NEGATIVE,coords));
            coord.clear();
            coord.add(1);
            coord.add(4);
            coords.clear();
            coords.add(coord);
            gameController.update(client,new Message(Event.PLAYER_DRAW_POSITIVE,coords));
            gameController.update(client,new Message(Event.PLAYER_INSERT_POSITIVE,3,coords));
            gameController.update(client,new Message(Event.NEW_TURN_RECONNECTED));
            gameController.update(client,new Message(Event.NEW_TURN));
            gameController.update(client,new Message(Event.LOGIN_TRUE));
            gameController.update(client,new Message(Event.FORCED_END_MATCH));
            gameController.update(client,new Message(Event.FINISH_MATCH));
            gameController.update(client,new Message(Event.CLIENT_CLOSE));
            lobby.setModel(game);
            lobby.setEndGame(true);
            gameController.update(client,new Message(Event.PLAYER_FINISH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}