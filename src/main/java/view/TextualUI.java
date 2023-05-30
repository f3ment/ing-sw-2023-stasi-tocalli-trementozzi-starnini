package view;

import model.Message;
import model.Type;
import model.views.ArrayListView;
import model.views.BoxView;
import model.views.GameView;
import model.ItemTiles;
import model.views.PlayerView;
import utils.*;

import java.io.IOException;
import java.util.*;
public class TextualUI extends View implements Runnable {

    private String username;
    private boolean myTurn = true;
    private boolean flagChat = false;

    private boolean lobbyExist = false;
    private boolean firstChat;
    private boolean choosing = false;

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


        setChanged();
        notifyObservers(new Message(Event.GAME_INIT));

    }
    public void update(Message message) {
        if(message.getEvent().equals(Event.GET_CHAT) && message.getUserName().equals(username)){
            if(message.getChat().getActive().contains(username)){
                this.flagChat = true;
                System.out.println("----------------------------");
                System.out.println(" -> Use '/exit' to get back to the game;");
                System.out.println(" -> Use '/toUSERNAME' to send a private message to the 'USERNAME' specified;");
                System.out.println("This is the chat : ");
                try{
                    message.getChat().getLastTen().forEach(e -> e.forEach(
                        (key, value) -> {
                            if (key.equals(username)) {
                                value.forEach((mesg, to) ->{
                                        System.out.println("You" +( to!=null? " to " + to + " > " + mesg : ">" + mesg) );
                                });
                            } else {
                                value.forEach((mesg, to) ->{
                                    System.out.println(key +( to!=null? (to.equals(username)? " to You > " + mesg :  " > " + mesg ) : ">" + mesg) );
                                });
                            }
                        }
                    ));
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }
        }else if (message.getEvent().equals(Event.SEND_MESSAGE)) {
            if (message.getChat().getActive().contains(username)) {
                message.getChat().getLast().forEach((key, value) -> {
                    if (!key.equals(username)) {
                        value.forEach((mesg, to) -> {
                            if (to != null && to.equals(username)) {
                                System.out.println(key + " to you > " + mesg);
                            } else if (to == null) {
                                System.out.println(key + " > " + mesg);
                            }
                        });
                    }
                });
            }
        }else if(message.getEvent().equals(Event.EXIT_CHAT) && message.getUserName().equals(username)){
            if (!message.getChat().getActive().contains(username)){
                synchronized (this){
                    System.out.println("----------------------------");
                    System.out.println("Now you can play.");
                    this.notifyAll();
                }
            }
        }
        else if( message.getModel()!= null && !message.getModel().getCurrentPlayer().getUsername().equals(username)){
            if(!flagChat && !choosing){
                synchronized (this){
                    if (myTurn && !flagChat) {
                        myTurn = false;

                        System.out.print(Color.YELLOW_BOLD_BRIGHT);
                        System.out.println(message.getModel().getCurrentPlayer().getUsername() + " is playing, wait for your turn!");
                        System.out.print(Color.RESET);
                        showFirstCommonGoal(message.getModel());
                        showSecondCommonGoal(message.getModel());
                        showAllScore(message.getModel());
                        showAllBookshelves(message.getModel());

                        showBoard(message.getModel());
                        choice();
                        while (flagChat) {
                            try {
                                this.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException();
                            }
                        }
                        //showBookshelf(o); -> non si può usare perché mostriamo la currentBookshelf che non corrisponde a quella del giocatore in attesa
                    }
                }
            }
        }else if(flagChat){
            synchronized (this){
                while (flagChat) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        if (message.getEvent().equals(Event.RECONNECTION)) {
            synchronized (this){
                System.out.println("you are in tha match!");
            }
        } else {
            if (message.getEvent().equals(Event.FINISH_MATCH)) {
                synchronized (this) {
                    System.out.println(Color.RED_BOLD_BRIGHT + "---END OF THE GAME---" + Color.RESET);
                    System.out.println(Color.GREEN_BRIGHT + "THE WINNER IS ==>" + message.getModel().getWinner() + Color.RESET);
                }
                setChanged();
                notifyObservers(new Message(Event.FINISH_MATCH));
            } else if (message.getModel() == null || message.getModel().getCurrentPlayer().getUsername().equals(username)) {
                myTurn = true;
                if (message.getEvent().equals(Event.PLAYER_DRAW_NEGATIVE)) {
                    synchronized (this){
                        System.out.println(Color.RED + "The cards you have selected are invalid, please select other cards : " + Color.RESET);
                    }
                    playerDraw(message.getModel());
                } else if (message.getEvent().equals(Event.PLAYER_DRAW_POSITIVE)) {
                    synchronized (this){
                        System.out.println(Color.GREEN + "Cards picked correctly!" + Color.RESET);
                        //show picked cards
                        showHand(message.getModel());
                    }
                    playerInsert(message.getModel());
                } else if (message.getEvent().equals(Event.PLAYER_INSERT_NEGATIVE)) {
                    synchronized (this){
                        System.out.println(Color.RED + "The selected column is not valid! Retry. " + Color.RESET);
                        showHand(message.getModel());
                    }
                    playerInsert(message.getModel());
                } else if (message.getEvent().equals(Event.PLAYER_INSERT_POSITIVE)) {
                    synchronized (this){
                        System.out.println(Color.GREEN + "Cards inserted correctly!" + Color.RESET);
                        showBookshelf(message.getModel());
                    }
                    setChanged();
                    notifyObservers(new Message(Event.PLAYER_FINISH));
                    //todo adjust events with start method
                } else if (message.getEvent().equals(Event.PLAYER_FINISH)) {
                    synchronized (this) {
                        if (choice()) {
                            while (flagChat) {
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException();
                                }
                            }
                        }
                    }
                    start(message.getModel());
                } else if (message.getEvent().equals(Event.NEW_TURN)) {
                    synchronized (this) {
                        if (choice()) {
                            while (flagChat || choosing) {
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException();
                                }
                            }
                        }
                    }
                    start(message.getModel());
                } else if (message.getEvent().equals(Event.FINISH_MATCH)) {
                    synchronized (this){
                        System.out.println("Game is finished!");
                    }
                    start(message.getModel());
                } /*else if (message.getEvent().equals(Event.FINISH_MATCH)) {
                    System.out.println(Color.RED_BOLD_BRIGHT + "---END OF THE GAME---" + Color.RESET);
                    System.out.println(Color.GREEN_BRIGHT + "THE WINNER IS ==>" + message.getModel().getWinner() + Color.RESET);
                //setChanged();
                //notifyObservers(new Message(Event.DELETE_MATCH));
            } */ else if (message.getEvent().equals(Event.LOGIN)) {
                    //System.out.println(Color.RED_BRIGHT + "Username NOT valid! Try again..." + Color.RESET)
                    System.out.println("Choose your Nickname: ");
                    do {
                        System.out.print("> ");
                        Scanner input = new Scanner(System.in);
                        this.username = input.nextLine();
                        if (this.username.equals("")) {
                            System.out.println(Color.RED + "Username can't be an empty string! Retry!!" + Color.RESET);
                        }
                    } while (this.username.equals(""));

                    System.out.println("Hi" + Color.GREEN_BRIGHT + " " + username.toUpperCase() + "! " + Color.RESET + "Choose the number of players: ");

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
                    notifyObservers(new Message(Event.LOGIN, nPlayers, username));
                } else if (message.getEvent().equals(Event.WAIT_START_OF_MATCH)) {
                    lobbyExist = true;

                    System.out.println(Color.YELLOW_BOLD + "Waiting for other player to join the lobby..." + Color.RESET);
                    System.out.println(Color.BLUE_UNDERLINED + "If you want to chat with the other players connected to your lobby : " + Color.RESET);
                    System.out.println(Color.BLUE_UNDERLINED + " - write '/chat' in order to access to the chat section and to read and write messages;" + Color.RESET);
                    System.out.println(Color.BLUE_UNDERLINED + " - write '/exit' in order to get back to the game. " + Color.RESET);
                    new Thread(() -> {
                        while (true) {
                            readingForChat();
                        }
                    }).start();
                    synchronized (this) {
                        if (choice()) {
                            while (flagChat) {
                                try {
                                    this.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException();
                                }
                            }
                        }
                    }
                } else if (message.getEvent().equals(Event.LOGIN_TRUE)) {
                    lobbyExist = true;
                    synchronized (this) {
                        while (flagChat) {
                            try {
                                this.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.print(Color.GREEN_BOLD);
                            System.out.println("Game is starting...");
                            System.out.print(Color.RESET);
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    setChanged();
                    notifyObservers(new Message(Event.NEW_TURN));
                }
            }
        }
    }
    private void start(GameView o) {
        if (o.getFirstPlayer() == o.getCurrentPlayer().getUsername() && o.getEndGame()) {
            setChanged();
            notifyObservers(new Message(Event.FINISH_MATCH));
        } else {
            System.out.print(Color.GREEN_BOLD_BRIGHT);
            System.out.println(o.getCurrentPlayer().getUsername() + ", it's your turn!");
            System.out.print(Color.RESET);
            menu(o);
            showBoard(o);
            playerDraw(o);
        }
    }

    /**
     * This method shows a menu to the player before and allows him to choose what he wants to see.
     * The possible choices are:
     * 1) Show Common Goals; (show the common goals of the game)
     * 2) Show Personal Goal; (show the personal goal of the player who is playing)
     * 3) Show all Bookshelves;
     * 4) Continue to play;
     * @param o is the GameView object that contains all the information about the game
     */
    private void menu(GameView o) {
        int choice;
        System.out.print(Color.YELLOW_BOLD_BRIGHT);
        System.out.println("This is the menu, select your choice : ");
        System.out.print(Color.RESET);
        System.out.print(Color.YELLOW);
        System.out.println("    1) Show Common Goals ; ");
        System.out.println("    2) Show Personal Goal ; ");
        System.out.println("    3) Show all Bookshelves ; ");
        System.out.println("    4) Continue to play ; ");
        System.out.print(Color.RESET);

        choice = readingInt();
        while( choice <1 || choice >4){
            System.err.println("Error! The selected choice is not in the menu! Retry : ");
            choice = readingInt();
        }
        switch (choice) {
            case 1:
                showFirstCommonGoal(o);
                showSecondCommonGoal(o);
                break;
            case 2:
                System.out.println("This is your personal goal : ");
                showPersonalGoal(o);
                break;
            case 3:
                showAllBookshelves(o);
                break;
            case 4:
                System.out.println("Ready to play...");
                break;
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method shows the board of the game, printing the matrix of the living room with its Item Tiles
     * @param o is the GameView object that contains the model of the game
     */
    void showBoard(GameView o) {
        System.out.println("------- BOARD -------");
        int a;
        System.out.print(" ");
        for (int j = 0; j < o.getHeightBoard(); j++) {
            a = j + 1;
            System.out.print(Color.WHITE_BRIGHT);
            System.out.print("  " + a);
        }
        System.out.print(Color.BLACK_BRIGHT + "\n");
        //System.out.print("  /");
        //for (int i = 0; i <= 2 * o.getLenghtBoard(); i++) System.out.print("■■");
        System.out.print(Color.RESET);
        for (int i = 0; i < o.getHeightBoard(); i++) {
            a = i + 1;
            System.out.print(Color.WHITE_BRIGHT + "" + a);
            for (int j = 0; j < o.getLenghtBoard(); j++) {
                BoxView box = o.getBoard()[i][j];
                if (box.getValid()) {
                    ItemTiles el = box.getItemContained();
                    if (el != null) {
                        //System.out.print(" " + el.getType().toString().charAt(0));
                        showItemTile(el);

                    } else {
                        System.out.print(Color.BLACK);
                        System.out.print(" ▓▓");
                        System.out.print(Color.RESET);
                    }
                } else {
                    System.out.print(Color.BLACK);
                    System.out.print(" ▓▓");
                    System.out.print(Color.RESET);
                }
            }

            System.out.print(Color.BLACK_BRIGHT + " \n" + Color.RESET);
        }
        //System.out.print(Color.BLACK_BRIGHT + "  \\");
        //for (int i = 0; i <= 2 * o.getLenghtBoard(); i++) System.out.print(" -");
        System.out.print("\n" + Color.RESET);
        System.out.println("");
    }

    /**
     * Method that shows all scores of all players with their username in vertical
     * @param o GameView object that contains all the information about the game
     */
    void showAllScore(GameView o){
        System.out.println("------- SCORE -------");
        HashMap<String, Integer> playerScore = (HashMap<String, Integer>) o.getMapPlayerScore();
        for(Map.Entry<String, Integer> set : playerScore.entrySet()){
            String nick = set.getKey();

            if(o.getCurrentPlayer().getUsername().equals(nick)){
                System.out.print(Color.GREEN);
            }else{
                System.out.print(Color.BLUE);
            }

            if(nick.equals(username)){
                nick = "You";
            }

            System.out.println("<" + nick + "> " + set.getValue());
            System.out.print(Color.RESET);
        }
        System.out.println("");
    }

    /**
     *  Method that shows all bookshelves of all players with their username in horizontal
     * @param o GameView object that contains all the information about the game
     */
    void showAllBookshelves(GameView o) {
        System.out.println("----- BOOKSHELF -----");
        ArrayListView PlayerList = o.getPlayerList();

        for(int n=0; n<o.getNumPlayer(); n++){
            PlayerView player = (PlayerView) PlayerList.get(n);
          // Verificarer se lo username è più lungo della board
          // operare in modo differente a seconda del controllo precedente
          // lo spezzone di codice che c'è sotto è per quando gli username sono più 'corti' della bookshelf
            System.out.print(" ");
            String nick = player.getUsername();
            if(nick.equals(username)){
                nick = "You";
            }
            for (int j = 0; j < o.getLenghtBookshelf()*3; j++) {
                if(j<nick.length()){
                    System.out.print(Color.WHITE_BRIGHT);
                    System.out.print(nick.charAt(j));
                    System.out.print(Color.RESET);
                }else {
                    System.out.print(" ");
                }
            }
            System.out.print(" ");
        }
        System.out.print("\n");
        for(int n=0; n<o.getNumPlayer(); n++) {
            //System.out.print(Color.WHITE_BRIGHT + "| " + Color.RESET);
            for (int j = 0; j < o.getLenghtBookshelf(); j++) {
                System.out.print(Color.WHITE_BRIGHT + "  " + (j + 1) + Color.RESET);
            }
            System.out.print("  ");
        }
        //System.out.print(Color.WHITE_BRIGHT + "|" + Color.RESET);
        System.out.print("\n");
        for (int i = 0; i < o.getHeightBookshelf(); i++) {
            for (int n = 0; n < o.getNumPlayer(); n++) {
                //System.out.print(Color.WHITE_BRIGHT + "| " + Color.RESET);
                for (int j = 0; j < o.getLenghtBookshelf(); j++) {
                    ItemTiles[][] curr = (ItemTiles[][])((PlayerView) PlayerList.get(n)).getBookshelf();
                    ItemTiles elem;
                    try {
                        elem = curr[i][j];
                        //System.out.print(" " + elem.getType().toString().charAt(0));
                        showItemTile(elem);
                    } catch (Exception e) {
                        System.out.print(Color.BLACK_BRIGHT + " ▓▓" + Color.RESET);
                    }
                }
                System.out.print("  ");
            }
            //System.out.print(Color.WHITE_BRIGHT + "|" + Color.RESET);
            System.out.print("\n");
        }
        System.out.println("");
    }

    /**
     * Method that shows the bookshelf of the current player
     * @param o GameView object that contains all the information about the game
     */
    void showBookshelf(GameView o) {
        System.out.println("This is your bookshelf : ");
        for (int j = 0; j < o.getLenghtBookshelf(); j++) {
            System.out.print(Color.WHITE_BRIGHT + "  " + (j + 1) + Color.RESET);
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
                    System.out.print(Color.BLACK_BRIGHT + " ▓▓" + Color.RESET);
                }
            }
            System.out.print("\n");
        }
    }

    private void showItemTile(ItemTiles elem) {
        System.out.print(" " + elem.getType().getColor() + "▓▓" + Color.RESET);
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
        order.forEach(e -> System.out.println(e + 1 + " " + o.getHand(e).getType().getColor() + "▓▓" +Color.RESET));
        showBookshelf(o);

        System.out.println("Now insert in which column would you like to insert your cards. ");
        System.out.println("REMEMBER : it must be between 1 and " + o.getLenghtBookshelf() + ".");
        column = readingInt() - 1;
        //choose column
        setChanged();
        notifyObservers(new Message(Event.PLAYER_INSERT_POSITIVE, column, order));

    }

    private void playerDraw(GameView o) {
        ArrayList<ArrayList<Integer>> drawen = new ArrayList<>();
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
        notifyObservers(new Message(Event.PLAYER_DRAW_POSITIVE, drawen));
    }


    void showHand(GameView o) {
        for (int i = 0; i < o.getPickedCards().size(); i++) {
            System.out.print(i + 1 + "  ");
        }
        System.out.print("\n");

        for (int i = 0; i < o.getPickedCards().size(); i++) {
            System.out.print(o.getHand(i).getType().getColor()+  "▓▓ " + Color.RESET);
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
            System.out.print("> ");
            input = new Scanner(System.in);
            try {
                userInput = input.nextInt();
                if (1 <= userInput && userInput < 10) {
                    break;
                } else {
                    System.err.println("Invalid Number, Please try again");
                }
            } catch (Exception e) {
                System.err.println("Invalid Number, Please try again");
            }
        }
        return userInput;
    }


    public void showFirstCommonGoal(GameView o){
        System.out.println(Color.YELLOW_BOLD_BRIGHT + "1st Common goal: " + Color.YELLOW + o.getFirstCommonGoalDescription() + Color.RESET);
    }
    public void showSecondCommonGoal(GameView o){
        System.out.println(Color.YELLOW_BOLD_BRIGHT + "2nd Common goal: " + Color.YELLOW + o.getSecondCommonGoalDescription() + Color.RESET);
    }

    public synchronized void readingForChat(){
        if(lobbyExist && firstChat ){
            this.firstChat = false;
            setChanged();
            notifyObservers(new Message(Event.GET_CHAT, username));
        }else if (lobbyExist && flagChat){
            Scanner input = new Scanner(System.in);
            String in = input.nextLine();
            if (in.equals("/chat")) {
                if (flagChat) {
                    System.err.println("You are already chatting!");
                }
            } else if (in.equals("/exit")) {
                flagChat = false;
                setChanged();
                notifyObservers(new Message(Event.EXIT_CHAT, username));
            } else if (flagChat) {
                String to = null;
                if(in.contains("/to")){
                    in = in.substring(3);
                    to = in.split(" ", 2)[0];
                    //todo parte l'eccezione se la stringa è vuota
                    in = in.split(" ", 2)[1];
                }
                setChanged();
                notifyObservers(new Message(Event.SEND_MESSAGE, username, in, to));
           }
        }
    }

    public boolean choice(){
        boolean flagErr;
        if(!flagChat && !choosing){
            choosing = true;
            do {
                flagErr = false;
                System.out.println("Write '/chat' to send and read messages or write '/play' to get back to the game");
                Scanner in = new Scanner(System.in);
                String input = in.nextLine();
                if (input.equals("/chat")) {
                    flagChat = true;
                    firstChat = true;
                    System.out.println("Opening chat ...");
                } else if (input.equals("/play")) {
                    flagChat = false;
                    firstChat = false;
                    System.out.println("Now you can continue to play.");
                } else {
                    System.err.println("ERROR! Input is not valid.");
                    flagErr = true;
                }
            } while (flagErr);
        }else{
            return true;
        }
        choosing = false;
        return flagChat;
    }

    public void showPersonalGoal(GameView o){
        Map<String, String> elem;
        boolean found;
        for (int i = 0; i < o.getHeightBookshelf(); i++){
            System.out.print(Color.WHITE_BRIGHT + "|" + Color.RESET);
            for(int j=0; j < o.getLenghtBookshelf(); j++){
                found = false;
                for (String e : o.getPersonalGoalByUsername(username).keySet()) {
                    elem = (Map<String, String>) o.getPersonalGoalByUsername(username).get(e);
                    if(Integer.valueOf(elem.get("X")).equals(i) && Integer.valueOf(elem.get("Y")).equals(j)){
                        found = true;
                        switch (e) {
                            case "CATS" :
                                System.out.print(" " + Type.CATS.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "GAMES" :
                                System.out.print(" " + Type.GAMES.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "PLANTS" :
                                System.out.print(" " + Type.PLANTS.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "BOOKS" :
                                System.out.print(" " + Type.BOOKS.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "FRAMES" :
                                System.out.print(" " + Type.FRAMES.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "TROPHIES" :
                                System.out.print(" " + Type.TROPHIES.getColor() + "▓▓" + Color.RESET);
                                break;
                        }
                    }
                }
                if(!found){
                    System.out.print(" " + Color.BLACK + "▓▓" + Color.RESET);
                }
            }
            System.out.println(Color.WHITE_BRIGHT + " |" +Color.RESET);
        }
    }
}
