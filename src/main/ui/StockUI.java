package ui;

import model.Event;
import model.EventLog;
import model.FoodItem;
import model.Stock;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.io.IOException;

// stock management application
public class StockUI extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/stock.json";

    private Stock stock;

    private JButton addButton;
    private JButton delButton;
    private JButton viewButton;
    private JButton saveButton;
    private JButton loadButton;

    private JButton fridgeButton;
    private JButton freezerButton;
    private JButton pantryButton;

    private JButton wastedButton;
    private JButton consumedButton;

    private JButton previous;

    private final JLabel viewLabel;
    private final JLabel foodLabel;

    private JPanel actionMenu;
    private JPanel addMenuLocation;
    private JPanel addMenuName;
    private JPanel delMenuPrevLocation;
    private JPanel delMenuName;
    private JPanel delMenuNewLocation;
    private JPanel viewMenu;
    private JPanel viewItemsMenu;
    private JPanel saveMenu;
    private JPanel loadMenu;

    private final JTextField addItemText;
    private final JTextField delItemText;

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    private final JLabel savedImage;
    private final JLabel loadedImage;


    // EFFECTS: runs the stock application
    public StockUI() throws IOException {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        initializeButtons();
        initializePanels();

        viewLabel = new JLabel();
        foodLabel = new JLabel();

        stock = new Stock("My Stock");

        addItemText = new JTextField("Enter food name");
        delItemText = new JTextField("enter food name");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        BufferedImage imgSave = ImageIO.read(new File("./data/save.jpg"));
        savedImage = new JLabel(new ImageIcon(imgSave));

        BufferedImage img = ImageIO.read(new File("./data/load.jpg"));
        loadedImage = new JLabel(new ImageIcon(img));

        setTitle("Stock");
        add(actionMenu);
        actionMenu();
    }

    // MODIFIES: this
    // EFFECTS: helper method for StockUI, initializes all buttons
    private void initializeButtons() {
        addButton = new JButton("Add Item");
        delButton = new JButton("Delete Item");
        viewButton = new JButton("View Stock");
        saveButton = new JButton("Save Stock");
        loadButton = new JButton("Load Stock");

        fridgeButton = new JButton("Fridge");
        freezerButton = new JButton("Freezer");
        pantryButton = new JButton("Pantry");

        wastedButton = new JButton("Wasted");
        consumedButton = new JButton("Consumed");

        previous = new JButton("return to home");
    }

    // MODIFIES: this
    // EFFECTS: helper method for StockUI, initializes all panels
    private void initializePanels() {
        actionMenu = new JPanel();
        addMenuLocation = new JPanel();
        addMenuName = new JPanel();
        delMenuPrevLocation = new JPanel();
        delMenuName = new JPanel();
        delMenuNewLocation = new JPanel();
        viewMenu = new JPanel();
        viewItemsMenu = new JPanel();
        saveMenu = new JPanel();
        loadMenu = new JPanel();
    }

    // MODIFIES: this
    // EFFECTS: loads menu with different available actions to be performed
    private void actionMenu() {
        actionMenu.setVisible(true);
        actionMenu.setLayout(new GridLayout(5,1));

        actionMenu.add(addButton);
        actionMenu.add(delButton);
        actionMenu.add(viewButton);
        actionMenu.add(saveButton);
        actionMenu.add(loadButton);

        addButton.setActionCommand("1add");
        delButton.setActionCommand("1del");
        viewButton.setActionCommand("1view");
        saveButton.setActionCommand("1save");
        loadButton.setActionCommand("1load");

        addButton.addActionListener(this);
        delButton.addActionListener(this);
        viewButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: loads panel of locations that food item can be added to
    private void addMenuLocation() {
        addMenuLocation.setLayout(new GridLayout(3,1));

        addMenuLocation.add(fridgeButton);
        addMenuLocation.add(freezerButton);
        addMenuLocation.add(pantryButton);

        fridgeButton.setActionCommand("2fridge");
        freezerButton.setActionCommand("2freezer");
        pantryButton.setActionCommand("2pantry");

        fridgeButton.addActionListener(this);
        freezerButton.addActionListener(this);
        pantryButton.addActionListener(this);

        this.getContentPane().add(addMenuLocation);
        addMenuLocation.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: panel for user input of food item name they want to add
    private void addMenuName() {
        addMenuName.add(addItemText);

        addItemText.setColumns(15);
        Font myFont = new Font("SansSerif", Font.BOLD, 24);
        addItemText.setFont(myFont);

        addItemText.setActionCommand("2add");
        addItemText.addActionListener(this);

        this.getContentPane().add(addMenuName);
        addMenuName.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: panel of locations user can delete food item from
    private void delMenuPrevLocation() {
        delMenuPrevLocation.setLayout(new GridLayout(3,1));
        delMenuPrevLocation.add(fridgeButton);
        delMenuPrevLocation.add(freezerButton);
        delMenuPrevLocation.add(pantryButton);

        fridgeButton.setActionCommand("3r");
        freezerButton.setActionCommand("3f");
        pantryButton.setActionCommand("3p");

        fridgeButton.addActionListener(this);
        freezerButton.addActionListener(this);
        pantryButton.addActionListener(this);

        this.getContentPane().add(delMenuPrevLocation);
        delMenuPrevLocation.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: panel for user input of food item name they want to delete
    private void delMenuName() {
        delMenuName.add(delItemText);

        delItemText.setColumns(15);
        Font myFont = new Font("SansSerif", Font.BOLD, 24);
        delItemText.setFont(myFont);

        delItemText.setActionCommand("3add");
        delItemText.addActionListener(this);

        this.getContentPane().add(delMenuName);
        delMenuName.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: panel of where deleted food item's new location should go
    private void delMenuNewLocation(String prevLocation) {
        delMenuNewLocation.setLayout(new GridLayout(2,1));

        delMenuNewLocation.add(wastedButton);
        delMenuNewLocation.add(consumedButton);

        wastedButton.setActionCommand("3w" + prevLocation);
        consumedButton.setActionCommand("3c" + prevLocation);

        wastedButton.addActionListener(this);
        consumedButton.addActionListener(this);

        this.getContentPane().add(delMenuNewLocation);
        delMenuNewLocation.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: panel of possible locations of food items to view
    private void viewMenu() {
        viewMenu.setVisible(true);
        viewMenu.setLayout(new GridLayout(5,1));

        viewMenu.add(fridgeButton);
        viewMenu.add(freezerButton);
        viewMenu.add(pantryButton);
        viewMenu.add(wastedButton);
        viewMenu.add(consumedButton);

        fridgeButton.setActionCommand("4fridge");
        freezerButton.setActionCommand("4freezer");
        pantryButton.setActionCommand("4pantry");
        wastedButton.setActionCommand("4wasted");
        consumedButton.setActionCommand("4consumed");

        fridgeButton.addActionListener(this);
        freezerButton.addActionListener(this);
        pantryButton.addActionListener(this);
        wastedButton.addActionListener(this);
        consumedButton.addActionListener(this);

        add(viewMenu);
    }

    // MODIFIES: this
    // EFFECTS: panel of image showing the user that the stock has been saved
    private void saveMenu() {
        saveMenu.add(savedImage);

        previous.setActionCommand("5back");
        previous.addActionListener(this);

        saveMenu.add(previous);

        this.getContentPane().add(saveMenu);
        saveMenu.setVisible(true);

        try {
            jsonWriter.open();
            jsonWriter.write(stock);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: panel of image showing the user that the stock has been loaded
    private void loadMenu() {
        loadMenu.add(loadedImage);

        previous.setActionCommand("6back");
        previous.addActionListener(this);

        loadMenu.add(previous);

        this.getContentPane().add(loadMenu);
        loadMenu.setVisible(true);

        try {
            stock = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: produces the next phase after each action event
    @Override
    public void actionPerformed(ActionEvent e) {
        actionMenu.setVisible(false);
        if ("1".equals(e.getActionCommand().substring(0,1))) {
            try {
                actionCommands(e.getActionCommand().substring(1));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else if ("2".equals(e.getActionCommand().substring(0,1))) {
            addCommands(e.getActionCommand().substring(1));

        } else if ("3".equals(e.getActionCommand().substring(0,1))) {
            delCommands(e.getActionCommand().substring(1,2), e.getActionCommand().substring(2));

        } else if ("4".equals(e.getActionCommand().substring(0,1))) {
            viewCommands(e.getActionCommand().substring(1));

        } else if ("5back".equals(e.getActionCommand())) {
            saveMenu.setVisible(false);
            actionMenu();

        } else if ("6back".equals(e.getActionCommand())) {
            loadMenu.setVisible(false);
            actionMenu();
        }
    }

    // EFFECTS: produces the next panel based on the action menu
    private void actionCommands(String command) throws IOException {
        switch (command) {
            case "add":
                addMenuName();
                break;
            case "del":
                delMenuName();
                break;
            case "view":
                viewMenu();
                break;
            case "save":
                saveMenu();
                break;
            default:
                loadMenu();
                break;
        }
    }

    // MODIFIES: stock
    // EFFECTS: processes commands relating to add menu
    private void addCommands(String command) {
        switch (command) {
            case "fridge":
                addHelper("fridge");
                break;
            case "freezer":
                addHelper("freezer");
                break;
            case "pantry":
                addHelper("pantry");
                break;
            default:
                addMenuName.setVisible(false);
                addMenuLocation();
                break;
        }
    }

    // MODIFIES: stock
    // EFFECTS: helper method for add
    private void addHelper(String location) {
        FoodItem foodItem;
        addMenuLocation.setVisible(false);
        foodItem = new FoodItem(addItemText.getText(), location);
        stock.addItem(foodItem);
        actionMenu.setVisible(true);
    }


    // MODIFIES: stock
    // EFFECTS: processes commands relating to del menu
    private void delCommands(String command, String prevLocation) {
        try {
            if (command.equals("r")) {
                delCommandHelper("fridge");

            } else if (command.equals("f")) {
                delCommandHelper("freezer");

            } else if (command.equals("p")) {
                delCommandHelper("pantry");

            } else if (command.equals("w")) {
                delMenuNewLocation.setVisible(false);
                actionMenu();
                stock.delItem(stock.findFoodItem(delItemText.getText(), prevLocation(prevLocation)), "wasted");

            } else if (command.equals("c")) {
                delMenuNewLocation.setVisible(false);
                actionMenu();
                stock.delItem(stock.findFoodItem(delItemText.getText(), prevLocation(prevLocation)), "consumed");

            } else {
                delMenuName.setVisible(false);
                delMenuPrevLocation();
            }
        } catch (NullPointerException e) {
            actionMenu();
        }

    }

    // EFFECTS: produces the foodItem list of the previous location
    private List<FoodItem> prevLocation(String location) {
        List<FoodItem> lastLocation = null;
        switch (location) {
            case "fridge":
                lastLocation = stock.getFridge();
                break;
            case "freezer":
                lastLocation = stock.getFreezer();
                break;
            case "pantry":
                lastLocation = stock.getPantry();
                break;
        }
        return lastLocation;
    }

    // EFFECTS: sets previous del panel to invisible and loads next del panel
    private void delCommandHelper(String prevLocation) {
        delMenuPrevLocation.setVisible(false);
        delMenuNewLocation(prevLocation);
    }

    // EFFECTS: processes commands relating to view panel
    private void viewCommands(String command) {
        viewMenu.setVisible(false);
        switch (command) {
            case "fridge":
                viewItems(stock.getFridge());

                break;
            case "freezer":
                viewItems(stock.getFreezer());

                break;
            case "pantry":
                viewItems(stock.getPantry());

                break;
            case "wasted":
                viewItems(stock.getWasted());

                break;
            case "consumed":
                viewItems(stock.getConsumed());

                break;
            case "back":
                viewItemsMenu.setVisible(false);
                actionMenu();
                break;
        }
    }

    // EFFECTS: helper function to view list
    private void viewItems(List<FoodItem> location) {
        viewItemsMenu.setVisible(true);

        foodLabel.setText(stock.viewItems(location).toString());
        viewItemsMenu.add(foodLabel);

        viewLabel.setText("There are " + location.size() + " items in here");
        viewItemsMenu.add(viewLabel);

        previous.setActionCommand("4back");
        previous.addActionListener(this);

        viewItemsMenu.add(previous);

        add(viewItemsMenu);
    }

    // EFFECTS: helps load the app and build content panel
    private static void showMenu() {
        StockUI newContentPane = null;
        try {
            newContentPane = new StockUI();
        } catch (FileNotFoundException e) {
            System.out.println("not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert newContentPane != null;
        newContentPane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newContentPane.pack();
        newContentPane.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e.getDate() + " " + e.getDescription());
                }
            }
        });
    }

    // EFFECTS: main function that loads the stock app
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showMenu();
            }
        });
    }

}
