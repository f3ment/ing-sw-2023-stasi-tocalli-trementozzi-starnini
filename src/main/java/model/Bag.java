package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;


/**
 * Bag class
 * This class is responsible for managing the bag.
 * It contains the items.
 * It is used by Board class to create the board as a matrix of boxes; in fact, each box contains an item which is extracted from the bag.
 */
public class Bag implements Serializable {
        private static final long serialVersionUID = 1L;
        private int id;
        private final int maxCardsPerType;

        private ArrayList<ItemTiles> items; //struttura contenente Items

        /**
         * Constructor for the Bag class that initializes the items.
         * It reads the number of items from the config.properties file.
         */
        public Bag(){
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

                id = Integer.parseInt(prop.getProperty("items.diffNumbers"));
                maxCardsPerType = Integer.parseInt(prop.getProperty("cards.maxNumberPerType"));

                this.items = new ArrayList<>();

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


        /**
         * Extracts a random item from the bag.
         * @return the extracted item
         * @throws NegativeArraySizeException if the bag is empty
         */
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

        /**
         * Returns the number of items left in the bag.
         * @return the number of items left in the bag
         */
        public int getLeftItems(){
                return items.size();
        }
}
