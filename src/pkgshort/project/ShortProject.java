/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.input.*;
import javafx.scene.Node;

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

        MenuBar menuBar = createMenu();
        VBox menuBox = addVBox();
        Pane surface = addSurface();
        
        // Set both panes' positions on main pane
        root.setTop(menuBar);
        root.setLeft(menuBox);
        root.setCenter(surface);
        
        Scene scene = new Scene(root, 800, 800);
                
        primaryStage.setTitle("Photopoop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public MenuBar createMenu() {
        // Create MenuBar
        MenuBar menuBar = new MenuBar();
        
        // Create menus
        Menu fileMenu = new Menu("File");
        Menu exportMenu = new Menu("Export");
        
        // Create Menu Items
        MenuItem newItem = new MenuItem("New");
        MenuItem openFileItem = new MenuItem("Open File");
        MenuItem exitItem = new MenuItem("Exit");
        
        MenuItem exportSvgItem = new MenuItem("Export into SVG");
        MenuItem printItem = new MenuItem("Print");
        
        // Add menuItems to the Menus
        fileMenu.getItems().addAll(newItem, openFileItem, exitItem);
        exportMenu.getItems().addAll(exportSvgItem, printItem);
        
        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(fileMenu, exportMenu);
        
        return menuBar;
    }
    
    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #336699;");

        Button btn1 = new Button("1");
        btn1.setPrefSize(20, 20);

        Button btn2 = new Button("2");
        btn2.setPrefSize(20, 20);

        Button btn3 = new Button("3");
        btn3.setPrefSize(20, 20);

        Button btn4 = new Button("4");
        btn4.setPrefSize(20, 20);

        Button btn5 = new Button("5");
        btn5.setPrefSize(20, 20);

        vbox.getChildren().addAll(btn1, btn2, btn3, btn4, btn5);

        return vbox;
    }
    
    public Pane addSurface() {
        Pane surface = new Pane();
        
        // Add an event handler to the pane and make it draw lines for now.
        surface.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
        });
        
        return surface;
    }
        
}
