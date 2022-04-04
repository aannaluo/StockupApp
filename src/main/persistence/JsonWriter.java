package persistence;

import model.Stock;
import org.json.JSONObject;

import java.io.*;

// code modelled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// a writer that writes the JSON representation of the stock to the file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a writer to the destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS : opens writer; throws the FileNotFoundException if the
    // file is not found
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes the JSON representation of the stock to file
    public void write(Stock stock) {
        JSONObject json = stock.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
