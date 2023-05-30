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

public class Game extends Observable<Event> implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean finish;
    private final int playerNumber;
    private TablePosition currentPosition;
    private final String firstPlayer;
    private final Bag bag;
    private final CommonGoal firstCommonGoal;
    private final CommonGoal secondCommonGoal;
    private final List<TablePosition> tablePositionList;
    private final Board board;

    private String winner;


    //private   <Map<Type , Pair<Integer , Integer>>> PersonalGoalDeck;
    //private   <Map<Type , Pair<Integer , Integer>>> PersonalGoalDeck;


    // The Game constructor:
    // -creates a new game taking the number of players and their nicknames as parameters
    // -initializes all the table positions with their relative players
    // -initializes all the game attributes relative to the game and the board
    // -randomly assigns player's personal goal and chooses two game's common goals



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
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
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
        this.tablePositionList = new ArrayList<TablePosition>();
        ArrayList<Map<String, Map<String, String>>> windowsArr = new ArrayList<Map<String, Map<String, String>>>();
        for(int i = 0; i< windows.size(); i++){
            windowsArr.add(windows.get(Integer.toString(i+1)));
        }
        for(int i = 0; i < playerNumber; i++){
            do{
                index =1+randomInt.nextInt(11);
            }while(nums[index-1]);
            nums[index]=true;
            int personalGoalIndex = randomInt.nextInt(windowsArr.size());
            this.tablePositionList.add(i, new TablePosition(usernames.get(i), new PersonalGoal(windowsArr.remove(personalGoalIndex), personalGoalIndex+1), new Bookshelf()));
        }

        index = randomInt.nextInt(playerNumber);
        tablePositionList.get(index).setFirstPosition(true);
        firstPlayer = tablePositionList.get(index).getPlayer().getUsername();
        currentPosition = tablePositionList.get(index);

        this.board = new Board(playerNumber);

        commonGoalsGenerator = new CommonGoalsGenerator(playerNumber);

        this.firstCommonGoal = commonGoalsGenerator.getFirst();
        this.secondCommonGoal = commonGoalsGenerator.getSecond();

        this.board.setBox(bag);

        this.finish = false;
    }
    public void validateCommonGoal(TablePosition tablePosition) {
        ScoringToken res;
        //check if player at current tableposition has already achieved the first commmon goal
        if(tablePosition.getPlayer().getToken(firstCommonGoal.getRomanNumber()-1) == null && !firstCommonGoal.getCompleted()){
            //add token to current player returned from validate
            tablePosition.getPlayer().setToken(firstCommonGoal.validate(tablePosition.getBookshelf()));
        }
        //check if player at current tableposition has already achieved the second commmon goal
        if(tablePosition.getPlayer().getToken(secondCommonGoal.getRomanNumber()-1) == null && !secondCommonGoal.getCompleted()){
            //add token to current player returned from validate
            tablePosition.getPlayer().setToken(secondCommonGoal.validate(tablePosition.getBookshelf()));
        }
    }
    public void validatePersonalGoal(TablePosition tablePosition){
        int res = tablePosition.getCurrentPGoal().validate(tablePosition.getBookshelf());
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() + res);
    }

    //validateAdjacent(position,0,0,0,batrix di false,null,true,0,occupied di false)
