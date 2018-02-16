package gui;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import java.awt.image.BufferedImage;

//Print additions
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.transform.Scale;

import projects.shortproj.util.Context;
import projects.shortproj.util.ElementGroup;

public final class TopMenu extends MenuBar {
	
	Context context;

    Printer printer = Printer.getDefaultPrinter();   //Grabs the default printer
	public TopMenu(Context context) {     
        this.context = context;
		
		// Create menus		
        Menu newMenu = new Menu("");
        Menu saveMenu = new Menu("");
        Menu loadMenu = new Menu("");
        Menu printMenu = new Menu("");
        Menu exitMenu = new Menu("");
        
        setOnAction(newMenu, "New");
        setOnAction(saveMenu, "Save");
        setOnAction(loadMenu, "Load");
        setOnAction(printMenu, "Print");
        setOnAction(exitMenu, "Exit");
        
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

    public void exit() {
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
    	PageLayout layOut = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
    	//Above line creates the printed page's default values
    	
    	if (printing != null && printing.showPrintDialog(printTarget.getScene().getWindow())) {
            //.showPrintDialog provides a basic UI system for the user to select which printer they want and what print
            //settings they want. It returns true if user accepts print settings OR there isn't a default print UI.
            //Returns false if print is canceled, print job is already started or failed.
           if(printing !=null){
             boolean finished = printing.printPage(printTarget); //Check if printing is successful. 
             if (finished) {
               printing.endJob();
             }
           }
        }
    	
    }
    
    public void createNew() {
    	// Give a warning message.
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning Dialog");
		alert.setHeaderText("Clearing Canvas");
		alert.setContentText("This will clear the canvas, are you sure?");
	
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() != ButtonType.OK)
		    return;
		
		// Remove all elements on the canvas and all groups in the sidebar.
	    context.surface.getChildren().removeAll(context.surface.getChildren());
	    context.sidebarRight.items.removeAll(context.sidebarRight.items);
	    
	    // Add back in the background group.
        ElementGroup background = new ElementGroup(new Group(), "- None -");
        context.sidebarRight.items.add(background);
    }
    
    // Create a label and put it on top of the menu item.
    // Make the label call the correct function on mouseclick.
    private void setOnAction(Menu menu, String action) {
	    Label menuLabel = new Label(action);
	    menuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	String str = action;
	    	
	        @Override
	        public void handle(MouseEvent event) {
	        	switch (str) {
	        	case "Load": 	load(context.stage, context.surface);
	        					break;
	        	case "Save":	save(context.stage, context.surface);
	        					break;
	        	case "Exit":	exit();
	        					break;
	        	case "New":		createNew();
	        					break;
	        	case "Print":	print(context.surface);
	        					break;
       }
	        }
	    });
	    menu.setGraphic(menuLabel);
    }
}