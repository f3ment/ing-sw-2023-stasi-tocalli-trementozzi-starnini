package model.goals;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import model.Bookshelf;
import java.util.Properties;

public class PersonalGoal implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int height;
    private final int length;
    private final int id;
    /*
     * Apertura file di configurazione
     * */
    String configFilePath = "./src/main/resources/config.properties";
    Properties prop = new Properties();



    private Map<String, Map<String, String>> windows;
    private int done;

    /**
     * Constructor of the class PersonalGoal that initialize the windows of the goal and the specific id
     * @param windows which is the map of the windows of the goal. The key is the type of the item and for each type there is a map with the coordinates of the item
     * @param id which is the id of the goal
     */
    public PersonalGoal(Map<String, Map<String, String>> windows, int id){
        FileInputStream ip;

        {
            try {
                ip = new FileInputStream(configFilePath);
                prop.load(ip);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.height = Integer.parseInt(prop.getProperty("bookshelf.height"));
        this.length = Integer.parseInt(prop.getProperty("bookshelf.width"));
        this.windows = windows;
        this.done = 0;
        this.id = id;
    }

    /**
     * Method that returns how many items are in the correct position
     * @return the number of items in the correct position
     */
    public int getDone(){
        return this.done;
    }

    /**
     * Method that returns the score of the goal based on the number of items in the correct position by looking at the configuration file
     * @return the score of the goal
     * @throws IndexOutOfBoundsException if the number of items in the correct position is not between the minimum and the maximum number of items
     */
    public int getScore() throws IndexOutOfBoundsException {
        if(this.done < Integer.parseInt(prop.getProperty("score.minNum")) ||
                this.done > Integer.parseInt(prop.getProperty("score.maxNum"))) throw new IndexOutOfBoundsException();
        return Integer.parseInt(prop.getProperty("score."+done+"goal"));
    }

    /**
     * Method that verifies if the goal is satisfied for the bookshelf passed as parameter.
     * @param bookshelf which is the bookshelf to check
     * @return the score of the goal
     */
    public int validate(Bookshelf bookshelf) {
        Map<String, String> elem;
        this.done = 0;
        for (String e : windows.keySet()) {
            elem = windows.get(e);
            try{
                if (bookshelf.getItem((Integer) Integer.valueOf(elem.get("X")), (Integer) Integer.valueOf(elem.get("Y"))).getType().toString().equals(e)) {
                    done++;
                }
            }catch (Exception ex){
                continue;
            }

        }
        System.out.println("done: "+done);
        return getScore();
    }

    /**
     * Method that returns the id of the goal
     * @return the id of the goal
     */
    public int getId() {
        return id;
    }

    /**
     * Method that returns the windows Map of the goal
     * @return the windows Map of the goal
     */
    public Map<String, Map<String, String>> getWindows() {
        return windows;
    }

}
