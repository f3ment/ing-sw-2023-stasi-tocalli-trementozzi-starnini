package model;

import java.util.Random;

public class Bag {
        private ItemTiles[] items; //pila
        private int[] took;
        private int actualLength;
        public Bag(){
                this.items = new ItemTiles[132];
                this.took = new int[132];
                this.actualLength = 132;
                for(int i =0 ; i <132 ; i++){
                        took[i] = i;
                }
                for(int i =0 ; i <22 ; i++){
                        items[i] = new ItemTiles(Type.CATS);
                }
                for(int i =0 ; i <22 ; i++){
                        items[i] = new ItemTiles(Type.BOOKS);
                }
                for(int i =0 ; i <22 ; i++){
                        items[i] = new ItemTiles(Type.GAMES);
                }
                for(int i =0 ; i <22 ; i++){
                        items[i] = new ItemTiles(Type.FRAMES);
                }
                for(int i =0 ; i <22 ; i++){
                        items[i] = new ItemTiles(Type.PLANTS);
                }
                for(int i =0 ; i <22 ; i++){
                        items[i] = new ItemTiles(Type.TROPHIES);
                }

        }

        //singolo item perché per refillare la box diventa difficile altrimenti

        public ItemTiles extract () throws NegativeArraySizeException{
                /*
                        randomizziamo su actuallength che rapprseneta lunghezza di estraibili
                        poi diminuisco axtulength e scambio indirizzo estratto con actualLength prima di decrementare
                */
                int randomIndex;
                Random rand;
                ItemTiles tmp;
                if(actualLength <=0){
                        throw new NegativeArraySizeException();
                }else{
                        //randomIndex = rand.nextInt(0, actualLength);
                        tmp = items[randomIndex];
                        items[randomIndex] = items[actualLength];
                        items[actualLength] = tmp;
                        actualLength--;
                }
        }
        public int getLeftItems(){
                return actualLength;
        }
}
