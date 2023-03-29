package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class Bag {

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
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }

        private int id = Integer.parseInt(prop.getProperty("items.diffNumbers"));
        private int maxCardsPerType = Integer.parseInt(prop.getProperty("cards.maxNumberPerType"));

        private ArrayList<ItemTiles> items; //struttura contenente Items
        public Bag(){
                this.items = new ArrayList<ItemTiles>();

                for(int i =0 ; i <maxCardsPerType ; i++){
                        items.add(new ItemTiles(Type.CATS, i%id));
                }
                for(int i =0 ; i <maxCardsPerType ; i++){
                        items.add(new ItemTiles(Type.BOOKS, i%id));
                }
                for(int i =0 ; i <maxCardsPerType ; i++){
                        items.add(new ItemTiles(Type.GAMES, i%id));
                }
                for(int i =0 ; i <maxCardsPerType ; i++){
                        items.add(new ItemTiles(Type.FRAMES, i%id));
                }
                for(int i =0 ; i <maxCardsPerType ; i++){
                        items.add(new ItemTiles(Type.PLANTS, i%id));
                }
                for(int i =0 ; i <maxCardsPerType ; i++){
                        items.add(new ItemTiles(Type.TROPHIES, i%id));
                }
        }

        //estrazione random di un ItemTiles con conseguente rimozione dalla struttura dati
        public ItemTiles extract() throws NegativeArraySizeException{
                int randomIndex;
                Random rand = new Random();
                if(items.size() == 0){
                        throw new NegativeArraySizeException();
                }else{
                        randomIndex = rand.nextInt(items.size());
                        return items.remove(randomIndex);
                }
        }

        //returns the number of items left in the bag
        public int getLeftItems(){
                return items.size();
        }
}
