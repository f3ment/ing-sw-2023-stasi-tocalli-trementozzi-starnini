package model.goals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.sun.tools.javac.util.Pair;
import model.Bookshelf;
import model.ScoringToken;
import model.Type;

import java.security.GeneralSecurityException;
import java.util.Properties;

//todo implement windows
public class PersonalGoal {

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
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Map<String, String>> windows;
    private int done;
    public PersonalGoal(Map<String, Map<String, String>> windows){
        /*todo
            if a goal is obtained, done will be incremented by one, until 6
            in this way at the end of the game we know how many goals has been achieved.
        */
        this.windows = windows;
        this.done = 0;
    }

    public int getDone(){
        return this.done;
    }

    //creare una exception se done è maggiore di 6 o minore di 0
    public int getScore() throws IndexOutOfBoundsException {
        if(this.done < Integer.parseInt(prop.getProperty("score.minNum")) ||
                this.done > Integer.parseInt(prop.getProperty("score.maxNum"))) throw new IndexOutOfBoundsException();
        return Integer.parseInt(prop.getProperty("score."+done+"goal"));
    }

    public int validate(Bookshelf bookshelf){
        Map<String, String> elem;
        for(String e : windows.keySet()){
            elem = (Map<String, String>) windows.get(e);
            if(bookshelf.getItem((Integer) Integer.valueOf(elem.get("X")), (Integer) Integer.valueOf(elem.get("Y"))).getType().toString().equals(e)){
                done++;
            }
        }
        return getScore();
    }
}
