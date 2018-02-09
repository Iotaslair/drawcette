package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import util.Context;

public final class TopMenu extends MenuBar {

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
                Platform.exit();
            }
        });
        
        onAction(loadMenu);
        loadMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                load(context.stage);
            }
        });

        onAction(saveMenu);
        saveMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle (ActionEvent e){
                save(context.stage);
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
    
    // Opens up file opener, need to find a way to display what is opened
    public void load(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        //allows for the user to filter between different types of files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg","*.jpeg","*.jpe"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
        //opens up the file
        fileChooser.showOpenDialog(primaryStage);
    }

    public void save(Stage primaryStage)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");


        fileChooser.showOpenDialog(primaryStage);
    }
}
