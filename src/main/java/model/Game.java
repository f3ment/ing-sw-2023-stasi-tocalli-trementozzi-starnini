package model;

import model.board.Board;
import model.goals.CommonGoal;

import java.util.List;

public class Game {

    private int playerNumber;
    private TablePosition currentPosition;
    private Player firstPlayer;


    private Bag bag;
    private CommonGoal firstCommonGoal;
    private CommonGoal secondCommonGoal;
    private List<TablePosition> tablePositionList;
    private List<Player> playerList;
    private Board board;


    /*
     *  TO DO: metodi setter e getter, metodi creator.
     */


    public void validateCommonGoal(TablePosition tablePosition){}
    public void validatePersonalGoal(TablePosition tablePosition){}
    public void validateAdjacent(TablePosition tablePosition){}
    public void fillBoard(){}
    public void setEndGame(){}


}
