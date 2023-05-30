package view;

import javafx.scene.effect.SepiaTone;
import model.ItemTiles;
import model.Message;
import model.Type;
import model.views.BoxView;
import model.views.GameView;
import utils.Event;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class GraphicalUI extends View implements Runnable {
    private ScenesController GuiController;

    private String username;

    private boolean myTurn = true;

    private ArrayList<String> nicknames;

    ArrayList<String> players = new ArrayList<String>();
    private boolean flagChat;


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
    public void update(Message message) {
        if(message.getEvent().equals(Event.GET_CHAT) && message.getUserName().equals(username)){
            if(message.getChat().getActive().contains(username)){
                this.flagChat = true;
                // todo mostrare chat
                try{
                    message.getChat().getLastTen().forEach(e -> e.forEach(
                            (key, value) -> {
                                if (key.equals(username)) {
                                    value.forEach((mesg, to) ->{
                                        //todo mostrare messaggi
                                    });
                                } else {
                                    value.forEach((mesg, to) ->{
                                        //todo altri messaggi
                                    });
                                }
                            }
                    ));
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }
        }else if (message.getEvent().equals(Event.SEND_MESSAGE)) {
            if (message.getChat().getActive().contains(username)) {
                message.getChat().getLast().forEach((key, value) -> {
                    if (!key.equals(username)) {
                        value.forEach((mesg, to) -> {
                            if (to != null && to.equals(username)) {
                                //todo altri messaggi
                            } else if (to == null) {
                                //todo altri messaggi
                            }
                        });
                    }
                });
            }
        }else if(message.getEvent().equals(Event.EXIT_CHAT) && message.getUserName().equals(username)){
            if (!message.getChat().getActive().contains(username)){
                synchronized (this){
                    this.notifyAll();
                }
                // todo gestire chat
            }
        }else if (message.getEvent().equals(Event.LOGIN_TRUE)) {
            Platform.runLater(() -> {
                try {
                    HelloApplication.setScene("game");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                initGameScene(message.getModel());
                GuiController.startGame();
            });
        }else if( message.getModel()!= null && !message.getModel().getCurrentPlayer().getUsername().equals(username)){
            Platform.runLater(() -> {
                GuiController.setMyTurn(false , message.getModel().getCurrentPlayer().getUsername());
            });
        }
        if (message.getModel() == null || message.getModel().getCurrentPlayer().getUsername().equals(username)) {
            myTurn = true;
            if (message.getEvent().equals(Event.PLAYER_DRAW_NEGATIVE)) {
                Platform.runLater(() -> {
                    GuiController.badDraw();
                });
            } else if (message.getEvent().equals(Event.PLAYER_DRAW_POSITIVE)) {
                Platform.runLater(() -> {
                    GuiController.goodDraw();
                });
            } else if (message.getEvent().equals(Event.PLAYER_INSERT_NEGATIVE)) {
                Platform.runLater(() -> {
                    GuiController.insertNegative(message.getModel());
                });
            } else if (message.getEvent().equals(Event.PLAYER_INSERT_POSITIVE)) {
               Platform.runLater(() -> {
                   GuiController.insertPositive(message.getModel());
               });
            } else if (message.getEvent().equals(Event.PLAYER_FINISH)) {
                //todo gestire
            } else if (message.getEvent().equals(Event.NEW_TURN)) {
                Platform.runLater(() -> {
                    GuiController.setMyTurn(true, null);
                    GuiController.letDraw();
                });
            } else if (message.getEvent().equals(Event.FINISH_MATCH)) {
                //todo gestire finestra vincitore fine partita
            } else if (message.getEvent().equals(Event.LOGIN)) {
                Platform.runLater(() -> {
                    try {
                        HelloApplication.setScene("playerInfo");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else if (message.getEvent().equals(Event.WAIT_START_OF_MATCH)) {
                username = GuiController.getUsername();
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
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
    }

    private void initGameScene(GameView model) {
        fillBoard(model);
        GuiController.showShelves(model,username);
        GuiController.setChair(model,username);
        GuiController.setCommongoals(model);
        GuiController.setPersonalGoal(model);
    }



    private void fillBoard(GameView o) {
       for(int i=0;i<o.getHeightBoard();i++){
           for(int j=0;j<o.getLenghtBoard();j++){
               BoxView box = o.getBoard()[i][j];
               if (box.getValid()) {
                   ItemTiles el = box.getItemContained();
                   if (el != null) {
                       GuiController.setGridImage(GuiController.pickTileImage(el.getType(),el.getId()) , i, j);
                   }
               }
           }
       }
    }



}




