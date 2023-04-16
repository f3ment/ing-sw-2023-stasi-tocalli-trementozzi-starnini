package view;

import model.Box;
import model.Game;
import model.GameView;
import model.ItemTiles;
import model.board.Board;
import utils.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;


public class TextualUI extends Observable<Event> implements Observer<GameView,Event>, Runnable {

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        setChanged();
        notifyObservers(Event.NEW_TURN, null, null);

    }

    @Override
    public void update(GameView o, Enum arg, Integer columnNumber, ArrayList coords) {
        if(arg.equals(Event.PLAYER_DRAW_NEGATIVE)){
            System.out.println("Le carte selezionate sono sbagliate! Riprova : ");
            playerDraw(o);
        } else if (arg.equals(Event.PLAYER_DRAW_POSITIVE)){
            System.out.println("Cards picked correctly!");
            //show picked cards
            showHand(o);
            playerInsert(o);
        } else if (arg.equals(Event.PLAYER_INSERT_NEGATIVE)) {
            System.out.println("The selected column is not valid! Retry. ");
            showHand(o);
            playerInsert(o);
        } else if (arg.equals(Event.PLAYER_INSERT_POSITIVE)) {
            System.out.println("Cards inserted correctly!");
            showBookshelf(o);
            setChanged();
            notifyObservers(Event.PLAYER_FINISH, null, null);
        }else if(arg.equals(Event.PLAYER_FINISH)){
            run();
        }else if (arg.equals(Event.NEW_TURN)){
            start(o);
        }else if(arg.equals(Event.FINISCH_MATCH)){
            System.out.println("END GAME");
            System.out.println("THE WINNER IS "+ o.getWinner());
        }
    }

    private void start(GameView o) {
        if(o.getFirstPlayer()==o.getCurrentPlayer().getUsername()&&o.getEndGame()==true){
            setChanged();
            notifyObservers(Event.FINISCH_MATCH, null,null);
        }
        System.out.println( o.getCurrentPlayer().getUsername() + ", it's your turn!");
        showBoard(o);
        playerDraw(o);
    }

    void showBoard(GameView o){
        System.out.print(" ");
        int a;
        for(int j =0 ; j < o.getHeightBoard(); j++){
            a=j+1;
            System.out.print(" " +a);
        }
        System.out.print("\n");
        for(int i =0; i< o.getHeightBoard(); i++){
            a=i+1;
            System.out.print(a);
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

    void showBookshelf(GameView o){
        System.out.println("This is your bookshelf : ");
        for(int j =0 ; j < o.getLenghtBookshelf(); j++){
            System.out.print(" " +(j+1));
        }
        System.out.print("\n");
        for(int i =0; i< o.getHeightBookshelf(); i++){
            for(int j = 0; j<o.getLenghtBookshelf(); j++){
                ItemTiles el = o.getCurrentBookshelf()[i][j];
                if (el != null) {
                    System.out.print(" " + el.getType().toString().charAt(0));
                } else {
                    System.out.print(" -");
                }
            }
            System.out.print("\n");
        }
    }

    private void playerInsert(GameView o){
        //scan input
        ArrayList<Integer> order = new ArrayList<>();
        int column;
        boolean flag;
        //controllo input ordine
        do{
            flag = true;
            System.out.println("Insert in which order do you want to insert cards : ");

            for (int i = 0; i < o.getPickedCards().size(); i++) {
                System.out.print("Insert the index of the " + (i + 1) + " card to insert : ");
                int index = reading();
                while(index<1 || index > o.getPickedCards().size()){
                    System.out.println("Invalid Index , insert again!");
                    index = reading();
                }
                order.add(index-1);
            }



            //check on order input
            if (order.stream().sorted().distinct().count() != o.getPickedCards().size()) {
                System.out.println("ERROR! Found many occurrencies of the same index!");
                flag = false;
            } else {
                flag = !order.stream().allMatch(e -> e < 0 || e >= o.getPickedCards().size());
                if (!flag) {
                    System.out.println("ERROR! Indexes inserted are not correct!");
                }
            }
            if(!flag){
                System.out.println("Retry!");
            }
        }while(!flag);

        System.out.println("The choosen order is : ");
        order.forEach(e->System.out.println( e+1 + " " + o.getPickedCards().get(e).getType().toString().charAt(0) + " "));
        showBookshelf(o);
        System.out.println("Now insert in which column would you like to insert your cards. ");
        System.out.println("REMEMBER : it must be between 1 and " +o.getLenghtBookshelf() + ".");
        column = reading()-1;
        //choose column
        setChanged();
        notifyObservers(Event.PLAYER_INSERT_POSITIVE, column, order);

    }

    private void playerDraw(GameView o) {
        ArrayList<ArrayList<Integer>> drawen = new ArrayList<ArrayList<Integer>>();
        int nCards, x, y;
        boolean flag;
        ArrayList<Integer> coords;
        System.out.println("Insert how many cards do you want to draw from board : ");
        nCards = reading();
        while(nCards <= 0 || nCards >3){
            System.out.println("The number of cards must be between 1 and 3! Retry.");
            System.out.println("Insert how many cards do you want to draw from board : ");
            nCards = reading();
        }
        for(int i = 0; i< nCards; i++){
            flag = true;
            coords = new ArrayList<Integer>();
            int z=i+1;
            System.out.println("Insert the coordinates of the " + z + " card : ");
            System.out.print("x : ");
            x = reading();
            x--;
            while(x>= o.getHeightBoard() || x<0){
                System.out.println("Not valid coordinate! Retry.");
                System.out.print("x : ");
                x = reading();
                x--;
            }
            System.out.print("y : ");
            y = reading();
            y--;
            while(y>= o.getLenghtBoard() || y<0){
                System.out.println("Not valid coordinate! Retry.");
                System.out.print("y : ");
                y = reading();
                y--;
            }

            if(!o.getBoard()[x][y].getValid()||o.getBoard()[x][y].getItemContained()==null){
                System.out.println("The chosen box is not playable! Retry.");
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
                    i--;
                    break;
                }
            }
            if(flag){
                drawen.add(coords);
            }
        }
        setChanged();
        notifyObservers(Event.PLAYER_DRAW_POSITIVE, null,drawen);
    }


    void showHand(GameView o){
        for(int i = 0; i<o.getPickedCards().size(); i++){
            System.out.print(i + 1 +" ");
        }
        System.out.print("\n");
        for(ItemTiles i : o.getPickedCards()){
            System.out.print(i.getType().toString().charAt(0) + " ");
        }
        System.out.print("\n");
    }

    public int reading(){
        int userInput;
        while(true) {
            try {
                Scanner input = new Scanner(System.in);
                userInput = input.nextInt();
                break;
            }
            catch(InputMismatchException | NumberFormatException ex ) {
                System.out.println("Invalid Number, Please try again");
            }
        }
        return userInput;
    }
}
