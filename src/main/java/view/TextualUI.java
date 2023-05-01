package view;

import model.BoxView;
import model.GameView;
import model.ItemTiles;
import utils.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class TextualUI extends Observable<Event> implements Runnable {

    private String username;
    private boolean myTurn = true;

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        setChanged();
        notifyObservers(Event.GAME_INIT, null, null , null);

    }

    //update chiamato direttamente dall'oggetto che si occupa di gestire il client
    public void update(GameView o, Enum arg) {
        if(o==null || o.getCurrentPlayer().getUsername().equals(username)){
            myTurn = true;
            if (arg.equals(Event.PLAYER_DRAW_NEGATIVE)) {
                System.out.println("The cards you have selected are invalid, please select other cards : ");
                playerDraw(o);
            } else if (arg.equals(Event.PLAYER_DRAW_POSITIVE)) {
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
                notifyObservers(Event.PLAYER_FINISH, null, null, null);
            } else if (arg.equals(Event.PLAYER_FINISH)) {
                start(o);
            } else if (arg.equals(Event.NEW_TURN)) {
                start(o);
            } else if (arg.equals(Event.FINISH_MATCH)) {
                System.out.println("---END OF THE GAME---");
                System.out.println("THE WINNER IS ==>" + o.getWinner());
            } else if (arg.equals(Event.LOGIN)) {
                System.out.println("Choose your Nickname: ");
                Scanner input = new Scanner(System.in);
                this.username = input.nextLine();
                System.out.println("Hi " + username.toUpperCase() + "!, Choose the number of players: ");
                int nPlayers = readingInt();
                setChanged();
                notifyObservers(Event.LOGIN, nPlayers, null, username);
            } else if (arg.equals(Event.WAIT_START_OF_MATCH)) {
                System.out.println("Waiting for other player to join the lobby...");
            } else if (arg.equals(Event.LOGIN_TRUE)) {
                System.out.println("Game is starting...");
                setChanged();
                notifyObservers(Event.NEW_TURN, null, null, null);
            }
        }else{
            if(myTurn){
                myTurn = false;
                System.out.println(o.getCurrentPlayer().getUsername() + " is playing, wait for your turn!");
                showBoard(o);
                //showBookshelf(o); -> non si può usare perché mostriamo la currentBookshelf che non corrisponde a quella del giocatore in attesa
            }
        }
    }

    private void start(GameView o) {
        if(o.getFirstPlayer()==o.getCurrentPlayer().getUsername()&&o.getEndGame()==true){
            setChanged();
            notifyObservers(Event.FINISH_MATCH, null,null,null);
        }
        System.out.println( o.getCurrentPlayer().getUsername() + ", it's your turn!");
        showBoard(o);
        playerDraw(o);
    }

    void showBoard(GameView o){
        System.out.println("This is the current board : ");
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
                BoxView box = o.getBoard()[i][j];
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
                int index = readingInt();
                while(index<1 || index > o.getPickedCards().size()){
                    System.out.println("Invalid Index , insert again!");
                    index = readingInt();
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
        column = readingInt()-1;
        //choose column
        setChanged();
        notifyObservers(Event.PLAYER_INSERT_POSITIVE, column, order ,null);

    }

    private void playerDraw(GameView o) {
        ArrayList<ArrayList<Integer>> drawen = new ArrayList<ArrayList<Integer>>();
        int nCards, x, y;
        boolean flag;
        ArrayList<Integer> coords;
        System.out.println("Insert how many cards do you want to draw from board : ");
        nCards = readingInt();
        if(nCards > o.getMaxDrawable()){
            System.out.println("The number of cards must be minor");
            nCards=4;
        }
        while(nCards <= 0 || nCards >3){
            System.out.println("The number of cards must be between 1 and 3! Retry.");
            System.out.println("Insert how many cards do you want to draw from board : ");
            nCards = readingInt();
            if(nCards > o.getMaxDrawable()){
                System.out.println("The number of cards must be minor");
                nCards=4;
            }
        }
        for(int i = 0; i< nCards; i++){
            flag = true;
            coords = new ArrayList<Integer>();
            int z=i+1;
            System.out.println("Insert the coordinates of the " + z + " card : ");
            System.out.print("x : ");
            x = readingInt();
            x--;
            while(x>= o.getHeightBoard() || x<0){
                System.out.println("Not valid coordinate! Retry.");
                System.out.print("x : ");
                x = readingInt();
                x--;
            }
            System.out.print("y : ");
            y = readingInt();
            y--;
            while(y>= o.getLenghtBoard() || y<0){
                System.out.println("Not valid coordinate! Retry.");
                System.out.print("y : ");
                y = readingInt();
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
        notifyObservers(Event.PLAYER_DRAW_POSITIVE, null,drawen,null);
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

    public int readingInt(){
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
