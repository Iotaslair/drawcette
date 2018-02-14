package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import java.awt.image.BufferedImage;

//Print additions
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.transform.Scale;

import projects.shortproj.util.Context;

public final class TopMenu extends MenuBar {

    Printer printer = Printer.getDefaultPrinter();   //Grabs the default printer
	public TopMenu(Context context) {     
        // Create menus
        Menu newMenu = new Menu("New");
        Menu saveMenu = new Menu("Save");
        Menu loadMenu = new Menu("Load");
        Menu printMenu = new Menu("Print");
        Menu exitMenu = new Menu("Exit");
        
        onAction(exitMenu);
        exitMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	Alert alert = new Alert(AlertType.CONFIRMATION);
            	alert.setTitle("Warning Dialog");
            	alert.setHeaderText("Exiting Program");
            	alert.setContentText("All unsaved work will be lost. "
            			+ "Are you sure that you want to continue?");

            	Optional<ButtonType> result = alert.showAndWait();
            	if (result.get() != ButtonType.OK)
            	    return;
                Platform.exit();
            }
        });
        
        onAction(loadMenu);
        loadMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                load(context.stage, context.surface);
            }
        });

        onAction(saveMenu);
        saveMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle (ActionEvent e){
                save(context.stage, context.surface);
            }

        });

        onAction(printMenu);
        printMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle (ActionEvent e){
                print(context.surface);
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
                new FileChooser.ExtensionFilter("All Image Files", "*.jpg","*.jpeg","*.jpe", "*.png", "*.svg" ),
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
        BufferedImage bufferedImage = new BufferedImage(550, 400, BufferedImage.TYPE_INT_ARGB);

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
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        //Place where the user wants the file saved to
        File outputFile = fileChooser.showSaveDialog(primaryStage);

        //makes a writable image which takes a picture of whats in the drawing space
        //                                                        Looking for height and width of stuff
        //                                                        or get the right scene thing
        WritableImage snapshot = pane.snapshot(new SnapshotParameters(),null);
        BufferedImage bImage;
        bImage = javafx.embed.swing.SwingFXUtils.fromFXImage(snapshot,bufferedImage);
        if (outputFile != null)
        {
        	System.out.println("Trying to save!");
        	try
            {
                ImageIO.write(bImage, "png", outputFile);
            }
            catch (IOException ex){
            	System.out.println("Exception!");
            }  
        }
    }
    
    public void print(Node printTarget){
       PrinterJob printing = PrinterJob.createPrinterJob(); //Creates a print request for the default system Printer.
                                                            //Returns null if there isn't any default print
       PageLayout layOut = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
       //Above line creates the printed page's default values
       double scaleX = layOut.getPrintableWidth()/printTarget.getBoundsInParent().getWidth();  //Scales image to page
       double scaleY = layOut.getPrintableHeight()/printTarget.getBoundsInParent().getHeight();
       Scale scaled = new Scale(scaleX, scaleY);
       printTarget.getTransforms().add(scaled);  //Scales image
       if(printing !=null){
         boolean finished = printing.printPage(printTarget); //Check if printing is successful. 
         if (finished) {
            printing.endJob(); //Ends Print
         }
        }
       printTarget.getTransforms().remove(scaled);  //Unscales image
       //}
    }
}