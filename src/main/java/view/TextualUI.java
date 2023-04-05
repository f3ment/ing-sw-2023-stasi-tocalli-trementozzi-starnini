package view;

import model.Game;
import model.board.Board;
import utils.*;

import java.util.ArrayList;


public class TextualUI extends Observable<Event> implements Observer<Game,Event>, Runnable {

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.println("--- NEW TURN ---");
            /* Player chooses */
            Event c = Event.PLAYER_DRAW;
            ArrayList<Integer[]> drawen= new ArrayList<>();
            Integer[] a= new Integer[2];
            a[0]=3;
            a[1]=2;
            drawen.add(a);
            Integer[] b= new Integer[2];
            b[0]=6;
            b[1]=7;
            drawen.add(b);
            setChanged();
            notifyObservers(c, 0,drawen);
        }
    }

    @Override
    public void update(Game model, Event arg,int columnNumber , ArrayList coords ,int[] insertionOrder) {

        }
    }

    private void playerDraw(Game model,Event arg) {
        Event event = arg;
        if (event == null) {
            return;
        }
        /* Show PLAYER's draw */
        Board boardgame=model.getBoard();
        for(int i=0;i<boardgame.getMaxHeight();i++){
            for(int j=0;j< boardgame.getMaxLength();j++){
                try{
                    System.out.print("  "+boardgame.getBox(i,j).getItemContained().getType().toString().charAt(0)+"  ");
                }catch (Exception e){
                    System.out.print(" -- ");
                }
            }
            System.out.print("\n");
        }
    }

}
