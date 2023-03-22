import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main( String args[] ) {
        Scanner myInput = new Scanner( System.in );
        int numPlayers;
        int numberCardsThrowable;
        int x;
        int y;
        int index;
        int choosencolumn;
        String player;
        ArrayList<String> usernames=null;
        Game currentMatch;
        Player currentPlayer;
        TablePosition currentPosition;
        do {
            System.out.println("Enter the number of the players: ");
            numPlayers = myInput.nextInt();
        }while(numPlayers<2||numPlayers>4);
        for(int i=0;i<numPlayers;i++) {
            System.out.println("Enter your username: ");
            player = myInput.nextLine();
            usernames.add(player);
        }
        currentMatch=new Game(numPlayers,usernames);
        currentPlayer=currentMatch.getFirstPlayer();
        currentPosition=currentPlayer.getCurrentPosition();
        System.out.println("How many card do you wanna throw? ");
        numberCardsThrowable= myInput.nextInt();

        // TODO controlli vari
        for(int i=0;i<numberCardsThrowable;i++) {
            x=myInput.nextInt();
            y=myInput.nextInt();
            currentPlayer.drawFromBoard(currentMatch.getBoard(), x, y);
        }
        choosencolumn=myInput.nextInt();
        //TODO controlli sulla bookshelf
        currentPlayer.getCards();
        index=myInput.nextInt();
        currentPlayer.getCard(index);
        currentPlayer.insertInBookshelf(choosencolumn,);



    }
}