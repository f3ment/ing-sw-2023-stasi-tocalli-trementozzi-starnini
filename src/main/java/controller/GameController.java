package controller;
import model.*;
import utils.Event;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameController implements Observer {
    private final Game game;

    //to implement
    //private final TextualUI view;;
    public GameController(Game game){
        this.game = game;
    }

    /*
    *method that checks the board's coordinates chosen by the
    *plyer from where to pick the tiles: coords should contain
    * one , two or three pairs of coordinates based on the player choice
    * [[int x1,int y1],[int x2,int y2]]
    */
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

    private boolean checkDraw(ArrayList<int[]> coords){
        boolean notValid = false;
        ArrayList<Integer> x = new ArrayList<Integer>();
        ArrayList<Integer> y = new ArrayList<Integer>();
        /*
        *check if chosen tiles are on the same row
        */
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
    private boolean insert(int columnNumber){
        return false;
    }

    private void changeCurrentPosition(){}

//todo gestione falso
    @Override
    public void update(Observable o, Enum arg, int columnNumber, ArrayList coords) {
        if (arg.equals(Event.PLAYER_DRAW)) {
            draw(coords);
        } else if (arg.equals(Event.PLAYER_INSERT)) {
            insert(columnNumber);
        } else if (arg.equals(Event.PLAYER_FINISH)) {
            changeCurrentPosition();
        }
    }

}
