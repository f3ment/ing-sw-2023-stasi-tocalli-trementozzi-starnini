package controller;
import model.*;
import model.board.Board;
import utils.Event;
import utils.Observable;
import utils.Observer;
import view.TextualUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameController implements Observer<TextualUI,Event> {
    private final Game game;

    //private final TextualUI view;;
    public GameController(Game game){
        this.game = game;
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
            for(int i=0;i<game.getCurrentPosition().getPlayer().getPickedCards().size();i++){
                System.out.println(insertionOrder.get(i));
            }
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

    @Override
    public void update(TextualUI o, Enum arg, Integer columnNumber, ArrayList coords ) {
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
            if(game.getEndGame()==true&& game.getCurrentPosition().getPlayer().getUsername()==game.getFirstPlayer().getUsername()){
                game.setWinner();
                game.setChangedAndNotifyObservers(Event.FINISCH_MATCH);

            }
            if(game.checkBoardEmpty()){
                game.fillBoard();
            }
            if(game.getEndGame()==false || (game.getCurrentPosition().getPlayer().getUsername()!=game.getFirstPlayer().getUsername()&& game.getEndGame()==true)) {
                changeCurrentPosition();
                game.setChangedAndNotifyObservers(Event.PLAYER_FINISH);
            }
        }else if(arg.equals(Event.NEW_TURN)){

            game.setChangedAndNotifyObservers(Event.NEW_TURN);
        }else if(arg.equals(Event.FINISCH_MATCH)){
            game.setWinner();
            game.setChangedAndNotifyObservers(Event.FINISCH_MATCH);
        }
    }



}
