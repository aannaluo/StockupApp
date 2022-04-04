package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a stock with food item lists representing each location
public class Stock implements Writable {
    private String name;
    private List<FoodItem> fridge;
    private List<FoodItem> freezer;
    private List<FoodItem> pantry;
    private List<FoodItem> wasted;
    private List<FoodItem> consumed;

    // EFFECTS: constructs a stock with empty food item lists:
    //          fridge, freezer, pantry, wasted, consumed
    public Stock(String name) {
        this.name = name;
        this.fridge = new ArrayList<>();
        this.freezer = new ArrayList<>();
        this.pantry = new ArrayList<>();
        this.wasted = new ArrayList<>();
        this.consumed = new ArrayList<>();
    }

    // REQUIRES: no duplicate items
    // MODIFIES: this
    // EFFECTS:  adds item to the correct location
    public void addItem(FoodItem foodItem) {
        switch (foodItem.getLocation()) {
            case "fridge":
                addToStock(foodItem, fridge, "fridge");
                break;
            case "freezer":
                addToStock(foodItem, freezer, "freezer");
                break;
            case "pantry":
                addToStock(foodItem, pantry, "pantry");
                break;
            case "wasted":
                addToStock(foodItem, wasted, "wasted");
                break;
            default:
                addToStock(foodItem, consumed, "consumed");
                break;
        }
    }

    // EFFECTS: helper for addItem to ensure the item is not already in stock and adds item
    public void addToStock(FoodItem foodItem, List<FoodItem> location, String locationName) {
        if (findFoodItem(foodItem.getName(), location) == null) {
            location.add(foodItem);
            if (location == fridge || location == freezer || location == pantry) {
                EventLog.getInstance().logEvent(new Event(foodItem.getName() + " added to " + locationName));
            } else {
                EventLog.getInstance().logEvent(new Event(foodItem.getName() + " moved to " + locationName));
            }

        }
    }

    // REQUIRES: food item must already be in current location
    // MODIFIES: this
    // EFFECTS:  deletes item from original location and moves it to
    //           wasted or consumed food item list
    public void delItem(FoodItem foodItem, String newLocation) {
        if (foodItem.getLocation().equals("fridge")) {
            fridge.remove(foodItem);
            foodItem.changeLocation(newLocation);
            addItem(foodItem);

        } else if (foodItem.getLocation().equals("freezer")) {
            freezer.remove(foodItem);
            foodItem.changeLocation(newLocation);
            addItem(foodItem);

        } else {
            pantry.remove(foodItem);
            foodItem.changeLocation(newLocation);
            addItem(foodItem);
        }
    }

    public List<FoodItem> getFridge() {
        return fridge;
    }

    public List<FoodItem> getFreezer() {
        return freezer;
    }

    public List<FoodItem> getPantry() {
        return pantry;
    }

    public List<FoodItem> getWasted() {
        return wasted;
    }

    public List<FoodItem> getConsumed() {
        return consumed;
    }

    public String getName() {
        return name;
    }

    @Override
    // EFFECTS: returns the object as a JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("fridge", locationToJson(fridge));
        json.put("freezer", locationToJson(freezer));
        json.put("pantry", locationToJson(pantry));
        json.put("wasted", locationToJson(wasted));
        json.put("consumed", locationToJson(consumed));
        return json;
    }

    // EFFECTS: returns the food item with given name
    public FoodItem findFoodItem(String name, List<FoodItem> location) {
        FoodItem foundFood = null;
        for (FoodItem foodItem : location) {
            if (foodItem.getName().equals(name)) {
                foundFood = foodItem;
            }
        }
        return foundFood;
    }

    // EFFECTS: produces list of item names
    public List<String> viewItems(List<FoodItem> location) {
        List<String> names = new ArrayList<>();
        for (FoodItem foodItem : location) {
            names.add(foodItem.getName());
        }
        return names;
    }

    // returns JSONArray from the location
    private JSONArray locationToJson(List<FoodItem> location) {
        JSONArray jsonArray = new JSONArray();

        for (FoodItem f : location) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }

}
