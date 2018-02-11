package projects.shortproj.gui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;

import projects.shortproj.util.Context;


public final class TopMenu extends MenuBar {

	public TopMenu(Context context, DrawingSurface surface) {     
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
                load(context.stage, surface);
            }
        });

        onAction(saveMenu);
        saveMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle (ActionEvent e){
                save(context.stage, surface);
            }

        });

        // Add Menus to the MenuBar
        this.getMenus().addAll(newMenu, saveMenu, loadMenu, printMenu, exitMenu);
	}
	
    public static void onAction(Menu menu) {
        final MenuItem menuItem = new MenuItem();

        menu.getItems().add(menuItem);
        menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
        menu.addEventHandler(Menu.ON_SHOWING, event -> menu.fire());
    }
    
    // Opens up file opener and displays image
    public void load(Stage primaryStage, DrawingSurface pane) {
        //makes a object that opens files
        FileChooser fileChooser = new FileChooser();
        //sets title of said object
        fileChooser.setTitle("Open File");
        
        //allows for the user to filter between different types of files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("All Image Files", "*.jpg","*.jpeg","*.jpe", "*.png" ),
                new FileChooser.ExtensionFilter("JPG", "*.jpg","*.jpeg","*.jpe"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );


        //opens up the file viewer and sets the file chosen to 'file'
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        //converts that file to an Image
        Image image = new Image(file.toURI().toString());
        //converts that image to ImageView (Actually lets the image be seen for some reason
        //thanks for making that clear javafx :) )
        ImageView iv1 = new ImageView(image);
        //makes sure a file is selected
        if (file != null)
        {
            //adds image to the canvas
            pane.getChildren().add(iv1);
        }
    }


    public void save(Stage primaryStage, DrawingSurface pane)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        System.out.println("Opened save function");

        //sets a default directory to save pictures
        File defaultSaveLocation = new File(System.getProperty("user.home"),".gui/SavedPictures");
        if (!defaultSaveLocation.exists())
        {
            defaultSaveLocation.mkdirs();
        }
        fileChooser.setInitialDirectory(defaultSaveLocation);

        //Extension stuff
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Files", "*.*"),
            new FileChooser.ExtensionFilter("All Image Files", "*.jpg","*.jpeg","*.jpe", "*.png" ),
            new FileChooser.ExtensionFilter("JPG", "*.jpg","*.jpeg","*.jpe"),
            new FileChooser.ExtensionFilter("PNG", "*.png"),
            new FileChooser.ExtensionFilter("Scalable Vector Graphics", "*.SVG")
        );

        //Place where the user wants the file saved to
        File outputFile = fileChooser.showSaveDialog(primaryStage);

        //makes a writable image which takes a picture of whats in the drawing space
        //                                                        Looking for height and width of stuff
        //                                                        or get the right scene thing
        WritableImage snapshot = primaryStage.getScene().snapshot(new SnapshotParameters(),null);
        ImageView iv1 = new ImageView(snapshot);
        /*if (outputFile != null)
        {
        	System.out.println("Trying to save!");
            
        	try
            {
                ImageIO.write(SwingFXUtils.fromFXImage(IMAGE OBJECT), null),"png", outputFile;
                              or IMAGE OBJECT,"png", outputFile
            }
            catch (IOException ex){
            }
            
        }*/
    }
}