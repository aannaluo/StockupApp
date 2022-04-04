package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a food item with a name and location
public class FoodItem implements Writable {
    private String name;
    private String location;

    // REQUIRES: name must not be empty, location must be one of
    //              - fridge, freezer, pantry, wasted, consumed
    // EFFECTS: constructs a FoodItem with name of food,
    //          and the location of where it is placed
    public FoodItem(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // MODIFIES: this
    // EFFECTS:  replaces the location of the foodItem with new location
    public void changeLocation(String newLocation) {
        location = newLocation;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    // EFFECTS: returns the object as a JsonObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("location", location);
        return json;
    }

}
