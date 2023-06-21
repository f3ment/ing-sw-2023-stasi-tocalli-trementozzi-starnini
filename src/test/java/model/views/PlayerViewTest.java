package model.views;

import model.Game;
import model.ItemTiles;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerViewTest {

    @Test
    void test() {
        ArrayList<String> nicknames = new ArrayList();
        nicknames.add("Myke01");
        nicknames.add("Toky33");
        nicknames.add("Sterning17");
        nicknames.add("F3ment");
        Game model;
        try {
            model = new Game(nicknames);
        } catch (IOException e) {
            System.out.println("Error in creating the model");
            throw new RuntimeException(e);
        }

        PlayerView playerView = new PlayerView(model.getListPlayer().get(0));
        assertTrue(playerView.getUsername().equals("Myke01") || playerView.getUsername().equals("Toky33") ||
                playerView.getUsername().equals("Sterning17") || playerView.getUsername().equals("F3ment"));
        System.out.println("PlayerViewTest getUsername: OK");
        assertTrue(playerView.getScore() == 0);
        System.out.println("PlayerViewTest getScore: OK");
        assertTrue(playerView.getTokens().size() == 0);
        System.out.println("PlayerViewTest getTokens: OK");
        assertTrue(playerView.getCurrentPosition().equals(model.getListPlayer().get(0).getCurrentPosition()));
        System.out.println("PlayerViewTest getCurrentPosition: OK");
        assertTrue(playerView.getBookshelf() != null);
        System.out.println("PlayerViewTest getBookshelf: OK");
        assertTrue(playerView.getPickedCards() != null && playerView.getPickedCards().size() == 0);
    }

}