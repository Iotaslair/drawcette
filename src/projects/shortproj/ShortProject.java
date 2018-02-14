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
        context = new Context(primaryStage);
        
        Scene scene = new Scene(context.root, 1200, 800);
        
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
