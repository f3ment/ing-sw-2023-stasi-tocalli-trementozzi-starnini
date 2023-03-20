package model;

import com.sun.tools.javac.jvm.Items;
import model.board.FourBoard;
import model.board.ThreeBoard;
import model.board.TwoBoard;
import model.goals.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Game {

    private int playerNumber;
    private TablePosition currentPosition;
    private Player firstPlayer;
    private Bag bag;

    private CommonGoal firstCommonGoal;
    private CommonGoal secondCommonGoal;
    private List<TablePosition> tablePositionList;
    private FourBoard board;
    private List<Bookshelf> bookshelves;


    // The Game constructor:
    // -creates a new game taking the number of players and their nicknames as parameters
    // -initializes all the table positions with their relative players
    // -initializes all the game attributes relative to the game and the board
    // -randomly assigns player's personal goal and chooses two game's common goals

    public Game(int playerNumber, ArrayList<String> usernames){
        Random randomInt = new Random();
        int index;
        boolean[] nums= new boolean[12];
        ArrayList<CommonGoal> commonGoals;
        boolean res; //fill up board
        ItemTiles item; // fill up board

        this.playerNumber = playerNumber;
        this.bag = new Bag();

        this.bookshelves = new ArrayList<Bookshelf>();
        for(int i = 0 ; i < playerNumber; i++){
            bookshelves.add(new Bookshelf());
        }

        this.tablePositionList = new ArrayList<TablePosition>();
        for(int i = 0; i < playerNumber; i++){
            do{
                index =1+randomInt.nextInt(11);
            }while(nums[index-1]);
                nums[index]=true;
            this.tablePositionList.add(i, new TablePosition(usernames.get(i), new PersonalGoal(index), this.bookshelves.get(i)));
        }

        index= randomInt.nextInt(3);
        tablePositionList.get(index).setFirstPosition(true);
        firstPlayer = tablePositionList.get(index).getPlayer();
        currentPosition = tablePositionList.get(index);

        switch (playerNumber) {
            case 2:
                this.board = new TwoBoard();
            case 3:
                this.board = new ThreeBoard();
            case 4:
                this.board = new FourBoard();
        }

        commonGoals = new ArrayList<CommonGoal>();
        commonGoals.add( new CheckAngles(0,playerNumber));
        commonGoals.add( new CheckColumnDifferent(0,playerNumber, 2, true));
        commonGoals.add( new CheckColumnDifferent(0,playerNumber, 3, false));
        commonGoals.add( new CheckColumnLength(0,playerNumber));
        commonGoals.add( new CheckCross(0,playerNumber));
        commonGoals.add( new CheckEightEquals(0,playerNumber));
        commonGoals.add( new CheckEqualsDiagonal(0,playerNumber));
        commonGoals.add( new CheckGroupsSameType(0,playerNumber, 2, 6));
        commonGoals.add( new CheckGroupsSameType(0,playerNumber, 4, 4));
        commonGoals.add( new CheckRowDifferent(0,playerNumber, 2, false));
        commonGoals.add( new CheckRowDifferent(0,playerNumber, 4, true));
        commonGoals.add( new CheckSquare(0,playerNumber));

        index = randomInt.nextInt(commonGoals.size()-1);
        this.firstCommonGoal = commonGoals.get(index);
        commonGoals.remove(index);
        this.firstCommonGoal.setRomanNumber(1);

        index = randomInt.nextInt(commonGoals.size()-1);
        this.secondCommonGoal = commonGoals.get(index);
        this.secondCommonGoal.setRomanNumber(2);

        do{
            item = bag.extract();
        }while(board.setBox(item));

        for(int i = 0; i< board.getMaxHeight(); i++){
            for(int j = 0; j< board.getMaxLength(); j++){
                board.setBox(bag.extract());
            }
        }

    }
    /*
     *  TO DO: metodi setter e getter, metodi creator.
     */


    public void validateCommonGoal(TablePosition tablePosition){

    }
    public void validatePersonalGoal(TablePosition tablePosition){

    }
    public void validateAdjacent(TablePosition tablePosition){
        tablePosition.getBookshelf().

    }
    public void fillBoard(){
        ItemTiles item;
        do{
            item = bag.extract();
        }while(board.setBox(item));

    }
    public void setEndGame(){}
    public void setCurrentPosition(){
        int newCurrentIndex=tablePositionList.indexOf(currentPosition)+1;
        if(newCurrentIndex==tablePositionList.size()){
            newCurrentIndex=0;
        }
        currentPosition=tablePositionList.get(newCurrentIndex);
    }

}
