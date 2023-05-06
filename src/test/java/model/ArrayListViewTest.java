package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ArrayListViewTest {
    @Test
    void ArrayListViewTest(){
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(new Object());
        ArrayListView arrayListView = new ArrayListView(arrayList);
        assertNotNull(arrayListView.get(0));
    }

    @Test
    void sizeTest(){
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(new Object());
        ArrayListView arrayListView = new ArrayListView(arrayList);
        assertEquals(1, arrayListView.size());
    }
}