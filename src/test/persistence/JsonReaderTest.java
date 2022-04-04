package persistence;

import model.FoodItem;
import model.Stock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Stock stock = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStock() {
        JsonReader reader = new JsonReader("./data/testEmptyStock.json");
        try {
            Stock stock = reader.read();
            assertEquals("My stock", stock.getName());
            assertTrue(stock.getFridge().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStock() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStock.json");
        try {
            Stock stock = reader.read();
            assertEquals("My stock", stock.getName());
            List<FoodItem> fridge = stock.getFridge();
            assertEquals(2, fridge.size());
            checkFoodItem("apple", "fridge", fridge.get(0));
            checkFoodItem("cheese", "fridge", fridge.get(1));
        } catch (IOException e) {
            fail("couldn't read from file");
        }
    }
}