//void
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
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() + res );
        return res;
    }


    public boolean fillBoard(){
        return this.board.setBox(this.bag);
    }

    // todo chiamata isFull della bookshelf che a sua volta chiama setEndGame
    // todo domanda gestione turni, eventuale multithreading come listener
    public void setEndGame(boolean finish){
        this.finish = finish;
    }
    public boolean getEndGame(){
        return this.finish;
    }
    public void setCurrentPosition(){
        int newCurrentIndex=tablePositionList.indexOf(currentPosition)+1;
        if(newCurrentIndex==tablePositionList.size()){
            newCurrentIndex=0;
        }
        currentPosition=tablePositionList.get(newCurrentIndex);
    }

    public List<Player> getListPlayer(){
        List<Player> list = new ArrayList<Player>();
        for(int i=0; i<tablePositionList.size(); i++){
            list.add(tablePositionList.get(i).getPlayer());
        }
        return list;
    }


    public List<Bookshelf> getListBookshelf(){
        List<Bookshelf> list= new ArrayList<Bookshelf>();
        for(int i=0;i<tablePositionList.size();i++){
            list.add(tablePositionList.get(i).getBookshelf());
        }
        return list;
    }

    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    public Bookshelf getCurrentBookshelf(){
        return currentPosition.getBookshelf();
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<ItemTiles> getPickedCards(){
        return currentPosition.getPlayer().getPickedCards();
    }

    public CommonGoal getFirstCommonGoal() {
        return firstCommonGoal;
    }

    public CommonGoal getSecondCommonGoal() {
        return secondCommonGoal;
    }

    //TODO notify...
    public void setChangedAndNotifyObservers(Event arg) {
        setChanged();
        notifyObservers(new Message(arg));
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setWinner() {
        int score=0;
        String winner = "init"; //initialize to avoid this.winner error *can't assign a nullable variable

        for(TablePosition o: tablePositionList){
            if(o.getPlayer().getScore()>score){
                winner=o.getPlayer().getUsername();
                score=o.getPlayer().getScore();
            }
        }
        this.winner= winner;
    }

    public void setForcedWinner(String username){
        this.winner=username;
    }

    public String getWinner() {
        return winner;
    }

    public void changeCurrentPosition(){
        if(getCurrentPosition().getBookshelf().isFull()){
            setEndGame(true);
            getCurrentPosition().getPlayer().setScore(getCurrentPosition().getPlayer().getScore()+1);
        }
        validateAdjacent(getCurrentPosition());
        validateCommonGoal(getCurrentPosition());
        validatePersonalGoal(getCurrentPosition());
        setCurrentPosition();
    }

    /*
     * check if there is a column in the shelf with enough space to insert all the chosen tiles
     * if not return false
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
    public boolean checkBoardEmpty() {
        boolean result=true;
        for(int i=0;i<getBoard().getMaxHeight()&&result;i++){
            for(int j=0;j<getBoard().getMaxLength()&&result;j++){
                if(getBoard().getBox(i,j).getValid()&&getBoard().getBox(i,j).getItemContained()!=null){
                    if(i>0&&getBoard().getBox(i-1,j).getValid()&&getBoard().getBox(i-1,j).getItemContained()!=null){
                        result=false;
                    }
                    if(i<getBoard().getMaxHeight()-1 &&getBoard().getBox(i+1,j).getValid()&&getBoard().getBox(i+1,j).getItemContained()!=null){
                        result=false;
                    }
                    if(j>0&&getBoard().getBox(i,j-1).getValid()&&getBoard().getBox(i,j-1).getItemContained()!=null){
                        result=false;
                    }
                    if(j<getBoard().getMaxLength()-1&&getBoard().getBox(i,j+1).getValid()&&getBoard().getBox(i,j+1).getItemContained()!=null){
                        result=false;
                    }

                }
            }
        }
        return result;
    }

    /*
     *method that checks the board's coordinates chosen by the
     *player from where to pick the tiles: coords should contain
     * one , two or three pairs of coordinates based on the player choice
     * [[int x1,int y1],[int x2,int y2],[int x3 ,int y3]]
     */
    public boolean checkDraw(ArrayList<ArrayList<Integer>> coords){
        /*
         *check if chosen tiles are on the same row
         */
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
            /*
             *check if chosen tiles are on the same column
             */
            for(int i=1;i<coords.size();i++){
                if(coords.get(0).get(1) != coords.get(i).get(1))
                    return false;
            }
        }
        /*
         *check if chosen tiles are adjacent
         */
        //ArrayList<Integer> a=x.stream().sorted().collect(Collectors.toList(Integer));
        //List b=y.stream().sorted().collect(Collectors.toList());
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
        /*
         *check if chosen tiles have at least one free side
         */
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
    public void setForcedCurrentPosition(String user){
        for(TablePosition t :tablePositionList){
            if(t.getPlayer().getUsername().equals(user)){
                this.currentPosition=t;
            }
        }
    }
}
