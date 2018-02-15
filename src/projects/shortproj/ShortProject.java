package projects.shortproj;

import projects.shortproj.util.Context;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class ShortProject extends Application {
	public AnchorPane root;
    private Context context;

    @Override
    public void start(Stage primaryStage) {
        context = new Context(primaryStage);
        
		// Set element positions on main pane
		AnchorPane.setTopAnchor(context.menuBar, 0.0);
		AnchorPane.setLeftAnchor(context.menuBar, 0.0);
		AnchorPane.setRightAnchor(context.menuBar, 0.0);
		
		AnchorPane.setTopAnchor(context.menuBox, context.menuBar.getPrefHeight());
        AnchorPane.setLeftAnchor(context.menuBox, 0.0);
        AnchorPane.setBottomAnchor(context.menuBox, 0.0);
        
        AnchorPane.setTopAnchor(context.sidebarRight, context.menuBar.getPrefHeight());
        AnchorPane.setRightAnchor(context.sidebarRight, 0.0);
        AnchorPane.setBottomAnchor(context.sidebarRight, 0.0);
        
        AnchorPane.setLeftAnchor(context.colorPicker, 0.0);        
        AnchorPane.setRightAnchor(context.colorPicker, 0.0);
        AnchorPane.setBottomAnchor(context.colorPicker, 0.0);

        AnchorPane.setTopAnchor(context.surface, 0.0);
        AnchorPane.setLeftAnchor(context.surface, 0.0);
        AnchorPane.setRightAnchor(context.surface, 0.0);
        AnchorPane.setBottomAnchor(context.surface, 0.0);
        
        AnchorPane root = new AnchorPane(context.menuBar, context.menuBox, context.sidebarRight, context.colorPicker, context.surface);
        
        Scene scene = new Scene(root, 1200, 800);
        
        primaryStage.setTitle("Photopoop v.0.0.3 // Shapes can be LIKE filled dude Edition");
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
