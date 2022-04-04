package persistence;

import model.FoodItem;
import model.Stock;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Stock stock = new Stock("My Stock");
            JsonWriter writer = new JsonWriter("./data/\0randomname");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStock() {
        try {
            Stock stock = new Stock("My stock");
            JsonWriter writer = new JsonWriter("./data/testEmptyStock.json");
            writer.open();
            writer.write(stock);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyStock.json");
            stock = reader.read();
            assertEquals("My stock", stock.getName());
            assertTrue(stock.getFridge().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Stock stock = new Stock("My stock");
            stock.addItem(new FoodItem("cheese", "fridge"));
            stock.addItem(new FoodItem("ham", "fridge"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStock.json");
            writer.open();
            writer.write(stock);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStock.json");
            stock = reader.read();
            assertEquals("My stock", stock.getName());
            List<FoodItem> fridgeItems = stock.getFridge();
            assertEquals(2, fridgeItems.size());
            checkFoodItem("cheese", "fridge", fridgeItems.get(0));
            checkFoodItem("ham", "fridge", fridgeItems.get(1));
        } catch (IOException e) {
            fail("Should not have thrown Exception");
        }
    }
}
