package model;

import model.views.ArrayListView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ArrayListViewTest {
    @Test
    void ArrayListViewTest(){
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(new Object());
        model.views.ArrayListView arrayListView = new model.views.ArrayListView(arrayList);
        assertNotNull(arrayListView.get(0));
    }

    @Test
    void sizeTest(){
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(new Object());
        model.views.ArrayListView arrayListView = new ArrayListView(arrayList);
        assertEquals(1, arrayListView.size());
    }
}