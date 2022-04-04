package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodItemTest {
    private FoodItem testFoodItem = new FoodItem("broccoli", "fridge");

    @BeforeEach
    void runBefore() {
    testFoodItem = new FoodItem("broccoli", "fridge");
    }

    @Test
    void testConstructor() {
        assertEquals("broccoli",testFoodItem.getName());
        assertEquals("fridge",testFoodItem.getLocation());
    }

    @Test
    void testChangeLocation() {
        assertEquals("fridge", testFoodItem.getLocation());

        testFoodItem.changeLocation("freezer");
        assertEquals("freezer", testFoodItem.getLocation());

        testFoodItem.changeLocation("wasted");
        assertEquals("wasted", testFoodItem.getLocation());

        testFoodItem.changeLocation("wasted");
        assertEquals("wasted", testFoodItem.getLocation());

    }

}