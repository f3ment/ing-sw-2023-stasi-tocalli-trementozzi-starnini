package view;

import model.ItemTiles;
import model.Message;
import model.views.BoxView;
import model.views.GameView;
import utils.Event;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;

public class GraphicalUI extends View implements Runnable {
    private ScenesController GuiController;

    private String username;

    private boolean myTurn = true;

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


    //todo implementare un eventHandler per ogni evento che arriva dal server (idem per testuale)

    @Override
    /**
     * this method will be called by the server when a new message
     * is received from the server and will update the gui accordingly
     * to the message received
     *
     * @param message the message received from the server
     */
    public void update(Message message) {
        if (message.getEvent().equals(Event.RECONNECTION)){
            Platform.runLater(() -> {
                username = GuiController.getUsername();

                try {
                    HelloApplication.setScene("game");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                initGameScene(message.getModel());
                GuiController.setMyTurn(false, "You have been reconnected succesfully \n to the game. " + message.getModel().getCurrentPlayer().getUsername());

            });
        }else if (message.getEvent().equals(Event.SEND_MESSAGE)) {
                Platform.runLater(() -> GuiController.updateChat(message.getChat().getLast()));
        }else if (message.getEvent().equals(Event.LOGIN_TRUE)) {
            Platform.runLater(() -> {
                try {
                    HelloApplication.setScene("game");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                initGameScene(message.getModel());
                GuiController.startGame(username);
            });
        }else if( message.getModel()!= null && !message.getModel().getCurrentPlayer().getUsername().equals(username)){
            Platform.runLater(() -> {
                if(message.getEvent().equals(Event.FINISH_MATCH)){
                    try {
                        HelloApplication.setScene("endGameScene");
                        GuiController.showWinner(message.getModel());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                GuiController.checkEndTokenAssigned(message.getModel());
                GuiController.setMyTurn(false , message.getModel().getCurrentPlayer().getUsername());
                GuiController.updateScores(message.getModel(),username);
                GuiController.updateStack(message.getModel());
                GuiController.showShelves(message.getModel(),username);
                fillBoard(message.getModel());
            });
        }
        if (message.getModel() == null || message.getModel().getCurrentPlayer().getUsername().equals(username)) {
            myTurn = true;
            if (message.getEvent().equals(Event.PLAYER_DRAW_NEGATIVE)) {
                Platform.runLater(() -> GuiController.badDraw());
            } else if (message.getEvent().equals(Event.PLAYER_DRAW_POSITIVE)) {
                Platform.runLater(() -> GuiController.goodDraw());
            } else if (message.getEvent().equals(Event.PLAYER_INSERT_NEGATIVE)) {
                Platform.runLater(() -> GuiController.insertNegative(message.getModel()));
            } else if (message.getEvent().equals(Event.PLAYER_INSERT_POSITIVE)) {
                Platform.runLater(() -> GuiController.insertPositive(message.getModel()));
            } else if (message.getEvent().equals(Event.PLAYER_FINISH)) {
                startNewTurn(message.getModel());
            } else if (message.getEvent().equals(Event.NEW_TURN)) {
                startNewTurn(message.getModel());
            } else if (message.getEvent().equals(Event.FINISH_MATCH)) {
                Platform.runLater(() -> {
                    try {
                        HelloApplication.setScene("endGameScene");
                        GuiController.showWinner(message.getModel());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
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
                });
            }
        }

    }

    private void startNewTurn(GameView model) {
        Platform.runLater(() -> {
            fillBoard(model);
            GuiController.checkEndTokenAssigned(model);
            GuiController.setMyTurn(true, null);
            GuiController.letDraw(model.getMaxDrawable());
            GuiController.updateStack(model);
            GuiController.updateScores(model,username);
            GuiController.showShelves(model,username);
        });
    }

    private void initGameScene(GameView model) {
        fillBoard(model);
        GuiController.showShelves(model,username);
        GuiController.setChair(model,username);
        GuiController.setCommongoals(model);
        GuiController.setPersonalGoal(model,username);
    }




//todo capire perchè java heap error quando c'è il refill
    private void fillBoard(GameView o) {
       GuiController.cleanBoard();
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




