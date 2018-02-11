package projects.shortproj;

import projects.shortproj.gui.*;
import projects.shortproj.util.Context;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class ShortProject extends Application {
	
    private Context context;
	private ColorBar colorPicker;
	
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        context = new Context(primaryStage);
        
        SideBar menuBox = new SideBar(context);
        colorPicker = new ColorBar(context);
        DrawingSurface surface = new DrawingSurface(menuBox, colorPicker, context);
        MenuBar menuBar = new TopMenu(context, surface);
        
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
}
