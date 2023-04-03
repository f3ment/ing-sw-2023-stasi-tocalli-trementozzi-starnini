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
        final CommonGoalsGenerator commonGoalsGenerator;
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

        commonGoalsGenerator = new CommonGoalsGenerator(playerNumber);

        this.firstCommonGoal = commonGoalsGenerator.getFirst();
        this.secondCommonGoal = commonGoalsGenerator.getSecond();

        this.board.setBox(bag);

        this.finish = false;
    }
    public void validateCommonGoal(TablePosition tablePosition) throws Exception {
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
    public int validateAdjacent(TablePosition tablePosition) throws Exception{
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
        res = validateAdjacentRecursive(tablePosition, 0, 0,0, batrix, null, true, 0, occupied);
        tablePosition.getPlayer().setScore(tablePosition.getPlayer().getScore() + res );
        return res;
    }

    //TODO move validateAdjacentRecursive to bookshelf
    public int validateAdjacentRecursive(TablePosition tablePosition,int i,int j,int count,Boolean[][] batrix,Type type,boolean starting,int score,Boolean[][] occupied) throws Exception{
        Bookshelf validateshelf= tablePosition.getBookshelf();
        try {
            if (!batrix[i][j]  && !starting && !occupied[i][j]) {
                if (validateshelf.getItem(i, j).getType().equals(type)) {
                    count++;
                    occupied[i][j] = true;
                    if (i < validateshelf.getHeight() - 1) {
                        count = validateAdjacentRecursive(tablePosition, i + 1, j, count, batrix, type, false, score, occupied);
                    }
                    if (j < validateshelf.getLength() - 1) {
                        count = validateAdjacentRecursive(tablePosition, i, j + 1, count, batrix, type, false, score, occupied);
                    }
                    if (i >0) {
                        count = validateAdjacentRecursive(tablePosition, i -1, j, count, batrix, type, false, score, occupied);
                    }
                    if (j >0) {
                        count = validateAdjacentRecursive(tablePosition, i, j -1, count, batrix, type, false, score, occupied);
                    }
                    if (count >= Integer.parseInt(
                            prop.getProperty("score.MinlimitParameter"))) {
                        batrix[i][j] = true;
                    }else {
                        occupied[i][j] = false;
                    }
                    return count;
                } else {
                    return count;
                }
            } else if (starting==true&&batrix[i][j]==false&&occupied[i][j]==false) {
                occupied[i][j] = true;
                count=1;
                if (i < validateshelf.getHeight() - 1) {
                    count = validateAdjacentRecursive(tablePosition, i + 1, j, count, batrix, validateshelf.getItem(i, j).getType(), false, score, occupied);
                }
                if (j < validateshelf.getLength() - 1) {
                    count = validateAdjacentRecursive(tablePosition, i, j + 1, count, batrix, validateshelf.getItem(i, j).getType(), false, score, occupied);
                }
                if (i >0) {
                    count = validateAdjacentRecursive(tablePosition, i -1, j, count, batrix, type, false, score, occupied);
                }
                if (j >0) {
                    count = validateAdjacentRecursive(tablePosition, i, j -1, count, batrix, type, false, score, occupied);
                }

                if (count >= Integer.parseInt(
                        prop.getProperty("score.MinlimitParameter"))) {
                    //Check if count is higher than limit value
                    if (count > Integer.parseInt(
                            prop.getProperty("score.MaxlimitParameter")
                    )) count = Integer.parseInt(
                            prop.getProperty("score.MaxlimitParameter")
                    );
                    score += Integer.parseInt(prop.getProperty("score.adj"+count));
                    batrix[i][j] = true;
                    //System.out.println(validateshelf.getItem(i, j).getType());
                    //System.out.println(count);
                }else{
                     occupied[i][j] = false;
                }
                /*if(count==3){
                    score+=2;
                } else if (count==4) {
                    score+=3;
                } else if (count==5) {
                    score+=5;
                } else if (count>=6) {
                    score+=8;
                }
                if(count>=3){
                    batrix[i][j]=true;
                    System.out.println(validateshelf.getItem(i, j).getType());
                    System.out.println(count);
                }else{
                    occupied[i][j] = false;
                }*/
                count = 0;
                if (j < validateshelf.getLength() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i, j + 1, count, batrix, null, true, score, occupied);
                } else if (i < validateshelf.getHeight() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i + 1, 0, count, batrix, null, true, score, occupied);
                }
                return score;
            } else if (batrix[i][j]==true && starting==true) {
                if (j < validateshelf.getLength() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i, j + 1, 0, batrix, null, true, score, occupied);
                } else if (i < validateshelf.getHeight() - 1) {
                    score = validateAdjacentRecursive(tablePosition, i + 1, 0, 0, batrix, null, true, score, occupied);
                }
                return score;

            } else if(batrix[i][j]==true && starting==false) {
                return count;
            }else if (occupied[i][j]==true&&starting==false) {
                return count;
            }
        }catch(Exception e){
                if(starting==false){
                    return count;
                }else {
                    if (j < validateshelf.getLength() - 1) {
                        score = validateAdjacentRecursive(tablePosition, i, j + 1, 0, batrix, null, true, score, occupied);
                    } else if (i < validateshelf.getHeight() - 1) {
                        score = validateAdjacentRecursive(tablePosition, i + 1, 0, 0, batrix, null, true, score, occupied);
                    }
                    return score;
                }

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

    /*
    * Check if the cards chosen by a player are valid to be drawn
    */
    public boolean CheckDrawnCards(){
        return board.CheckChoice(currentPosition.getPlayer().getCardsToCheck());
    }

    public TablePosition getCurrentPosition() {
        return currentPosition;
    }

    public Board getBoard() {
        return board;
    }
}
