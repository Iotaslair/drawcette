

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

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
        //Button btn = new Button();
        //btn.setText("Say 'Hello World'");
        //btn.setOnAction(new EventHandler<ActionEvent>() {
            
        //    @Override
        //    public void handle(ActionEvent event) {
        //        System.out.println("Hello World!");
        //    }
        //});
        
        Pane root = new Pane();
        //root.getChildren().add(btn);

        
        Scene scene = new Scene(root, 500, 500);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
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
                    
                    root.getChildren().add(line);
                    
                    storedx = -1;
                    storedy = -1;
                }
            }
        });
        
        primaryStage.setTitle("Hello World!");
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
