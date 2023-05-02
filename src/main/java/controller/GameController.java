package controller;
import distributed.Client;
import model.*;
import utils.Event;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameController {
    private final Game game;

    //private final TextualUI view;;
    //private final Client view;
    public GameController(Game game){
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
        //TODO Capire come gestire turni e ascoltare la view corretta
        game.changeCurrentPosition();
    }

    //todo gestione input non validi
    public void update(Client o, Enum arg, Integer columnNumber, ArrayList coords, String userName) {
        if(o==null){
            return;
        }
        if (arg.equals(Event.PLAYER_DRAW_POSITIVE)) {
            if(draw(coords)){
                game.setChangedAndNotifyObservers(Event.PLAYER_DRAW_POSITIVE);
            }else{
                game.setChangedAndNotifyObservers(Event.PLAYER_DRAW_NEGATIVE);
            }

        } else if (arg.equals(Event.PLAYER_INSERT_POSITIVE)) {
            if(insert(columnNumber, coords)){
                game.setChangedAndNotifyObservers(Event.PLAYER_INSERT_POSITIVE);
            }else{
                game.setChangedAndNotifyObservers(Event.PLAYER_INSERT_NEGATIVE);
            }
        } else if (arg.equals(Event.PLAYER_FINISH)) {
            //Check if re-fill board

            if(game.getCurrentPosition().getBookshelf().isFull()){
                game.setEndGame(true);
            }
            if(game.getEndGame() && game.getCurrentPosition().getPlayer().getUsername()==game.getFirstPlayer()){
                game.setWinner();
                game.setChangedAndNotifyObservers(Event.FINISH_MATCH);

            }
            if(game.checkBoardEmpty()){
                game.fillBoard();
            }
            if(!game.getEndGame() || (game.getCurrentPosition().getPlayer().getUsername()!=game.getFirstPlayer()&& game.getEndGame())) {
                changeCurrentPosition();
                game.setChangedAndNotifyObservers(Event.PLAYER_FINISH);
            }
        }else if(arg.equals(Event.NEW_TURN)){

            game.setChangedAndNotifyObservers(Event.NEW_TURN);
        }else if(arg.equals(Event.FINISH_MATCH)){
            game.setWinner();
            game.setChangedAndNotifyObservers(Event.FINISH_MATCH);
        } else if (arg.equals(Event.LOGIN_TRUE)) {
            game.setChangedAndNotifyObservers(Event.LOGIN_TRUE);
        }
    }



}
