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
        if(checkDraw(coords)){
            for (ArrayList<Integer> i : coords){
                game.getCurrentPosition().getPlayer().drawFromBoard(game.getBoard(), i.get(0), i.get(1));
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
    private boolean checkDraw(ArrayList<ArrayList<Integer>> coords){
        /*
        * check if there is a column in the shelf with enough space to insert all the chosen tiles
        * if not return false
        */
        /*
         *check if chosen tiles are on the same row
         */
        boolean notValid = false;
        ArrayList<Integer> x = new ArrayList<Integer>();
        ArrayList<Integer> y = new ArrayList<Integer>();
        ArrayList<ItemTiles> validCards = new ArrayList<ItemTiles>();
        for(int i=0;i<coords.size();i++){
            x.add(coords.get(i).get(0));
            y.add(coords.get(i).get(1));
            if(coords.get(0).get(0) != coords.get(i).get(0)) {
                notValid = true;
                break;
            }
        }
        if(notValid){
            /*
             *check if chosen tiles are on the same column
             */
            for(int i=1;i<coords.size();i++){
                if(coords.get(0).get(1) != coords.get(i).get(1))
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
        for(ArrayList<Integer> elem : coords){
            if(!game.getBoard().getBox(elem.get(0),elem.get(1)).getValid()) {
                return false;
            }else{
                try{
                    if(game.getBoard().getBox(elem.get(0)+1,elem.get(1)).getItemContained()!=null &&
                            game.getBoard().getBox(elem.get(0)-1,elem.get(1)).getItemContained()!=null &&
                            game.getBoard().getBox(elem.get(0),elem.get(1)+1).getItemContained()!=null &&
                            game.getBoard().getBox(elem.get(0),elem.get(1)-1).getItemContained()!=null){
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
    private boolean checkInsert(int columnNumber){
        try {
            game.getCurrentPosition().getBookshelf().setChoosenColumn(columnNumber);
            if((int)(game.getCurrentPosition().getBookshelf().getColumnsSize().get(columnNumber)) > (6-game.getCurrentPosition().getPlayer().getPickedCards().size())){
                return false;
            }
            return true;
        } catch (Exception e) {
            //column not correct
            return false;
        }
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
            if(game.getEndGame()==true&& game.getCurrentPosition().getPlayer().getUsername()==game.getFirstPlayer().getUsername()){
                game.setWinner();
                game.setChangedAndNotifyObservers(Event.FINISCH_MATCH);

            }
            if(checkBoardEmpty()){
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
        } else if (arg.equals(Event.LOGIN_TRUE)) {
            game.setChangedAndNotifyObservers(Event.LOGIN_TRUE);
        }
    }

    private boolean checkBoardEmpty() {
        boolean result=true;
        for(int i=0;i<game.getBoard().getMaxHeight()&&result;i++){
            for(int j=0;j<game.getBoard().getMaxLength()&&result;j++){
                if(game.getBoard().getBox(i,j).getValid()&&game.getBoard().getBox(i,j).getItemContained()!=null){
                    if(i>0&&game.getBoard().getBox(i-1,j).getValid()&&game.getBoard().getBox(i-1,j).getItemContained()!=null){
                        result=false;
                    }
                    if(i<game.getBoard().getMaxHeight()-1 &&game.getBoard().getBox(i+1,j).getValid()&&game.getBoard().getBox(i+1,j).getItemContained()!=null){
                        result=false;
                    }
                    if(j>0&&game.getBoard().getBox(i,j+1).getValid()&&game.getBoard().getBox(i,j+1).getItemContained()!=null){
                        result=false;
                    }
                    if(j<game.getBoard().getMaxLength()-1&&game.getBoard().getBox(i-1,j).getValid()&&game.getBoard().getBox(i-1,j).getItemContained()!=null){
                        result=false;
                    }

                }
            }
        }
        return result;
    }


}
