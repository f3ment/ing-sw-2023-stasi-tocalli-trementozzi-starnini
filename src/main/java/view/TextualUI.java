package view;

import model.Game;
import model.GameView;
import model.ItemTiles;
import model.board.Board;
import utils.*;

import java.util.ArrayList;


public class TextualUI extends Observable<Event> implements Observer<GameView,Event>, Runnable {

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        System.out.println("--- NEW TURN ---");
        /* Player chooses */
        playerDraw();
    }

    @Override
    public void update(GameView o, Enum arg, int columnNumber, ArrayList coords) {
        if(arg.equals(Event.PLAYER_DRAW_NEGATIVE)){
            System.out.println("Le carte selezionate sono sbagliate! Riprova : ");
            playerDraw();
        } else if (arg.equals(Event.PLAYER_DRAW_POSITIVE)){
            System.out.println("Carte selezionate correttamente!");
            //show picked cards
            for(ItemTiles i : o.getCurrentPosition().getPlayer().getPickedCards()){
                System.out.print(i.toString().charAt(0)+ " ");
            }
            playerInsert();
        } else if (arg.equals(Event.PLAYER_INSERT_NEGATIVE)) {
            System.out.println("La colonna scelta non è valida! Riprova : ");
            for(ItemTiles i : o.getCurrentPosition().getPlayer().getPickedCards()){
                System.out.print(i.toString().charAt(0)+ " ");
            }
            playerInsert();
        } else if (arg.equals(Event.PLAYER_INSERT_POSITIVE)) {
            notifyObservers(Event.PLAYER_FINISH, null, null);
        }else if(arg.equals(Event.PLAYER_FINISH)){
                run();
        }
    }

    private void playerInsert(){
        //scan input
        ArrayList<Integer> order = new ArrayList<>();
        order.add(2);
        order.add(1);
        order.add(0);
        //controllo input ordine
        //choose column
        int column = 0;
        notifyObservers(Event.PLAYER_INSERT_POSITIVE, column, order);

    }

    private void playerDraw() {
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
        notifyObservers(Event.PLAYER_DRAW_POSITIVE, null,drawen);
    }

}
