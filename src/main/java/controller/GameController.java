package controller;
import distributed.Client;
import model.*;
import utils.Event;
import view.Color;

import java.util.ArrayList;

/**
 * Class GameController
 * This class is the controller of a lobby game
 * It serves as an intermediary between the model and the view, and it is used to update the model and the view
 * when a player performs an action or when the model changes its state
 */
public class GameController {
    private final Game game;
    private final Lobby lobby;

    /**
     * Constructor of the class GameController
     * @param game the game model
     * @param lobby game's lobby
     */
    public GameController(Game game, Lobby lobby){
        this.lobby = lobby;
        this.game = game;
        //this.view = view;
    }

    /**
     * Method that checks if the chosen tiles are valid and if they are, it draws them from the board
     * @param coords coordinates of the tiles to draw
     * @return true if the draw is successful, false otherwise
     */
    private boolean draw(ArrayList<ArrayList<Integer>> coords){
        if(game.checkDraw(coords)){
            for (ArrayList<Integer> i : coords){
                game.getCurrentPosition().getPlayer().drawFromBoard(game.getBoard(), i.get(0), i.get(1));
            }
            return true;
        }else{
            return false;
        }
    }


    /**
     * Method that checks if the chosen tiles can be inserted in the selected column
     * and if they are, it inserts them in the player's bookshelf
     *
     * @param columnNumber the column where the player wants to insert the tiles
     * @param insertionOrder the order in which the player wants to insert the tiles
     * @return true if the insertion is successful, false otherwise
     */
    private boolean insert(int columnNumber, ArrayList<Integer> insertionOrder ){
        if(game.checkInsert(columnNumber)){
            int size= game.getCurrentPosition().getPlayer().getPickedCards().size();
            for(int i=0;i<size;i++){
                try {

                    for(int j=i+1;j<size;j++){
                        if(insertionOrder.get(j)>insertionOrder.get(i)){
                            insertionOrder.set(j,insertionOrder.get(j)-1);
                        }
                    }

                    game.getCurrentPosition().getPlayer().insertInBookshelf(columnNumber,insertionOrder.get(i));

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    //column not right
                    return false;
                }

            }
            game.getCurrentPosition().getPlayer().clearHand();
            return true;
        }else{
            return false;
        }
    }



    /**
     * Method that changes the current position of the game to the next one
     * in order to let the next player play
     */
    private void changeCurrentPosition(){
        game.changeCurrentPosition();
    }

    /**
     * Method that updates the model when a player performs an action by changing model state
     * and notifying the observers of the model if the action is valid
     * @param o client that has to be updated
     * @param message message that contains the event to be performed
     */
    public void update(Client o,Message message) {
        if(o==null){
            return;
        }
        if(message.getEvent().equals(Event.CONNECTION_PROBLEM)) {
            if(game.checkBoardEmpty()){
                game.fillBoard();
            }
            game.getCurrentPosition().getPlayer().clearHand();
            changeCurrentPosition();
            game.setChangedAndNotifyObservers(Event.PLAYER_FINISH);
        }else if (message.getEvent().equals(Event.PLAYER_DRAW_POSITIVE)) {
            if(draw(message.getCoords())){
                game.setChangedAndNotifyObservers(Event.PLAYER_DRAW_POSITIVE);
            }else{
                game.setChangedAndNotifyObservers(Event.PLAYER_DRAW_NEGATIVE);
            }

        } else if (message.getEvent().equals(Event.PLAYER_INSERT_POSITIVE)) {
            if(insert(message.getColumnNumber(), message.getCoords())){
                game.setChangedAndNotifyObservers(Event.PLAYER_INSERT_POSITIVE);
            }else{
                game.setChangedAndNotifyObservers(Event.PLAYER_INSERT_NEGATIVE);
            }
        } else if (message.getEvent().equals(Event.PLAYER_FINISH)) {
            //Check if re-fill board

            game.checkFinalControl();
            if(game.getEndGame() && game.getCurrentPosition().getPlayer().getUsername().equals(game.getLastPlayer())&&game.getFinalFlow()!=3){//&&game.getFirstFinisher().equals(game.getCurrentPosition().getPlayer().getUsername())
                lobby.getChatController().update(o, new Message(Event.SEND_MESSAGE, new ChatMessage(Color.RED + "The match is ending!" + Color.RESET, Color.RED + "SERVER" + Color.RESET, null) ));
                lobby.getChatController().update(o, new Message(Event.EXIT_CHAT, ""));
                changeCurrentPosition();
                System.out.println("il booleano finale vale "+ game.getFinalFlow());
                game.setWinner();
                game.setChangedAndNotifyObservers(Event.FINISH_MATCH);
            }else {
                if (game.checkBoardEmpty()) {
                    game.fillBoard();
                }
                if (!game.getEndGame() || (!game.getCurrentPosition().getPlayer().getUsername().equals(game.getFirstPlayer()) && game.getEndGame())||(game.getCurrentPosition().getPlayer().getUsername().equals(game.getFirstPlayer()) && game.getEndGame()&&game.getFirstFinisher().equals(game.getFirstPlayer()))) {
                    changeCurrentPosition();
                    game.setChangedAndNotifyObservers(Event.PLAYER_FINISH);
                }
            }
        }else if(message.getEvent().equals(Event.NEW_TURN_RECONNECTED)){
            game.setChangedAndNotifyObservers(Event.NEW_TURN_RECONNECTED);
        }else if(message.getEvent().equals(Event.NEW_TURN)){
            game.setChangedAndNotifyObservers(Event.NEW_TURN);
        }else if(message.getEvent().equals(Event.FINISH_MATCH)){
            game.setWinner();
            if(game.getFinalFlow()!=1) {
                game.setRegularFlow();
            }
            if(game.getFinalFlow()==1||game.getFinalFlow()==4){
                lobby.getChatController().update(o, new Message(Event.SEND_MESSAGE, new ChatMessage(Color.RED + "The match is ending!" + Color.RESET, Color.RED + "SERVER" + Color.RESET, null) ));
                lobby.getChatController().update(o, new Message(Event.EXIT_CHAT, ""));
            }
            System.out.println(game.getFinalFlow());
            game.setChangedAndNotifyObservers(Event.FINISH_MATCH);
        } else if (message.getEvent().equals(Event.LOGIN_TRUE)) {
            game.setChangedAndNotifyObservers(Event.LOGIN_TRUE);
        }else if (message.getEvent().equals(Event.FORCED_END_MATCH)) {
            lobby.getChatController().update(o, new Message(Event.SEND_MESSAGE, new ChatMessage(Color.RED + "The match is ending!" + Color.RESET, Color.RED + "SERVER" + Color.RESET, null) ));
            lobby.getChatController().update(o, new Message(Event.EXIT_CHAT, ""));
            game.setForcedWinner(message.getUserName());
            game.setCurrentPlayer(message.getUserName());
            game.setFinalForcedFlow();
            //o.update(new Message(Event.FORCED_END_MATCH));
            game.setChangedAndNotifyObservers(Event.FINISH_MATCH);
        }
    }



}
