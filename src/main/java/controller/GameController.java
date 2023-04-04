package controller;
import model.*;
import utils.Event;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;

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
    private boolean checkDraw(ArrayList<int[]> coords){
        boolean notValid = false;
        ArrayList<ItemTiles> validCards = new ArrayList<ItemTiles>();
        for(int i=1;i<coords.size();i++){
            if(coords.get(0)[0] != coords.get(i)[0])
                notValid = true;
                break;
        }
        if(notValid){
            for(int i=1;i<coords.size();i++){
                if(coords.get(0)[1] != coords.get(i)[1])
                    return false;
            }
        }
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
                    }else {
                        validCards.add(game.getBoard().getBox(elem[0],elem[1]).getItemContained());
                    }
                }catch (IndexOutOfBoundsException e){
                    return false;
                }

            }
        }

        return false;
    }
    private boolean insert(int columnNumber){
        return false;
    }

    private void changeCurrentPosition(){}


    @Override
    public void update(Observable o, Enum arg, int columnNumber, ArrayList coords) {
        if (arg.equals(Event.PLAYER_DRAW)) {
            checkDraw(coords);
        } else if (arg.equals(Event.PLAYER_INSERT)) {
            insert(columnNumber);
        } else if (arg.equals(Event.PLAYER_FINISH)) {
            changeCurrentPosition();
        }
    }

}
