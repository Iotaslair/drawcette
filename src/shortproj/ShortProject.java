

import gui.*;
import util.*;

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


public class ShortProject extends Application {
    
    Boolean firstClick = true;
    double storedx = -1;
    double storedy = -1;
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Context context = new Context(primaryStage);
        MenuBar menuBar = new TopMenu(context);
        VBox menuBox = new SideBar(context);
        HBox colorPicker = new ColorBar(context);
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
}
