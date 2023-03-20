package model;

import java.util.ArrayList;
import java.util.Random;

public class Bag {
        private ArrayList<ItemTiles> items; //pila
        public Bag(){
                this.items = new ArrayList<ItemTiles>();

                for(int i =0 ; i <22 ; i++){
                        items.add(new ItemTiles(Type.CATS));
                }
                for(int i =0 ; i <22 ; i++){
                        items.add(new ItemTiles(Type.BOOKS));
                }
                for(int i =0 ; i <22 ; i++){
                        items.add(new ItemTiles(Type.GAMES));
                }
                for(int i =0 ; i <22 ; i++){
                        items.add(new ItemTiles(Type.FRAMES));
                }
                for(int i =0 ; i <22 ; i++){
                        items.add(new ItemTiles(Type.PLANTS));
                }
                for(int i =0 ; i <22 ; i++){
                        items.add(new ItemTiles(Type.TROPHIES));
                }

        }

        //singolo item perché per refillare la box diventa difficile altrimenti

        public ItemTiles extract () throws NegativeArraySizeException{
                int randomIndex;
                Random rand = new Random();
                if(items.size() == 0){
                        throw new NegativeArraySizeException();
                }else{
                        randomIndex = rand.nextInt(items.size());
                        return items.remove(randomIndex);
                }
        }
        public int getLeftItems(){
                return items.size();
        }
}
