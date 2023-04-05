package controller;
import model.*;
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

    //TODO implement textualUI
    //private final TextualUI view;;
    public GameController(Game game){
        this.game = game;
    }




    /*
    * method to draw tiles from the model board
    * */
    private boolean draw(ArrayList<int[]> coords){
        if(checkDraw(coords)){
            for (int[] i : coords){
                game.getCurrentPosition().getPlayer().drawFromBoard(game.getBoard(), i[0], i[1]);
            }
            return true;
        }else{
            return false;
        }
    }

    /*
     *method that checks the board's coordinates chosen by the
     *plyer from where to pick the tiles: coords should contain
     * one , two or three pairs of coordinates based on the player choice
     * [[int x1,int y1],[int x2,int y2],[int x3 ,int y3]]
     */
    private boolean checkDraw(ArrayList<int[]> coords){
        /*
        * check if there is a column in the shelf with enough space to insert all the chosen tiles
        * if not return false
        */
        if(coords.size() > game.getCurrentPosition().getBookshelf().getMaxDrowable())
            return false;
        /*
         *check if chosen tiles are on the same row
         */
        boolean notValid = false;
        ArrayList<Integer> x = new ArrayList<Integer>();
        ArrayList<Integer> y = new ArrayList<Integer>();
        ArrayList<ItemTiles> validCards = new ArrayList<ItemTiles>();
        for(int i=0;i<coords.size();i++){
            x.add(coords.get(i)[0]);
            y.add(coords.get(i)[1]);
            if(coords.get(0)[0] != coords.get(i)[0])
                notValid = true;
                break;
        }
        if(notValid){
            /*
             *check if chosen tiles are on the same column
             */
            for(int i=1;i<coords.size();i++){
                if(coords.get(0)[1] != coords.get(i)[1])
                    return false;
            }
        }
        /*
         *check if chosen tiles are adjacent
         */
        x.stream().sorted().collect(Collectors.toList());
        y.stream().sorted().collect(Collectors.toList());
        notValid=false;
        for(int i=0;i<x.size()-1;i++){
            if(x.get(i+1)-x.get(i)!=1)
                notValid=true;
        }
        if(notValid){
            for(int i=0;i<y.size()-1;i++){
                if(y.get(i+1)-y.get(i)!=1)
                    return false;
            }
        }
        /*
         *check if chosen tiles have at least one free side
         */
        for(int[] elem : coords){
            if(!game.getBoard().getBox(elem[0],elem[1]).getValid()) {
                return false;
            }else{
                try{
                    if(game.getBoard().getBox(elem[0]+1,elem[1]).getItemContained()!=null &&
                            game.getBoard().getBox(elem[0]-1,elem[1]).getItemContained()!=null &&
                            game.getBoard().getBox(elem[0],elem[1]+1).getItemContained()!=null &&
                            game.getBoard().getBox(elem[0],elem[1]-1).getItemContained()!=null){
                        return false;
                    }
                }catch (IndexOutOfBoundsException e){
                    return false;
                }

            }
        }
        return true;
    }




    private boolean insert(int columnNumber, ArrayList<Integer> insertionOrder ){
        if(checkInsert(columnNumber)){
            for(int i=0;i<game.getCurrentPosition().getPlayer().getPickedCards().size();i++){
                try {
                    game.getCurrentPosition().getPlayer().insertInBookshelf(columnNumber,insertionOrder.get(i));
                } catch (Exception e) {
                    //column not right
                    return false;
                }

            }
            return true;
        }else{
            return false;
        }
    }

    /*
     * method that checks if the chosen column has enough space to insert all the tiles
     */
    private boolean checkInsert(int columnNumber){
        if((int)(game.getCurrentPosition().getBookshelf().getColumnsSize().get(columnNumber)) < (6-game.getCurrentPosition().getPlayer().getPickedCards().size())){
            return false;
        }
        return true;
    }

    private void changeCurrentPosition(){
        //TODO Capire come gestire turni e ascoltare la view corretta
        if(game.getCurrentPosition().getBookshelf().isFull()){
            game.setEndGame(true);
            game.getCurrentPosition().getPlayer().setScore(game.getCurrentPosition().getPlayer().getScore()+1);
        }
        game.validateAdjacent(game.getCurrentPosition());
        game.validateCommonGoal(game.getCurrentPosition());
        game.validatePersonalGoal(game.getCurrentPosition());
        game.setCurrentPosition();
    }

    //todo gestione input non validi
    @Override
    public void update(TextualUI o, Enum arg, int columnNumber, ArrayList coords ) {
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
            changeCurrentPosition();
        }
    }

}
