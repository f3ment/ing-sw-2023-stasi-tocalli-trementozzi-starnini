package model.views;

import java.util.ArrayList;

public class ArrayListView {
    ArrayList array;
    public ArrayListView(ArrayList array){
        this.array=new ArrayList<>(array);
    }


    public Object get(int x){
        return array.get(x);
    }
    public int size(){
        return array.size();
    }
}
