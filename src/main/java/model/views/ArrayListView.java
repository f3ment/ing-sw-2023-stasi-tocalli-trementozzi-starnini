package model.views;

import java.util.ArrayList;

/**
 * This class represents an immutable copy of an ArrayList.
 * it is used by the views to get the data from the model
 * without being able to modify it.
 */
public class ArrayListView {
    ArrayList array;

    /**
     * @param array the ArrayList to be copied
     */
    public ArrayListView(ArrayList array){
        this.array=new ArrayList<>(array);
    }


    /**
     * Returns the element at the specified position in this list.
     * @param x the index of the element to be returned
     * @return the element at the specified position in this list
     */
    public Object get(int x){
        return array.get(x);
    }

    /**
     * @return the number of elements in this list
     */
    public int size(){
        return array.size();
    }
}
