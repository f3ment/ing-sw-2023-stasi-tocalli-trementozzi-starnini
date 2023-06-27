package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

/**
 * This class represents the bag of the game. It contains all the items that will be used during the game.
 */
public class Bag implements Serializable {
        private static final long serialVersionUID = 1L;
        private int id;
        private final int maxCardsPerType;

        private ArrayList<ItemTiles> items; //struttura contenente Items

        /**
         * Constructor of the class Bag. It creates a new bag with all the items that will be used during the game.
         */
        public Bag(){

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
         * This method extracts a random item from the bag.
         * @return the item extracted from the bag.
         * @throws NegativeArraySizeException if the bag is empty.
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
         * This method returns the number of items left in the bag.
         * @return the number of items left in the bag.
         */
        public int getLeftItems(){
                return items.size();
        }
}
