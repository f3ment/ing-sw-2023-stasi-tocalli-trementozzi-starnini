package model;

import org.junit.jupiter.api.Test;
import view.Color;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void getColor() {
        System.out.println(" CA " + Type.CATS.getColor() + "ts" + Color.RESET);
    }
}