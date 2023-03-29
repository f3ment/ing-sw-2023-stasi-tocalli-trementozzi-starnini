package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.board.Board;
import model.goals.CommonGoal;
import model.goals.PersonalGoal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    private boolean finish;
    private final int playerNumber;
    private TablePosition currentPosition;
    private final Player firstPlayer;
    private final Bag bag;
    private final CommonGoal firstCommonGoal;
    private final CommonGoal secondCommonGoal;
    private final List<TablePosition> tablePositionList;
    private Board board;


    //private   <Map<Type , Pair<Integer , Integer>>> PersonalGoalDeck;
    //private   <Map<Type , Pair<Integer , Integer>>> PersonalGoalDeck;


    // The Game constructor:
    // -creates a new game taking the number of players and their nicknames as parameters
    // -initializes all the table positions with their relative players
    // -initializes all the game attributes relative to the game and the board
    // -randomly assigns player's personal goal and chooses two game's common goals




    /*
     * Apertura file di configurazione
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

    public Game(ArrayList<String> usernames) throws IOException {
        Random randomInt = new Random();
        int index;
        boolean[] nums= new boolean[12];
        ArrayList<CommonGoal> commonGoals;

        Gson gson = new Gson();



        this.playerNumber = usernames.size();
        this.bag = new Bag();

        //initializes the personal goal deck with 12 cards
        //every card is an hashmap of 6 couplets of key (Type) and value (pair of coordinates)
        //PersonalGoalDeck = new ArrayList<Map<Type , Pair<Integer , Integer>>>();

        // 1. JSON file to Java object
        Map<String, Map<String, Map<String, String>>> windows = gson.fromJson(new FileReader("./src/main/resources/personalGoals.json"),
                new TypeToken<Map<String, Map<String, Map<String, String>>>>() {}.getType());
        //Best Practice
        //object.forEach((key, value) -> value.values().forEach(i -> System.out.println(i.get("X"))));

        // Ours
        /*object.entrySet().stream().forEach(e-> {
            e.getValue().entrySet().stream().map(
                    k -> k.getValue()).forEach(i -> System.out.println(i.get("X")));
        });*/

        this.tablePositionList = new ArrayList<TablePosition>();
        for(int i = 0; i < playerNumber; i++){
            do{
                index =1+randomInt.nextInt(11);
            }while(nums[index-1]);
            nums[index]=true;
            index =1+randomInt.nextInt(12);
            this.tablePositionList.add(i, new TablePosition(usernames.get(i), new PersonalGoal(windows.remove(Integer.toString(index))), new Bookshelf()));
        }

        index = randomInt.nextInt(playerNumber);
        tablePositionList.get(index).setFirstPosition(true);
        firstPlayer = tablePositionList.get(index).getPlayer();
        currentPosition = tablePositionList.get(index);

        this.board = new BoardGenerator(playerNumber).getBoard();

        commonGoals = new CommonGoalsGenerator(playerNumber).getCommonGoals();

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
    public void validatePersonalGoal(TablePosition tablePosition){
        int res = tablePosition.getCurrentPGoal().validate(tablePosition.getBookshelf());
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() + res);
    }

    //validateAdjacent(position,0,0,0,batrix di false,null,true,0,occupied di false)

    public void validateAdjacent(TablePosition tablePosition) throws Exception{
        boolean[][] batrix = new boolean[tablePosition.getBookshelf().getHeight()]
                [tablePosition.getBookshelf().getLength()];
        boolean[][] occupied = new boolean[tablePosition.getBookshelf().getHeight()]
                [tablePosition.getBookshelf().getLength()];
        int res;
        res = validateAdjacentRecursive(tablePosition, 0, 0,0, batrix, null, true, 0, occupied);
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() + res );
    }

    public int validateAdjacentRecursive(TablePosition tablePosition,int i,int j,int count,boolean[][] batrix,Type type,boolean starting,int score,boolean[][] occupied) throws Exception{
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
                if(count>=Integer.parseInt(
                        prop.getProperty("score.MinlimitParameter"))){
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

            if(count >= Integer.parseInt(
                    prop.getProperty("score.MinlimitParameter"))){
                /* Check if count is higher than limit value */
                if(count > Integer.parseInt(
                        prop.getProperty("score.MaxlimitParameter")
                )) count = Integer.parseInt(
                        prop.getProperty("score.MaxlimitParameter")
                );
                score += Integer.parseInt(prop.getProperty("score.adj"+count));
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
        }else if(batrix[i][j] || validateshelf.getItem(i,j)==null){
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

    // todo chiamata isFull della bookshelf che a sua volta chiama setEndGame
    // todo domanda gestione turni, eventuale multithreading come listener
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

    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    public Board getBoard() {
        return board;
    }
}
