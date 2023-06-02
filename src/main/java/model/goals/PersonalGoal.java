package model.goals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import model.Bookshelf;
import model.ScoringToken;
import model.Type;
import view.Color;

import java.security.GeneralSecurityException;
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

    public int getDone(){
        return this.done;
    }

    //todo creare una exception se done è maggiore di 6 o minore di 0
    public int getScore() throws IndexOutOfBoundsException {
        if(this.done < Integer.parseInt(prop.getProperty("score.minNum")) ||
                this.done > Integer.parseInt(prop.getProperty("score.maxNum"))) throw new IndexOutOfBoundsException();
        return Integer.parseInt(prop.getProperty("score."+done+"goal"));
    }

    public int validate(Bookshelf bookshelf) {
        Map<String, String> elem;
        this.done = 0;
        for (String e : windows.keySet()) {
            elem = (Map<String, String>) windows.get(e);
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

    public int getId() {
        return id;
    }

    public Map<String, Map<String, String>> getWindows() {
        return windows;
    }

}
