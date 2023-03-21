package model;

import com.sun.tools.javac.jvm.Items;
import model.board.FourBoard;
import model.board.ThreeBoard;
import model.board.TwoBoard;
import model.goals.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Game {
    private boolean finish;
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

    public Game(int playerNumber, ArrayList<String> usernames)  {
        Random randomInt = new Random();
        int index;
        boolean[] nums= new boolean[12];
        ArrayList<CommonGoal> commonGoals;

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

        this.board.setBox(bag);

        this.finish = false;
        }
    public void validateCommonGoal(TablePosition tablePosition) throws Exception {
        ScoringToken res;
        //check if player at current tableposition has already achieved the first commmon goal
        if(tablePosition.getPlayer().getToken(0) == null){
            //add token to current player returned from validate
            tablePosition.getPlayer().setToken(0, firstCommonGoal.validate(tablePosition.getBookshelf()));
        }

        //check if player at current tableposition has already achieved the second commmon goal
        if(tablePosition.getPlayer().getToken(1) == null){
            //add token to current player returned from validate
            tablePosition.getPlayer().setToken(1,secondCommonGoal.validate(tablePosition.getBookshelf()));
        }
    }
    public void validatePersonalGoal(TablePosition tablePosition){}

    //validateAdjacent(position,0,0,0,batrix di false,null,true,0,occupied di false)

    public void validateAdjacent(TablePosition tablePosition){
        boolean[][] batrix = new boolean[6][5];
        boolean[][] occupied = new boolean[6][5];
        int res;
        res = validateAdjacentRecursive(tablePosition, 0, 0,0, batrix, null, true, 0, occupied);
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() + res );
    }
    public int validateAdjacentRecursive(TablePosition tablePosition,int i,int j,int count,boolean[][] batrix,Type type,boolean starting,int score,boolean[][] occupied){
        Bookshelf validateshelf= tablePosition.getBookshelf();
        if(!batrix[i][j] && validateshelf.getItem(i,j)!=null && !starting && !occupied[i][j]) {
            if(validateshelf.getItem(i,j).getType().equals(type)){
                    count++;
                    occupied[i][j]=true;
                    if(i<validateshelf.getHeight()-1) {
                        count = count+validateAdjacentRecursive(tablePosition, i + 1, j, count, batrix, type, false,score,occupied);
                    }
                    if(j<validateshelf.getLength()-1){
                        count = count+validateAdjacentRecursive(tablePosition, i , j+1, count, batrix, type, false,score,occupied);
                    }
                    if(count>=3){
                        batrix[i][j]=true;
                    }
                    occupied[i][j]=false;
                    return count;
            }else{
                return count;
            }
        }else if(starting && validateshelf.getItem(i,j)!=null) {
            occupied[i][j]=true;
            if (i < validateshelf.getHeight() - 1) {
                count = validateAdjacentRecursive(tablePosition, i + 1, j, 0, batrix, validateshelf.getItem(i, j).getType(), false, score,occupied);
            }
            if (j < validateshelf.getLength() - 1) {
                count = count + validateAdjacentRecursive(tablePosition, i, j + 1, 0, batrix, validateshelf.getItem(i, j).getType(), false, score,occupied);
            }
            if (count == 3) {
                score = score + 2;
                batrix[i][j] = true;
                starting = false;
            } else if (count == 4) {
                score = score + 3;
                batrix[i][j] = true;
                starting = false;
            } else if (count == 5) {
                score = score + 5;
                batrix[i][j] = true;
                starting = false;
            } else if (count >= 6) {
                score = score + 8;
                batrix[i][j] = true;
                starting = false;
            }
            count = 0;
            occupied[i][j]=false;

            if (j < validateshelf.getLength() - 1) {
                score = validateAdjacentRecursive(tablePosition, i, j + 1, count, batrix, null, true, score,occupied);
            } else if (i < validateshelf.getHeight() - 1) {
                score = validateAdjacentRecursive(tablePosition, i + 1, 0, count, batrix, null, true, score,occupied);
            }
            return score;
        }else if(batrix[i][j]==true|| validateshelf.getItem(i,j)==null){
            if (j < validateshelf.getLength() - 1) {
                score = validateAdjacentRecursive(tablePosition, i, j + 1, 0, batrix, null, true, score,occupied);
            } else if (i < validateshelf.getHeight() - 1) {
                score = validateAdjacentRecursive(tablePosition, i + 1, 0, 0, batrix, null, true, score,occupied);
            }
            return score;
        }
        return score;
    }

    public boolean fillBoard(){
        return this.board.setBox(this.bag);
    }

    //todo chiamata isFull della bookshelf che a sua volta chiama setEndGame
    public void setEndGame(boolean finish){
        this.finish = finish;
    }
    public void setCurrentPosition(){
        int newCurrentIndex=tablePositionList.indexOf(currentPosition)+1;
        if(newCurrentIndex==tablePositionList.size()){
            newCurrentIndex=0;
        }
        currentPosition=tablePositionList.get(newCurrentIndex);
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Bag getBag() {
        return bag;
    }

    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    public boolean isFinish() {
        return finish;
    }

    public FourBoard getBoard() {
        return board;
    }
}
