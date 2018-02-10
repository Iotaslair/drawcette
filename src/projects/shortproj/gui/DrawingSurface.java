package projects.shortproj.gui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import projects.shortproj.util.*;
import projects.shortproj.gui.*;

public class DrawingSurface extends Pane {

	private SideBar sideBar;
	private ColorBar colorBar;
	private Context context;
	
	public DrawingSurface(SideBar sideBar, ColorBar colorBar, Context context) {
		this.sideBar = sideBar;
		this.colorBar = colorBar;
		this.context = context;
		
		// Add an event handler to the pane and make it draw lines for now.
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Needs to add a check for what button is down to call appropriate function on surface.
                
                // An example function to call on surface.
                drawLine(event);
            }
        });
		
	}
	
	
	/*  Function that given a mouse even and a surface advances 
     *  the line drawing process on that surface by one click.
     */
    public void drawLine(MouseEvent event) {
        System.out.println(event.getX() + ", " + event.getY());
        if (context.firstClick){
            context.firstClick = false;
            context.storedx = event.getX();
            context.storedy = event.getY();
        }
        else {
            context.firstClick = true;
            // Make line
            Line line = new Line();
            Paint c = colorBar.getColor();
            line.setStroke(c);
            
            line.setStartX(context.storedx);
            line.setStartY(context.storedy);
            line.setEndX(event.getX());
            line.setEndY(event.getY());
            
            this.getChildren().add(line);
                    
            context.storedx = -1;
            context.storedy = -1;
        }
    }
}
