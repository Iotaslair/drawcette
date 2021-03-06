package projects.shortproj.gui;

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
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;

//Print additions
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.transform.Scale;

import projects.shortproj.util.Context;
import projects.shortproj.util.ElementGroup;

public final class TopMenu extends MenuBar {
	
	public Context context;

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

        File defaultSaveLocation = new File(System.getProperty("user.home"),"");
        fileChooser.setInitialDirectory(defaultSaveLocation);
        
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
        BufferedImage bufferedImage = new BufferedImage(550, 400, BufferedImage.TYPE_INT_ARGB);

        //Extension stuff
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPG", "*.jpg","*.jpeg","*.jpe"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );

         //sets a default directory to save pictures
        File defaultSaveLocation = new File(System.getProperty("user.home"),"");
        fileChooser.setInitialDirectory(defaultSaveLocation);


        fileChooser.setInitialFileName(".jpg");

        //Place where the user wants the file saved to
        File outputFile = fileChooser.showSaveDialog(primaryStage);

        //makes a writable image which takes a picture of whats in the drawing space
        //                                                        Looking for height and width of stuff
        //                                                        or get the right scene thing


        WritableImage snapshot = pane.snapshot(null,null);
        BufferedImage bImage;
        bImage = SwingFXUtils.fromFXImage(snapshot,bufferedImage);
        if (outputFile != null)
        {
        	try
            {
                ImageIO.write(bImage, "png", outputFile);
                System.out.println("Saved!");
            }
            catch (IOException ex){
            	System.out.println("Failed to save!");
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