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

    /*
     * Apertura file di configurazione
     * */
    String configFilePath = "./src/main/resources/config.properties";
    Properties prop = new Properties();



    private Map<String, Map<String, String>> windows;
    private int done;
    public PersonalGoal(Map<String, Map<String, String>> windows){
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
        return getScore();

    }

    public Map<String, Map<String, String>> getWindows() {
        return windows;
    }

    public void showPersonalGoal(){
        Map<String, String> elem;
        boolean found;
        for (int i = 0; i < height; i++){
            System.out.print(Color.WHITE_BRIGHT + "|" + Color.RESET);
            for(int j=0; j < length; j++){
                found = false;
                for (String e : windows.keySet()) {
                    elem = (Map<String, String>) windows.get(e);
                    if(Integer.valueOf(elem.get("X")).equals(i) && Integer.valueOf(elem.get("Y")).equals(j)){
                        found = true;
                        switch (e) {
                            case "CATS" :
                                System.out.print(" " + Type.CATS.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "GAMES" :
                                System.out.print(" " + Type.GAMES.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "PLANTS" :
                                System.out.print(" " + Type.PLANTS.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "BOOKS" :
                                System.out.print(" " + Type.BOOKS.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "FRAMES" :
                                System.out.print(" " + Type.FRAMES.getColor() + "▓▓" + Color.RESET);
                                break;
                            case "TROPHIES" :
                                System.out.print(" " + Type.TROPHIES.getColor() + "▓▓" + Color.RESET);
                                break;
                        }
                    }
                }
                if(!found){
                    System.out.print(" " + Color.BLACK + "▓▓" + Color.RESET);
                }
            }
            System.out.println(Color.WHITE_BRIGHT + " |" +Color.RESET);
        }
    }
}
