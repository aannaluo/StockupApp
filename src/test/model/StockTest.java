package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockTest {
    private Stock testStock;
    private FoodItem testFood1 = new FoodItem("apple","fridge");
    private FoodItem testFood2 = new FoodItem("popsicle","freezer");
    private FoodItem testFood3 = new FoodItem("chips","pantry");
    private FoodItem testFood4 = new FoodItem("banana","wasted");
    private FoodItem testFood5 = new FoodItem("carrot","consumed");
    private FoodItem testFood7 = new FoodItem("dumplings","freezer");
    private FoodItem testFood8 = new FoodItem("cookies","pantry");
    private FoodItem testFood9 = new FoodItem("meat","fridge");

    @BeforeEach
    void runBefore() {
        testStock = new Stock("test");
    }

    @Test
    void testConstructor() {
        assertTrue(testStock.getFridge().isEmpty());
        assertTrue(testStock.getFreezer().isEmpty());
        assertTrue(testStock.getPantry().isEmpty());
        assertTrue(testStock.getWasted().isEmpty());
        assertTrue(testStock.getConsumed().isEmpty());
    }


    @Test
    void testAddItemFridge() {
        testStock.addItem(testFood1);
        assertEquals(1,testStock.getFridge().size());
        assertEquals(testFood1,testStock.getFridge().get(0));
        assertEquals("apple", testStock.getFridge().get(0).getName());

        testStock.addItem(testFood1);
        assertEquals(1, testStock.getFridge().size());

        assertTrue(testStock.getFreezer().isEmpty());
    }

    @Test
    void testAddItemFreezer() {
        testStock.addItem(testFood2);
        assertEquals(1,testStock.getFreezer().size());
        assertEquals(testFood2,testStock.getFreezer().get(0));
        assertEquals("popsicle", testStock.getFreezer().get(0).getName());

        testStock.addItem(testFood2);
        assertEquals(1, testStock.getFreezer().size());

        assertTrue(testStock.getPantry().isEmpty());
    }

    @Test
    void testAddItemPantry() {
        testStock.addItem(testFood3);
        assertEquals(1,testStock.getPantry().size());
        assertEquals(testFood3,testStock.getPantry().get(0));
        assertEquals("chips", testStock.getPantry().get(0).getName());

        testStock.addItem(testFood3);
        assertEquals(1, testStock.getPantry().size());

        assertTrue(testStock.getWasted().isEmpty());
    }

    @Test
    void testAddItemWasted() {
        testStock.addItem(testFood4);
        assertEquals(1,testStock.getWasted().size());
        assertEquals(testFood4,testStock.getWasted().get(0));
        assertEquals("banana", testStock.getWasted().get(0).getName());

        testStock.addItem(testFood4);
        assertEquals(1, testStock.getWasted().size());

        assertTrue(testStock.getConsumed().isEmpty());
    }

    @Test
    void testAddItemConsumed() {
        testStock.addItem(testFood5);
        assertEquals(1,testStock.getConsumed().size());
        assertEquals(testFood5,testStock.getConsumed().get(0));
        assertEquals("carrot", testStock.getConsumed().get(0).getName());

        testStock.addItem(testFood5);
        assertEquals(1, testStock.getConsumed().size());

        assertTrue(testStock.getFridge().isEmpty());
    }

    @Test
    void testDelItemFreezer() {
        testStock.addItem(testFood1);
        testStock.addItem(testFood2);
        testStock.addItem(testFood7);

        assertTrue(testStock.getWasted().isEmpty());
        assertTrue(testStock.getConsumed().isEmpty());

        testStock.delItem(testFood2, "wasted");

        assertEquals(1, testStock.getFridge().size());

        assertEquals(1,testStock.getWasted().size());
        assertEquals(testFood2,testStock.getWasted().get(0));

        assertEquals(1,testStock.getFreezer().size());
        assertEquals("wasted", testFood2.getLocation());
        assertEquals("freezer", testFood7.getLocation());

        testStock.delItem(testFood7, "consumed");

        assertEquals(1,testStock.getConsumed().size());
        assertEquals(1,testStock.getWasted().size());
        assertEquals(0, testStock.getFreezer().size());
        assertEquals("consumed", testFood7.getLocation());

    }

    @Test
    void testDelItemFridge() {
        testStock.addItem(testFood1);
        testStock.addItem(testFood9);

        testStock.delItem(testFood1, "wasted");

        assertEquals(1,testStock.getWasted().size());
        assertEquals(testFood1,testStock.getWasted().get(0));

        assertEquals(1,testStock.getFridge().size());
        assertEquals("wasted", testFood1.getLocation());
        assertEquals("fridge", testFood9.getLocation());

        testStock.delItem(testFood9, "consumed");

        assertEquals(1,testStock.getConsumed().size());
        assertEquals(1,testStock.getWasted().size());
        assertEquals(0, testStock.getFridge().size());
        assertEquals("consumed", testFood9.getLocation());
    }

    @Test
    void testDelItemPantry() {
        testStock.addItem(testFood3);
        testStock.addItem(testFood8);

        testStock.delItem(testFood3, "wasted");

        assertEquals(1,testStock.getWasted().size());
        assertEquals(testFood3,testStock.getWasted().get(0));

        assertEquals(1,testStock.getPantry().size());
        assertEquals("wasted", testFood3.getLocation());
        assertEquals("pantry", testFood8.getLocation());

        testStock.delItem(testFood8, "consumed");

        assertEquals(1,testStock.getConsumed().size());
        assertEquals(1,testStock.getWasted().size());
        assertEquals(0, testStock.getPantry().size());
        assertEquals("consumed", testFood8.getLocation());
    }

    @Test
    void testFindFoodItem() {
        testStock.addItem(testFood2);
        assertEquals(1, testStock.getFreezer().size());
        assertEquals(testFood2,testStock.getFreezer().get(0));

        assertEquals(testFood2, testStock.findFoodItem("popsicle", testStock.getFreezer()));

    }


    @Test
    void testViewItems() {
        testStock.addItem(testFood1);
        testStock.addItem(testFood9);

        List<String> names = testStock.viewItems(testStock.getFridge());

        assertEquals("apple", names.get(0));
        assertEquals("meat", names.get(1));
    }
}
