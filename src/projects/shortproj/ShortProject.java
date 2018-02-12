package projects.shortproj;

import projects.shortproj.util.Context;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;

public class ShortProject extends Application {
	
    private Context context;
	
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        context = new Context(primaryStage);
        
        // Set both panes' positions on main pane
        root.setTop(context.menuBar);
        root.setLeft(context.menuBox);
        root.setRight(context.sidebarRight);
        root.setBottom(context.colorPicker);
        root.setCenter(context.surface);
        
        Scene scene = new Scene(root, 1200, 800);
                
        primaryStage.setTitle("Photopoop v.0.0.1");
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
