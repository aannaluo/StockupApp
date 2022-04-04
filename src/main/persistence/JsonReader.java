package persistence;

import model.FoodItem;
import model.Stock;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// code modelled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// a reader that reads the stock from the JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader that reads source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads stock from file and returns it,
    // throws an IOException there is an error reading data
    public Stock read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStock(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses stock from JSON object and returns it
    private Stock parseStock(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Stock stock = new Stock(name);
        addFood(stock, jsonObject);
        return stock;
    }

    // MODIFIES: stock
    // EFFECTS: parses each food item from JSON object and adds it to stock
    private void addFood(Stock stock, JSONObject jsonObject) {
        addFoodToLocation("fridge", stock, jsonObject);
        addFoodToLocation("freezer", stock, jsonObject);
        addFoodToLocation("pantry", stock, jsonObject);
        addFoodToLocation("wasted", stock, jsonObject);
        addFoodToLocation("consumed", stock, jsonObject);
    }

    // EFFECTS: helper function for addFood
    private void addFoodToLocation(String location, Stock stock, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray(location);

        for (Object json : jsonArray) {
            JSONObject nextFood = (JSONObject) json;
            addFoodItem(stock, nextFood);
        }

    }

    // EFFECTS: adds the JSON Object as a food item to the stock
    private void addFoodItem(Stock stock, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        FoodItem foodItem = new FoodItem(name, location);
        stock.addItem(foodItem);
    }

}
