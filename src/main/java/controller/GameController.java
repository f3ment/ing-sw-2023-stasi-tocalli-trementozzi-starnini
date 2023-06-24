package controller;
import distributed.Client;
import model.*;
import utils.Event;
import view.Color;

import java.util.ArrayList;

public class GameController {
    private final Game game;
    private final Lobby lobby;
    //private final TextualUI view;;
    //private final Client view;
    public GameController(Game game, Lobby lobby){
        this.lobby = lobby;
        this.game = game;
        //this.view = view;
    }

    /*
    * method to draw tiles from the model board
    * */
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






    private boolean insert(int columnNumber, ArrayList<Integer> insertionOrder ){
        if(game.checkInsert(columnNumber)){
            //test
            /*for(int i=0;i<game.getCurrentPosition().getPlayer().getPickedCards().size();i++){
                System.out.println(insertionOrder.get(i));
            }*/
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

    /*
     * method that checks if the chosen column has enough space to insert all the tiles
     */

    private void changeCurrentPosition(){
        game.changeCurrentPosition();
    }

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

            if(game.getCurrentPosition().getBookshelf().isFull()&& !game.getEndGame()){
                game.setEndGame(true);
            }
            if(game.getEndGame() && game.getCurrentPosition().getPlayer().getUsername().equals(game.getLastPlayer())){
                lobby.getChatController().update(o, new Message(Event.SEND_MESSAGE, new ChatMessage(Color.RED + "The match is ending!" + Color.RESET, Color.RED + "SERVER" + Color.RESET, null) ));
                lobby.getChatController().update(o, new Message(Event.EXIT_CHAT, ""));
                game.updateLastScore();
                game.setWinner();
                game.setChangedAndNotifyObservers(Event.FINISH_MATCH);
            }else {
                if (game.checkBoardEmpty()) {
                    game.fillBoard();
                }
                if (!game.getEndGame() || (!game.getCurrentPosition().getPlayer().getUsername().equals(game.getFirstPlayer()) && game.getEndGame())) {
                    changeCurrentPosition();
                    game.setChangedAndNotifyObservers(Event.PLAYER_FINISH);
                }
            }
        }else if(message.getEvent().equals(Event.NEW_TURN_RECONNECTED)){
            game.setChangedAndNotifyObservers(Event.NEW_TURN_RECONNECTED);
        }else if(message.getEvent().equals(Event.NEW_TURN)){
            System.out.println("sono in new turn server");
            game.setChangedAndNotifyObservers(Event.NEW_TURN);
        }else if(message.getEvent().equals(Event.FINISH_MATCH)){
            game.setWinner();
            game.setChangedAndNotifyObservers(Event.FINISH_MATCH);
        } else if (message.getEvent().equals(Event.LOGIN_TRUE)) {
            game.setChangedAndNotifyObservers(Event.LOGIN_TRUE);
        }else if (message.getEvent().equals(Event.FORCED_END_MATCH)) {
            lobby.getChatController().update(o, new Message(Event.SEND_MESSAGE, new ChatMessage(Color.RED + "The match is ending!" + Color.RESET, Color.RED + "SERVER" + Color.RESET, null) ));
            lobby.getChatController().update(o, new Message(Event.EXIT_CHAT, ""));
            game.setForcedWinner(message.getUserName());
            game.setCurrentPlayer(message.getUserName());
            game.setFinalFlow();
            //o.update(new Message(Event.FORCED_END_MATCH));
            game.setChangedAndNotifyObservers(Event.FINISH_MATCH);
        }
    }



}
