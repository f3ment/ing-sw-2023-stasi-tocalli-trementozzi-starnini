package view;

import model.ArrayListView;
import model.BoxView;
import model.GameView;
import model.ItemTiles;
import utils.*;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;


/*
1 Item = 1 cella
CAT = GREEN_BACKGROUND_BRIGHT
BOOKS = WHITE_BACKGROUND_BRIGHT
GAMES = YELLOW_BACKGROUND_BRIGHT
FRAMES = BLU_BACKGROUND_BRIGHT
TROPHY = CYAN _BACKGROUND_BRIGHT
PLANTS = MAGENTA_BACKGROUND_BRIGHT
 */
public class TextualUI extends Observable<Event> implements Runnable {

    private String username;
    private boolean myTurn = true;

    //TODO vietare input client quando è in attesa;
    @Override
    public void run() {


        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.RED_BOLD);
        System.out.println(".___  ___. ____    ____         _______. __    __   _______  __       _______  __   _______ " + "\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.YELLOW_BOLD);
        System.out.println("|   \\/   | \\   \\  /   /        /       ||  |  |  | |   ____||  |     |   ____||  | |   ____|" + "\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.GREEN_BOLD);
        System.out.println("|  \\  /  |  \\   \\/   /        |   (----`|  |__|  | |  |__   |  |     |  |__   |  | |  |__   " + "\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.BLUE_BOLD);
        System.out.println("|  |\\/|  |   \\_    _/          \\   \\    |   __   | |   __|  |  |     |   __|  |  | |   __|  " + "\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.MAGENTA_BOLD);
        System.out.println("|  |  |  |     |  |        .----)   |   |  |  |  | |  |____ |  `----.|  |     |  | |  |____ " + "\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.CYAN_BOLD);
        System.out.println("|__|  |__|     |__|        |_______/    |__|  |__| |_______||_______||__|     |__| |_______|" + "\033[0m");
        System.out.print(Color.RESET);

        System.out.println("\033[40m" + "                                                                                            " + "\33[0m");


        //noinspection InfiniteLoopStatement
        setChanged();
        notifyObservers(Event.GAME_INIT, null, null, null);

    }

    //update chiamato direttamente dall'oggetto che si occupa di gestire il client
    public void update(GameView o, Enum arg) {
        int i = 0;
        if (o == null || o.getCurrentPlayer().getUsername().equals(username)) {
            myTurn = true;
            if (arg.equals(Event.PLAYER_DRAW_NEGATIVE)) {
                System.out.println(Color.RED + "The cards you have selected are invalid, please select other cards : " + Color.RESET);
                playerDraw(o);
            } else if (arg.equals(Event.PLAYER_DRAW_POSITIVE)) {
                System.out.println(Color.GREEN + "Cards picked correctly!" + Color.RESET);
                //show picked cards
                showHand(o);
                playerInsert(o);
            } else if (arg.equals(Event.PLAYER_INSERT_NEGATIVE)) {
                System.out.println(Color.RED + "The selected column is not valid! Retry. " + Color.RESET);
                showHand(o);
                playerInsert(o);
            } else if (arg.equals(Event.PLAYER_INSERT_POSITIVE)) {
                System.out.println(Color.GREEN + "Cards inserted correctly!" + Color.RESET);
                showBookshelf(o);
                setChanged();
                notifyObservers(Event.PLAYER_FINISH, null, null, null);
            } else if (arg.equals(Event.PLAYER_FINISH)) {
                start(o);
            } else if (arg.equals(Event.NEW_TURN)) {
                start(o);
            } else if (arg.equals(Event.FINISH_MATCH)) {
                System.out.println(Color.RED_BOLD_BRIGHT + "---END OF THE GAME---" + Color.RESET);
                System.out.println(Color.GREEN_BRIGHT + "THE WINNER IS ==>" + o.getWinner() + "" + Color.RESET);
            } else if (arg.equals(Event.LOGIN)) {
                //System.out.println(Color.RED_BRIGHT + "Username NOT valid! Try again..." + Color.RESET)
                System.out.println("Choose your Nickname: ");
                System.out.print("> ");
                Scanner input = new Scanner(System.in);
                this.username = input.nextLine();
                System.out.println("Hi" + Color.GREEN_BRIGHT + " " + username.toUpperCase() + "! " + Color.RESET +"Choose the number of players: ");

                int nPlayers = 0;
                do {
                    nPlayers = readingInt();
                    if (nPlayers < 2 || nPlayers > 4) {
                        System.out.print(Color.RED);
                        System.out.println(nPlayers + " is not valid, please try again!!");
                        System.out.println("Choose between those values: " + Color.RED_BOLD + "2, 3, 4.");
                        System.out.print(Color.RESET);
                        System.out.print("> ");
                    }
                } while (nPlayers < 2 || nPlayers > 4);

                setChanged();
                notifyObservers(Event.LOGIN, nPlayers, null, username);
            } else if (arg.equals(Event.WAIT_START_OF_MATCH)) {
                System.out.println(Color.YELLOW_BOLD + "Waiting for other player to join the lobby..." + Color.RESET);

            } else if (arg.equals(Event.LOGIN_TRUE)) {
                System.out.print(Color.GREEN_BOLD);
                System.out.println("Game is starting...");
                System.out.print(Color.RESET);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                setChanged();
                notifyObservers(Event.NEW_TURN, null, null, null);
            }
        } else {
            if (myTurn) {
                myTurn = false;
                System.out.print(Color.YELLOW_BOLD_BRIGHT);
                System.out.println(o.getCurrentPlayer().getUsername() + " is playing, wait for your turn!");
                System.out.print(Color.RESET);
                showAllBookshelf(o);

                showBoard(o);
                //showBookshelf(o); -> non si può usare perché mostriamo la currentBookshelf che non corrisponde a quella del giocatore in attesa
            }
        }
    }

    private void start(GameView o) {


        if (o.getFirstPlayer() == o.getCurrentPlayer().getUsername() && o.getEndGame()) {
            setChanged();
            notifyObservers(Event.FINISH_MATCH, null, null, null);
        }
        System.out.print(Color.GREEN_BOLD_BRIGHT);
        System.out.println(o.getCurrentPlayer().getUsername() + ", it's your turn!");
        System.out.print(Color.RESET);
        showAllBookshelf(o);
        showBoard(o);
        playerDraw(o);
    }

    void showBoard(GameView o) {
        System.out.print(Color.BLUE_UNDERLINED);
        //4System.out.print(Color.GREEN);
        System.out.println("This is the current board :");
        System.out.print(Color.RESET);
        int a;
        System.out.print("   ");
        for (int j = 0; j < o.getHeightBoard(); j++) {
            a = j + 1;
            System.out.print(Color.WHITE_BRIGHT);
            System.out.print(" " + a);
        }
        System.out.print(Color.BLACK_BRIGHT + "\n");
        System.out.print("  /");
        for (int i = 0; i <= 2 * o.getLenghtBoard(); i++) System.out.print("-");
        System.out.print("\\\n" + Color.RESET);

        for (int i = 0; i < o.getHeightBoard(); i++) {
            a = i + 1;
            System.out.print(Color.WHITE_BRIGHT + "" + a + Color.BLACK_BRIGHT + " |" + Color.RESET);
            for (int j = 0; j < o.getLenghtBoard(); j++) {
                BoxView box = o.getBoard()[i][j];
                if (box.getValid()) {
                    ItemTiles el = box.getItemContained();
                    if (el != null) {
                        //System.out.print(" " + el.getType().toString().charAt(0));
                        showItemTile(el);

                    } else {
                        System.out.print(Color.YELLOW_BOLD);
                        System.out.print(" -");
                        System.out.print(Color.RESET);
                    }
                } else {
                    System.out.print(Color.RED_BOLD);
                    System.out.print(" x");
                    System.out.print(Color.RESET);
                }
            }

            System.out.print(Color.BLACK_BRIGHT + " |\n" + Color.RESET);
        }
        System.out.print(Color.BLACK_BRIGHT + "  \\");
        for (int i = 0; i <= 2 * o.getLenghtBoard(); i++) System.out.print("-");
        System.out.print("/\n" + Color.RESET);

    }





    void showAllBookshelf(GameView o) {
        ArrayListView bookShelfList = o.getListBookshelf();

        for(int n=0; n<o.getNumPlayer(); n++){
          // TODO: STAMPARE GLI USERNAME
          // Verificarer se lo username è più lungo della board
          // operare in modo differente a seconda del controllo precedente

          //lo spezzone di codice che c'è sotto è per quando gli username sono più 'corti' della bookshelf
        }
        for(int n=0; n<o.getNumPlayer(); n++) {
            System.out.print(Color.WHITE_BRIGHT + "| " + Color.RESET);
            for (int j = 0; j < o.getLenghtBookshelf(); j++) {
                System.out.print(Color.WHITE_BRIGHT + " " + (j + 1) + Color.RESET);
            }
            System.out.print("  ");
        }
        System.out.print(Color.WHITE_BRIGHT + "|" + Color.RESET);
        System.out.print("\n");
        for (int i = 0; i < o.getHeightBookshelf(); i++) {
            for (int n = 0; n < o.getNumPlayer(); n++) {
                System.out.print(Color.WHITE_BRIGHT + "| " + Color.RESET);
                for (int j = 0; j < o.getLenghtBookshelf(); j++) {
                    ItemTiles[][] curr = (ItemTiles[][]) bookShelfList.get(n);
                    ItemTiles elem = null;
                    try {
                        elem = curr[i][j];
                        //System.out.print(" " + elem.getType().toString().charAt(0));
                        showItemTile(elem);
                    } catch (Exception e) {
                        System.out.print(Color.BLACK_BRIGHT + " -" + Color.RESET);
                    }
                }
                System.out.print("  ");
            }
            System.out.print(Color.WHITE_BRIGHT + "|" + Color.RESET);
            System.out.print("\n");
        }

    }

    void showBookshelf(GameView o) {
        System.out.println("This is your bookshelf : ");
        for (int j = 0; j < o.getLenghtBookshelf(); j++) {
            System.out.print(Color.WHITE_BRIGHT + " " + (j + 1) + Color.RESET);
        }
        System.out.print("\n");
        for (int i = 0; i < o.getHeightBookshelf(); i++) {
            for (int j = 0; j < o.getLenghtBookshelf(); j++) {
                ItemTiles el;
                el = o.getCurrentBookshelf()[i][j];
                if (el != null) {
                    //System.out.print(" " + el.getType().toString().charAt(0));
                    showItemTile(el);
                } else {
                    System.out.print(Color.BLACK_BRIGHT + " -" + Color.RESET);
                }
            }
            System.out.print("\n");
        }
    }

    private void showItemTile(ItemTiles elem) {
        switch (elem.getType().toString().charAt(0)) {
            case 'C' ->
                    System.out.print(" " + Color.GREEN_BACKGROUND_BRIGHT + elem.getType().toString().charAt(0) + Color.RESET);
            case 'B' ->
                    System.out.print(" " + Color.YELLOW_BACKGROUND_BRIGHT + elem.getType().toString().charAt(0) + Color.RESET);
            case 'G' ->
                    System.out.print(" " + Color.WHITE_BACKGROUND_BRIGHT + elem.getType().toString().charAt(0) + Color.RESET);
            case 'F' ->
                    System.out.print(" " + Color.BLUE_BACKGROUND_BRIGHT + elem.getType().toString().charAt(0) + Color.RESET);
            case 'T' ->
                    System.out.print(" " + Color.CYAN_BACKGROUND_BRIGHT + elem.getType().toString().charAt(0) + Color.RESET);
            case 'P' ->
                    System.out.print(" " + Color.MAGENTA_BACKGROUND_BRIGHT + elem.getType().toString().charAt(0) + Color.RESET);
        }
    }

    private void playerInsert(GameView o) {
        //scan input
        ArrayList<Integer> order = new ArrayList<>();
        int column;
        boolean flag;
        //controllo input ordine
        do {
            flag = true;
            System.out.println(Color.WHITE_BRIGHT + "Insert in which order do you want to insert cards : " + Color.RESET);
            order.clear();
            for (int i = 0; i < o.getPickedCards().size(); i++) {
                System.out.print("Insert the index of the " + Color.CYAN_BOLD + (i + 1) + Color.RESET +  " card to insert : ");
                int index = readingInt();
                while (index < 1 || index > o.getPickedCards().size()) {
                    System.out.println(Color.RED_BOLD + "Invalid Index , insert again!\n> " + Color.RESET);
                    index = readingInt();
                }
                order.add(index - 1);
            }


            //check on order input
            if (order.stream().sorted().distinct().count() != o.getPickedCards().size()) {
                System.out.println(Color.RED_BOLD + "ERROR!" + Color.RED + " Found many occurrencies of the same index!" + Color.RESET);
                flag = false;
            } else {
                flag = !order.stream().allMatch(e -> e < 0 || e >= o.getPickedCards().size());
                if (!flag) {
                    System.out.println(Color.RED_BOLD + "ERROR!" + Color.RED + " Indexes inserted are not correct!" + Color.RESET);
                }
            }
            if (!flag) {
                System.out.println(Color.RED_UNDERLINED + "Retry!" + Color.RESET);
            }
        } while (!flag);

        System.out.println(Color.WHITE_BRIGHT + "The choosen order is : " + Color.RESET);
        /*TODO: aggiustare la riga successiva perché non stampa correttamente
        prende il riferimento all'ItemTile
        esempio di riferimento preso:
                                        model.ItemTiles@1612d6fb

                                        quindi stampa 'm' sempre

         */
        order.forEach(e -> System.out.println(e + 1 + " " + o.getHand(e).getType().toString().charAt(0) + " "));

        showBookshelf(o);

        System.out.println("Now insert in which column would you like to insert your cards. ");
        System.out.println("REMEMBER : it must be between 1 and " + o.getLenghtBookshelf() + ".");
        column = readingInt() - 1;
        //choose column
        setChanged();
        notifyObservers(Event.PLAYER_INSERT_POSITIVE, column, order, null);

    }

    private void playerDraw(GameView o) {
        ArrayList<ArrayList<Integer>> drawen = new ArrayList<ArrayList<Integer>>();
        int nCards, x, y;
        boolean flag;
        ArrayList<Integer> coords;
        System.out.println("Insert how many cards do you want to draw from board : ");
        nCards = readingInt();
        if (nCards > o.getMaxDrawable()) {
            System.out.println(Color.RED_BOLD + "Error!! The number of cards must be minor" + Color.RESET);
            nCards = 4;
        }
        while (nCards <= 0 || nCards > 3) {
            System.out.println(Color.RED + "The number of cards must be between " + Color.RED_BOLD + "1" + Color.RED + " and " + Color.RED_BOLD + "3" + Color.RED + "! Retry." + Color.RESET);
            System.out.println("Insert how many cards do you want to draw from board : ");
            nCards = readingInt();
            if (nCards > o.getMaxDrawable()) {
                System.out.println(Color.RED_BOLD + "Error!! The number of cards must be minor" + Color.RESET);
                nCards = 4;
            }
        }
        for (int i = 0; i < nCards; i++) {
            flag = true;
            coords = new ArrayList<Integer>();
            int z = i + 1;
            System.out.println("Insert the coordinates of the " + z + " card : ");
            System.out.print(Color.WHITE_BOLD_BRIGHT + "x : " + Color.RESET);
            x = readingInt();
            x--;
            while (x >= o.getHeightBoard() || x < 0) {
                System.out.println(Color.RED_BOLD + "Not valid coordinate! Retry." + Color.RESET);
                System.out.print(Color.WHITE_BOLD_BRIGHT + "x : " + Color.RESET);
                x = readingInt();
                x--;
            }
            System.out.print(Color.WHITE_BOLD_BRIGHT + "y : " + Color.RESET);
            y = readingInt();
            y--;
            while (y >= o.getLenghtBoard() || y < 0) {
                System.out.println(Color.RED_BOLD + "Not valid coordinate! Retry." + Color.RESET);
                System.out.print(Color.WHITE_BOLD_BRIGHT + "y : " + Color.RESET);
                y = readingInt();
                y--;
            }

            if (!o.getBoard()[x][y].getValid() || o.getBoard()[x][y].getItemContained() == null) {
                System.out.println(Color.RED_BOLD + "The chosen box is not playable! Retry." + Color.RESET);
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
            for (ArrayList<Integer> el : drawen) {
                if (el.get(0) == coords.get(0) && el.get(1) == coords.get(1)) {
                    System.out.println(Color.RED_BOLD + "ERROR! " + Color.RED + "The choosen card has already been selected! Retry." + Color.RESET);
                    flag = false;
                    i--;
                    break;
                }
            }
            if (flag) {
                drawen.add(coords);
            }
        }
        setChanged();
        notifyObservers(Event.PLAYER_DRAW_POSITIVE, null, drawen, null);
    }


    void showHand(GameView o) {
        for (int i = 0; i < o.getPickedCards().size(); i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.print("\n");

        for (int i = 0; i < o.getPickedCards().size(); i++) {
            System.out.print(o.getHand(i).getType().toString().charAt(0) + " ");
        }
        System.out.print("\n");
    }

    public int readingInt() {
        int userInput;
        Scanner input = new Scanner(System.in);
        try {
            while (System.in.available() > 0) {
                System.in.read(new byte[System.in.available()]);  //clear System.in
            }
        }catch (IOException e){
            System.out.println("error reading");
        }
        while (true) {
            try {
                userInput = input.nextInt();
                break;
            } catch (InputMismatchException | NumberFormatException ex) {
                System.out.println("Invalid Number, Please try again");
            }
        }
        return userInput;
    }
}
