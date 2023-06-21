package model.views;

import model.ItemTiles;
import model.Type;
import model.views.BoxView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxViewTest {

    @Test
    void BoxViewTest(){
        ItemTiles itemTiles = new ItemTiles(Type.CATS,1);
        model.views.BoxView boxView = new BoxView(false ,itemTiles);
        assertFalse(boxView.getValid());
        assertEquals(boxView.getItemContained(), itemTiles);
    }
}