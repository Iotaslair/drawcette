/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.input.*;

/**
 *
 * @author jerem
 */
public class ShortProject extends Application {
    
    Boolean firstClick = true;
    double storedx = -1;
    double storedy = -1;
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        MenuBar menuBar = createMenu(primaryStage);
        VBox menuBox = createSidebar();
        HBox colorPicker = createBottomToolbar();
        Pane surface = addSurface();
        
        // Set both panes' positions on main pane
        root.setTop(menuBar);
        root.setLeft(menuBox);
        root.setBottom(colorPicker);
        root.setCenter(surface);
        
        Scene scene = new Scene(root, 1000, 800);
                
        primaryStage.setTitle("Photopoop v.0.0.01");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public MenuBar createMenu(Stage stage) {
        // Create MenuBar
        MenuBar menuBar = new MenuBar();
        
        // Create menus
        Menu newMenu = new Menu("New");
        Menu saveMenu = new Menu("Save");
        Menu loadMenu = new Menu("Load");
        Menu printMenu = new Menu("Print");
        Menu exitMenu = new Menu("Exit");
        
        onAction(exitMenu);
        exitMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Platform.exit();
            }
        });
        
        onAction(loadMenu);
        loadMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                load(stage);
            }
        });

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(newMenu, saveMenu, loadMenu, printMenu, exitMenu);
        
        return menuBar;
    }
        
    public VBox createSidebar() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5, 25, 15, 15));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #336699;");    //Sets color to inputted value

        // Tool Group 1
        final ToggleGroup group1 = new ToggleGroup();

        Label toolsLabel1 = new Label("Brushes");
        toolsLabel1.setTextFill(Color.WHITE);
        
        ToggleButton tool1 = new ToggleButton("Brush 1");
        tool1.setToggleGroup(group1);
        tool1.setSelected(true);
        tool1.setPrefSize(60, 25);

        ToggleButton tool2 = new ToggleButton("Brush 2");
        tool2.setToggleGroup(group1);
        tool2.setPrefSize(60, 25);

        ToggleButton tool3 = new ToggleButton("Brush 3");
        tool3.setToggleGroup(group1);
        tool3.setPrefSize(60, 25);

        // Instrument Group 2        
        final ToggleGroup group2 = new ToggleGroup();

        Label toolsLabel2 = new Label("Shapes");
        toolsLabel2.setPadding(new Insets(10, 0, 0, 0));
        toolsLabel2.setTextFill(Color.WHITE);

        ToggleButton tool4 = new ToggleButton("Line");
        tool4.setToggleGroup(group2);
        tool4.setSelected(true);
        tool4.setPrefSize(60, 25);

        ToggleButton tool5 = new ToggleButton("Square");
        tool5.setToggleGroup(group2);
        tool5.setPrefSize(60, 25);

        ToggleButton tool6 = new ToggleButton("Circle");
        tool6.setToggleGroup(group2);
        tool6.setPrefSize(60, 25);

        
        // Add all elements to the toolbar
        vbox.getChildren().addAll(toolsLabel1, tool1, tool2, tool3, toolsLabel2, tool4, tool5, tool6);
        
        return vbox;
    }
    
    public HBox createBottomToolbar() {
        HBox hbox = new HBox();
        
        // Set up the panel
        hbox.setPadding(new Insets(20, 20, 15, 15));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #444;");
        
        // Color Palette
        final ToggleGroup group = new ToggleGroup();
        
        ToggleButton color1 = new ToggleButton();
        color1.setToggleGroup(group);
        // color1.setUserData(Color.RED);
        color1.setSelected(true);
        color1.setPrefSize(30, 30);
        color1.setStyle("-fx-base: red;");

        ToggleButton color2 = new ToggleButton();
        color2.setToggleGroup(group);
        color2.setUserData(Color.ORANGE);
        color2.setPrefSize(30, 30);
        color2.setStyle("-fx-base: orange;");

        ToggleButton color3 = new ToggleButton();
        color3.setToggleGroup(group);
        color3.setUserData(Color.YELLOW);
        color3.setPrefSize(30, 30);
        color3.setStyle("-fx-base: yellow;");
        
        ToggleButton color4 = new ToggleButton();
        color4.setToggleGroup(group);
        color4.setUserData(Color.GREEN);
        color4.setPrefSize(30, 30);
        color4.setStyle("-fx-base: green;");

        ToggleButton color5 = new ToggleButton();
        color5.setToggleGroup(group);
        color5.setUserData(Color.BLUE);
        color5.setPrefSize(30, 30);
        color5.setStyle("-fx-base: blue;");

        ToggleButton color6 = new ToggleButton();
        color6.setToggleGroup(group);
        color6.setUserData(Color.INDIGO);
        color6.setPrefSize(30, 30);
        color6.setStyle("-fx-base: indigo;");

        ToggleButton color7 = new ToggleButton();
        color7.setToggleGroup(group);
        color7.setUserData(Color.VIOLET);
        color7.setPrefSize(30, 30);
        color7.setStyle("-fx-base: violet;");

        ToggleButton color8 = new ToggleButton();
        color8.setToggleGroup(group);
        color8.setUserData(Color.BLACK);
        color8.setPrefSize(30, 30);
        color8.setStyle("-fx-base: black;");

        hbox.getChildren().addAll(color1, color2, color3, color4, color5, color6, color7, color8);
        
        return hbox;
    }
    
    public Pane addSurface() {
        Pane surface = new Pane();
        
        // Add an event handler to the pane and make it draw lines for now.
        surface.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Needs to add a check for what button is down to call approperate function on surface.
                
                // An example function to call on surface.
                drawLine(surface, event);       
            }
        });

        return surface;
    }

    public static void onAction(Menu menu) {
        final MenuItem menuItem = new MenuItem();

        menu.getItems().add(menuItem);
        menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
        menu.addEventHandler(Menu.ON_SHOWING, event -> menu.fire());
    }
    
    /*  Function that given a mouse even and a surface advances 
     *  the line drawing process on that surface by one click.
     */
    public void drawLine(Pane surface, MouseEvent event) {
        System.out.println(event.getX() + ", " + event.getY());
        if (firstClick){
            firstClick = false;
            storedx = event.getX();
            storedy = event.getY();
        }
        else {
            firstClick = true;
            // Make line
            Line line = new Line();
            
            line.setStartX(storedx);
            line.setStartY(storedy);
            line.setEndX(event.getX());
            line.setEndY(event.getY());
            
            surface.getChildren().add(line);
                    
            storedx = -1;
            storedy = -1;
        }
    }

    // Opens up file opener, need to find a way to display what is opened
    public void load(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(primaryStage);
    }
}
