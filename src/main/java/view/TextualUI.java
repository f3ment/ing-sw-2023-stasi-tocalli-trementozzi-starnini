package view;

import model.Box;
import model.Game;
import model.GameView;
import model.ItemTiles;
import model.board.Board;
import utils.*;

import java.util.ArrayList;
import java.util.Scanner;


public class TextualUI extends Observable<Event> implements Observer<GameView,Event>, Runnable {

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        setChanged();
        notifyObservers(Event.NEW_MATCH, null, null);
    }

    @Override
    public void update(GameView o, Enum arg, Integer columnNumber, ArrayList coords) {
        if(arg.equals(Event.PLAYER_DRAW_NEGATIVE)){
            System.out.println("Le carte selezionate sono sbagliate! Riprova : ");
            playerDraw(o);
        } else if (arg.equals(Event.PLAYER_DRAW_POSITIVE)){
            System.out.println("Carte selezionate correttamente!");
            //show picked cards
            for(ItemTiles i : o.getPickedCards()){
                System.out.print(i.toString().charAt(0)+ " ");
            }
            playerInsert();
        } else if (arg.equals(Event.PLAYER_INSERT_NEGATIVE)) {
            System.out.println("La colonna scelta non è valida! Riprova : ");
            for(ItemTiles i : o.getPickedCards()){
                System.out.print(i.toString().charAt(0)+ " ");
            }
            playerInsert();
        } else if (arg.equals(Event.PLAYER_INSERT_POSITIVE)) {
            notifyObservers(Event.PLAYER_FINISH, null, null);
        }else if(arg.equals(Event.PLAYER_FINISH)){
                run();
        }else if (arg.equals(Event.NEW_MATCH)){
            System.out.println("Starting new match!");
            showBoard(o);
        }
    }

    private void start(GameView o) {
        System.out.println("Starting new match!");
        showBoard(o);
        playerDraw(o);
    }

    void showBoard(GameView o){
        System.out.print(" ");
        for(int j =0 ; j < o.getHeightBoard(); j++){
            System.out.println(" " +j);
        }
        for(int i =0; i< o.getHeightBoard(); i++){
            System.out.print(i);
            for(int j = 0; j<o.getLenghtBoard(); j++){
                Box box = o.getBoard()[i][j];
                if(box.getValid()){
                    ItemTiles el = box.getItemContained();
                    if (el != null) {
                        System.out.print(" " + el.getType().toString().charAt(0));
                    } else {
                        System.out.print(" -");
                    }
                }else{
                    System.out.print(" x");
                }
            }
            System.out.print("\n");
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

    private void playerDraw(GameView o) {
        ArrayList<ArrayList<Integer>> drawen = new ArrayList<ArrayList<Integer>>();
        int nCards, x, y;
        boolean flag;
        ArrayList<Integer> coords;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert how many cards do you want to draw from board : ");
        nCards = scanner.nextInt();
        while(nCards <= 0 || nCards >3){
            System.out.println("The number of cards must be between 1 and 3! Retry.");
            System.out.println("Insert how many cards do you want to draw from board : ");
            nCards = scanner.nextInt();
        }
        for(int i = 0; i< nCards; i++){
            flag = true;
            coords = new ArrayList<Integer>();
            System.out.println("Insert the coordinates of the " + i + " card : ");
            System.out.print("x : ");
            x = scanner.nextInt();
            while(x>= o.getHeightBoard() || x<0){
                System.out.println("Not valid coordinate! Retry.");
                System.out.print("x : ");
                x = scanner.nextInt();
            }
            System.out.print("y : ");
            y = scanner.nextInt();
            while(y>= o.getLenghtBoard() || y<0){
                System.out.println("Not valid coordinate! Retry.");
                System.out.print("y : ");
                y = scanner.nextInt();
            }

            if(!o.getBoard()[x][y].getValid()){
                System.out.println("The choosen box is not playable! Retry.");
                i--;
                flag = false;
            }
            /*
            while(!o.getBoard()[x][y].getValid()){
                System.out.println("The choosen box is not playable! Retry.");
                System.out.println("Insert the coordinates of the " + i + " card : ");
                System.out.print("x : ");
                x = scanner.nextInt();
                while(x>= o.getHeightBoard() || x<0){
                    System.out.println("Not valid coordinate! Retry.");
                    System.out.print("x : ");
                    x = scanner.nextInt();
                }
                System.out.print("y : ");
                y = scanner.nextInt();
                while(y>= o.getLenghtBoard() || y<0){
                    System.out.println("Not valid coordinate! Retry.");
                    System.out.print("y : ");
                    y = scanner.nextInt();
                }
            }*/

            coords.add(x);
            coords.add(y);
            for(ArrayList<Integer> el : drawen ){
                if(el.get(0) == coords.get(0) && el.get(1) == coords.get(1)){
                    System.out.println("ERROR! The choosen card has already been selected! Retry.");
                    flag = false ;
                    break;
                }
            }
            if(flag){
                drawen.add(coords);
            }
        }
        scanner.close();
        setChanged();
        notifyObservers(Event.PLAYER_DRAW_POSITIVE, null,drawen);
    }

}
