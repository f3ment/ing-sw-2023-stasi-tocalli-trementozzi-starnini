package view;

import javafx.scene.effect.SepiaTone;
import model.Message;
import utils.Event;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class GraphicalUI extends View implements Runnable {
    private ScenesController GuiController;

    private ArrayList<String> nicknames;

    ArrayList<String> players = new ArrayList<String>();


    @Override
    public void run() {
        System.out.println("sono nella gui");
    }


    /**
     * this method sets the controller that will be used to change the javafx scene
     *
     * @param controller the controller that will be used to change the scene
     */
    public void setGuiController(ScenesController controller){
        GuiController = controller;
    }

    @Override
    /**
     * this method will be called by the server when a new message
     * is received from the server and will update the gui
     *
     * @param message the message received from the server
     */
    public void update(Message message){
        if(message.getEvent().equals(Event.LOGIN)){
            Platform.runLater(() -> {
                try {
                    HelloApplication.setScene("playerInfo");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else if ( message.getEvent().equals(Event.WAIT_START_OF_MATCH)){
            Platform.runLater(() -> {
                nicknames = message.getNicknames();
                try {
                    HelloApplication.setScene("Lobby");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                GuiController.addPlayerNameToLobby(nicknames);
            });

        }
    }

}


