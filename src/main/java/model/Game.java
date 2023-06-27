package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.board.Board;
import model.goals.CommonGoal;
import model.goals.PersonalGoal;
import utils.Event;
import utils.Observable;

import java.io.*;
import java.util.*;

/**
 * This class represents the game itself, it contains all the information about the game status,
 * all the references to the model objects and  all the methods to modify the status of the model.
 */
public class Game extends Observable<Event> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int lastIndex;
    private boolean finish;
    private final int playerNumber;
    private TablePosition currentPosition;
    private final String firstPlayer;
    private final Bag bag;
    private final CommonGoal firstCommonGoal;
    private final CommonGoal secondCommonGoal;
    private final List<TablePosition> tablePositionList;
    List <Player> finalPlayerList;

    private final Board board;

    private String winner;

    private String firstFinisher;
    private boolean endGameToken;
    private String shelfCompletedBy;

    private final String lastPlayer;

    private int finalFlow=2;


    /**
     * The game constructor, it initializes the game status and the model objects based on the number of player
     * @param usernames list of the players' usernames in the game
     * @throws IOException if the config file is not found
     */
    public Game(ArrayList<String> usernames) throws IOException {
        super();
        /*
         * Config file opening
         * */
        String configFilePath = "./src/main/resources/config.properties";
        Properties prop = new Properties();
        FileInputStream ip;
        {
            try {
                ip = new FileInputStream(configFilePath);
                prop.load(ip);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Random randomInt = new Random();
        int index;
        boolean[] nums= new boolean[12];
        final CommonGoalsGenerator commonGoalsGenerator;
        Gson gson = new Gson();



        this.playerNumber = usernames.size();
        this.bag = new Bag();
        this.lastIndex = -1;
        this.endGameToken = false;
        this.finalPlayerList = new ArrayList<>();

        //initializes the personal goal deck with 12 cards
        //every card is a hashmap of 6 couplets of key (Type) and value (pair of coordinates)

        // 1. JSON file to Java object
        Map<String, Map<String, Map<String, String>>> windows = gson.fromJson(new FileReader("./src/main/resources/personalGoals.json"),
                new TypeToken<Map<String, Map<String, Map<String, String>>>>() {}.getType());
        //Best Practice
        //object.forEach((key, value) -> value.values().forEach(i -> System.out.println(i.get("X"))));

        // Ours
        /*
        object.entrySet().stream().forEach(e-> {
            e.getValue().entrySet().stream().map(
                    k -> k.getValue()).forEach(i -> System.out.println(i.get("X")));
        });
        */
        this.tablePositionList = new ArrayList<>();
        ArrayList<Map<String, Map<String, String>>> windowsArr = new ArrayList<>();
        for(int i = 0; i< windows.size(); i++){
            windowsArr.add(windows.get(Integer.toString(i+1)));
        }
        for(int i = 0; i < playerNumber; i++){
            do{
                index =1+randomInt.nextInt(11);
            }while(nums[index-1]);
            nums[index]=true;
            int personalGoalIndex = randomInt.nextInt(windowsArr.size());
            while(personalGoalIndex == lastIndex){
                personalGoalIndex = randomInt.nextInt(windowsArr.size());
            }
            lastIndex = personalGoalIndex;
            this.tablePositionList.add(i, new TablePosition(usernames.get(i), new PersonalGoal(windowsArr.get(personalGoalIndex), personalGoalIndex+1), new Bookshelf()));
        }

        index = randomInt.nextInt(playerNumber);
        tablePositionList.get(index).setFirstPosition(true);
        firstPlayer = tablePositionList.get(index).getPlayer().getUsername();
        currentPosition = tablePositionList.get(index);

        if(index==0){
            index=playerNumber-1;
        }else{
            index--;
        }
        lastPlayer = tablePositionList.get(index).getPlayer().getUsername();

        this.board = new Board(playerNumber);

        commonGoalsGenerator = new CommonGoalsGenerator(playerNumber);

        this.firstCommonGoal = commonGoalsGenerator.getFirst();
        this.secondCommonGoal = commonGoalsGenerator.getSecond();

        this.board.setBox(bag);

        this.finish = false;
    }

    /**
     * This method checks if the current player has not already achieved the two common goals and if
     * the two common goals are still available to be completed and if so
     * it calls the validate method for the common goals, otherwise it does nothing
     * @param tablePosition the table position of the player
     */
    public void validateCommonGoal(TablePosition tablePosition) {
        ScoringToken res;
        //check if player at current tableposition has already achieved the first common goal
        if(!tablePosition.getPlayer().hasCompletedFirst() && !firstCommonGoal.getCompleted()) {
            //add token to current player returned from validate
            tablePosition.getPlayer().setToken(firstCommonGoal.validate(tablePosition.getBookshelf()), firstCommonGoal.getRomanNumber());
            if (firstCommonGoal.getStack().isEmpty()) {
                firstCommonGoal.setCompleted(true);
            }
        }
        //check if player at current tableposition has already achieved the second common goal
        if(!tablePosition.getPlayer().hasCompletedSecond() && !secondCommonGoal.getCompleted()){
            //add token to current player returned from validate
            tablePosition.getPlayer().setToken(secondCommonGoal.validate(tablePosition.getBookshelf()),secondCommonGoal.getRomanNumber());
            if (secondCommonGoal.getStack().isEmpty()) {
                secondCommonGoal.setCompleted(true);
            }
        }
    }

    /**
     * This method checks how many personal goal objectives the player has achieved and assigns the relative score
     * @param tablePosition the table position of the player
     */
    public void validatePersonalGoal(TablePosition tablePosition){
        int res = tablePosition.getCurrentPGoal().validate(tablePosition.getBookshelf());
        int currentPersonalScore = tablePosition.getPlayer().getPersonalGoalScore();
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() - currentPersonalScore);
        tablePosition.getPlayer().setPersonalGoalScore((Math.max(res, currentPersonalScore)));
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() + tablePosition.getPlayer().getPersonalGoalScore());
    }

    /**
     * This method checks if the player has achieved the adjacency objective thanks to the
     * validateAdjacentRecursive() function and after that it assigns the relative score
     * @param tablePosition the table position of the player
     * @return the score assigned to the player
     */
    public int validateAdjacent(TablePosition tablePosition){
        Boolean[][] batrix = new Boolean[tablePosition.getBookshelf().getHeight()]
                [tablePosition.getBookshelf().getLength()];
        Boolean[][] occupied = new Boolean[tablePosition.getBookshelf().getHeight()]
                [tablePosition.getBookshelf().getLength()];
        for(int i=0;i<tablePosition.getBookshelf().getHeight();i++){
            for(int j=0;j<tablePosition.getBookshelf().getLength();j++){
                occupied[i][j]=false;
                batrix[i][j]=false;
            }
        }
        int res;
        res = tablePosition.getBookshelf().validateAdjacentRecursive(tablePosition, 0, 0,0, batrix, null, true, 0, occupied);
        int currentAdjacentScore = tablePosition.getPlayer().getAdjacentScore();
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() - currentAdjacentScore);
        tablePosition.getPlayer().setAdjacentScore(Math.max(res, currentAdjacentScore));
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() + tablePosition.getPlayer().getAdjacentScore());
        return res;
    }


    /**
     * This method fills the board with tiles extracted from the bag
     * @return true if the bag is empty, false otherwise
     */
    public boolean fillBoard(){
        return this.board.setBox(this.bag);
    }


    /**
     * Sets the game to the last round if a player has completed the shelf
     * @param finish true if a player has completed the shelf, false otherwise
     */
    public void setEndGame(boolean finish){
        this.finish = finish;
    }

    /**
     * @return true if a player has completed the shelf, false otherwise
     */
    public boolean getEndGame(){
        return this.finish;
    }

    /**
     * This method change the current position of the player to the next one in the table position list
     */
    public void setCurrentPosition(){
        int newCurrentIndex=tablePositionList.indexOf(currentPosition)+1;
        if(newCurrentIndex==tablePositionList.size()){
            newCurrentIndex=0;
        }
        currentPosition=tablePositionList.get(newCurrentIndex);
    }

    /**
     * @return A list of all the players in the game
     */
    public List<Player> getListPlayer(){
        List<Player> list = new ArrayList<Player>();
        for(int i=0; i<tablePositionList.size(); i++){
            list.add(tablePositionList.get(i).getPlayer());
        }
        return list;
    }

    /**
     * @return A map of all the players in the game with their nickname as key
     */
    public Map<String, Player> getPlayerByNickname() {
        Map<String,Player> players = new HashMap<>();
        for(int i=0; i<tablePositionList.size(); i++){
            players.put(tablePositionList.get(i).getPlayer().getUsername(), tablePositionList.get(i).getPlayer());
        }
        return players;
    }

    /**
     * @return The username of the first player in the table position list
     */
    public String getFirstPlayer(){
        return firstPlayer;
    }

    /**
     * @return A list of all the bookshelves in the game
     */
    public List<Bookshelf> getListBookshelf(){
        List<Bookshelf> list= new ArrayList<Bookshelf>();
        for(int i=0;i<tablePositionList.size();i++){
            list.add(tablePositionList.get(i).getBookshelf());
        }
        return list;
    }


    /**
     * @return The current TablePosition of the player who is playing
     */
    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    /**
     * @return The current Bookshelf of the player who is playing
     */
    public Bookshelf getCurrentBookshelf(){
        return currentPosition.getBookshelf();
    }

    /**
     * @return The board object of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return The tiles drawn by the current player during its turn
     */
    public ArrayList<ItemTiles> getPickedCards(){
        return currentPosition.getPlayer().getPickedCards();
    }

    /**
     * @return The first common goal of the game
     */
    public CommonGoal getFirstCommonGoal() {
        return firstCommonGoal;
    }

    /**
     * @return The second common goal of the game
     */
    public CommonGoal getSecondCommonGoal() {
        return secondCommonGoal;
    }

    /**
     * This method notify al the observers of the game with the event passed as argument
     * and set the changed flag to true to notify the observers that a change has occurred
     * @param arg The event to be notified to the observers
     */
    public void setChangedAndNotifyObservers(Event arg) {
        setChanged();
        notifyObservers(new Message(arg));
    }


    /**
     * This method sets the winner of the game by checking the score of all the players
     */
    public void setWinner() {
        int score=0;
        String winner = "init"; //initialize to avoid this.winner error *can't assign a nullable variable

        for(TablePosition o: tablePositionList){
            if(o.getPlayer().getScore()>score && o.getPlayer().getStatus()){
                winner=o.getPlayer().getUsername();
                score=o.getPlayer().getScore();
            }
        }
        this.winner= winner;
    }

    /**
     * This method sets the winner of the game forcibly by providing the username of the player
     * @param username The username of the player to be set as winner
     */
    public void setForcedWinner(String username){
        this.winner=username;
    }

    /**
     * @return The username of the winner of the game
     */
    public String getWinner() {
        return winner;
    }

    /**
     * This method is called at the end of every turn and it calls the validation methods for
     * the game objectives, it checks a player has completed the shelf and eventually it sets the
     * new current player
     */
    public void changeCurrentPosition(){
        validateAdjacent(getCurrentPosition());
        validateCommonGoal(getCurrentPosition());
        validatePersonalGoal(getCurrentPosition());
        if(getCurrentPosition().getPlayer().getUsername().equals(getFirstFinisher())){
            getCurrentPosition().getPlayer().setScore(getCurrentPosition().getPlayer().getScore()+1);
            setEndGameToken(true,getCurrentPosition().getPlayer().getUsername());
        }
        if(getCurrentPosition().getPlayer().getUsername().equals(getFirstFinisher()) && getCurrentPosition().getPlayer().getUsername().equals(getLastPlayer())){
            finalFlow=1;
        }else {
            setCurrentPosition();
        }
    }

    /**
     * This method is called at the end of the game, and it checks if a player
     * has completed the shelf setting him/her as the first finisher
     */
    public void checkFinalControl(){
        if(getCurrentPosition().getBookshelf().isFull()&& !getEndGame()){
            setEndGame(true);
            setFirstFinisher(getCurrentPosition().getPlayer().getUsername());
        }
    }

    /**
     * @return The username of the last player in the table position list
     */
    public String getLastPlayer(){
        return lastPlayer;
    }

    /**
     * This method is called at the end of the game and it sets the first finisher of the shelf
     * @param b The boolean value to be set as end game token
     * @param username The username of the player who completed the shelf
     */
    private void setEndGameToken(boolean b, String username) {
        this.endGameToken=b;
        this.shelfCompletedBy=username;
    }

    /**
     * @return The boolean value of the end game token
     */
    public boolean getEndGameToken(){
        return this.endGameToken;
    }

    /**
     * @return The username of the first player who completed the shelf
     */
    public String getShelfCompletedBy() {
        return shelfCompletedBy;
    }


    /**
     * This method checks if the selected column has enough space to insert the picked tiles
     * @param columnNumber The column number to be checked
     * @return True if the column has enough space to insert the picked tiles, false otherwise
     */
    public boolean checkInsert(int columnNumber){
        try {
            getCurrentPosition().getBookshelf().setChoosenColumn(columnNumber);
            if((int)(getCurrentPosition().getBookshelf().getColumnsSize().get(columnNumber)) > (6-getCurrentPosition().getPlayer().getPickedCards().size())){
                return false;
            }
            return true;
        } catch (Exception e) {
            //column not correct
            return false;
        }
    }

    /**
     * This method checks if the board has just single tiles in it and if so it returns true
     * so that the board will be refilled.
     * @return True if the board is empty, false otherwise
     */
    public boolean checkBoardEmpty() {
        for(int i=0;i<getBoard().getMaxHeight();i++){
            for(int j=0;j<getBoard().getMaxLength();j++){
                if(getBoard().getBox(i,j).getValid()&&getBoard().getBox(i,j).getItemContained()!=null){
                    if(i>0 && getBoard().getBox(i-1,j).getValid() && getBoard().getBox(i-1,j).getItemContained()!=null){
                        return false;
                    }
                    if(i<getBoard().getMaxHeight()-1 &&getBoard().getBox(i+1,j).getValid()&&getBoard().getBox(i+1,j).getItemContained()!=null){
                        return false;
                    }
                    if(j>0&&getBoard().getBox(i,j-1).getValid()&&getBoard().getBox(i,j-1).getItemContained()!=null){
                        return false;
                    }
                    if(j<getBoard().getMaxLength()-1&&getBoard().getBox(i,j+1).getValid()&&getBoard().getBox(i,j+1).getItemContained()!=null){
                        return false;
                    }

                }
            }
        }
        return true;
    }

    /**
     *method that checks the board's coordinates chosen by the player and returns true if they are valid:
     * -the coordinates are on the board
     * -check if chosen tiles are on the same row
     * -otherwise it checks if chosen tiles are on the same column
     * -check if the chosen tiles are adjacent
     * -check if every tile has at least free side
     * @param coords one,two or three pairs of coordinates based on the player choice
     * [[int x1,int y1],[int x2,int y2],[int x3 ,int y3]]
     *
     * @return true if the coordinates are valid, false otherwise
     */
    public boolean checkDraw(ArrayList<ArrayList<Integer>> coords){

        boolean notValid = false;
        ArrayList<Integer> x = new ArrayList<Integer>(3);
        ArrayList<Integer> y = new ArrayList<Integer>(3);
        ArrayList<ItemTiles> validCards = new ArrayList<ItemTiles>();
        for(int i=0;i<coords.size();i++){
            x.add(coords.get(i).get(0));
            y.add(coords.get(i).get(1));
            if(coords.get(0).get(0) != coords.get(i).get(0)) {
                notValid = true;
            }
        }
        if(notValid){
            for(int i=1;i<coords.size();i++){
                if(coords.get(0).get(1) != coords.get(i).get(1))
                    return false;
            }
        }
        Collections.sort(x);
        Collections.sort(y);
        notValid=false;
        for(int i=0;i<x.size()-1;i++){
            if(x.get(i+1)-x.get(i)!=1)
                notValid=true;
        }
        if(notValid){
            for(int i=0;i<y.size()-1;i++){
                if(y.get(i+1)-y.get(i)!=1)
                    return false;
            }
        }
        for(ArrayList<Integer> elem : coords){
            if(!getBoard().getBox(elem.get(0),elem.get(1)).getValid()) {
                return false;
            }else{
                try{
                    if(elem.get(0)==0||elem.get(0)==getBoard().getMaxHeight()-1||elem.get(1)==0||elem.get(1)==getBoard().getMaxLength()-1) {

                    }
                    else if((getBoard().getBox(elem.get(0)+1,elem.get(1)).getItemContained()!=null )&&
                            getBoard().getBox(elem.get(0)-1,elem.get(1)).getItemContained()!=null &&
                            getBoard().getBox(elem.get(0),elem.get(1)+1).getItemContained()!=null &&
                            getBoard().getBox(elem.get(0),elem.get(1)-1).getItemContained()!=null){
                        return false;
                    }
                }catch (IndexOutOfBoundsException e){
                    return false;
                }

            }
        }
        return true;
    }

    /**This method returns the name of the player in the ranking position passed as parameter
     * @param position the position of the player in the ranking
     * @return the name of the player in the position passed as parameter
     */
    public String getPlayerNameByRanking(int position) {
        List <Player> finalResult = new ArrayList<>();
        for(TablePosition t : tablePositionList){
            if(t.getPlayer().getStatus()){
                finalResult.add(t.getPlayer());
            }
        }
        finalResult.sort(Comparator.comparing(Player::getScore));
        Collections.reverse(finalResult);
        if(position>=finalResult.size())
            return null;
        return finalResult.get(position).getUsername();
    }

    /**
     * This method sets the current player
     * @param id the id of the player
     */
    public void setCurrentPlayer(String id){
        for(TablePosition t:tablePositionList){
            if(t.getPlayer().getUsername().equals(id)){
                this.currentPosition=t;
                break;
            }
        }
    }

    /**
     * This method returns the final flow of the match based on which player has finished first
     * @return the final flow of the match
     */
    public int getFinalFlow(){
        return finalFlow;
    }

    /**
     * This method sets the final flow of the match to 3 when all players have disconnected
     */
    public void setFinalForcedFlow(){
        finalFlow=3;
    }

    /**
     * This method sets the final flow of the match to 4
     */
    public void setRegularFlow(){
        finalFlow=4;
    }


    public void setFirstFinisher(String id){
        this.firstFinisher=id;
    }
    public String getFirstFinisher(){
        return this.firstFinisher;
    }

}
