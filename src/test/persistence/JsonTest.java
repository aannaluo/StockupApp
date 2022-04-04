package persistence;

import model.FoodItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFoodItem(String name, String location, FoodItem foodItem) {
        assertEquals(name, foodItem.getName());
        assertEquals(location, foodItem.getLocation());
    }
}
