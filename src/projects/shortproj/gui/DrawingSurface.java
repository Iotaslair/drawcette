package projects.shortproj.gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import projects.shortproj.util.Context;
import java.lang.Math;

public class DrawingSurface extends Pane {

	private SideBar sideBar;
	private ColorBar colorBar;
	private Context context;
	
	public DrawingSurface(SideBar sideBar, ColorBar colorBar, Context context) {
		this.sideBar = sideBar;
		this.colorBar = colorBar;
		this.context = context;
		
		// Add an event handler to the pane that checks what button is pressed for what to do on mouseclick.
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Asks sidebar what button is down to call appropriate function on surface.
                String depressedButton = sideBar.getDepressedButtonGroup1();
                
                switch (depressedButton) {
                	case "line": 	drawLine(event);
                			 	 	break;
                	case "drag": 	dragClick(event);
                				 	break;
                	case "group":	newGroup(event);
                					break;
                	case "rotate":	rotateClick(event);
                					break;
                	case "remove":	removeFromGroup(event);
                					break;
                	default:     System.out.println("Don't know what to do with this click.");
                				 break;
                }
            }
        });
        
        // Add and event handler to handle drags.
        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		String depressedButton = sideBar.getDepressedButtonGroup1();
        		
        		switch (depressedButton) {
        			case "drag":	dragDrag(event);
        							break;
        		}
        	}
        });
        
        // Add an event handler for mouse release.
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		String depressedButton = sideBar.getDepressedButtonGroup1();
        		
        		switch (depressedButton) {
        			case "drag":	dragRelease(event);
        							break;
        		}
        	}
        });
        
     // Add an event handler for mouse move.
        this.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		String depressedButton = sideBar.getDepressedButtonGroup1();
        		
        		switch (depressedButton) {
        			case "rotate":	rotateMove(event);
        							break;
        		}
        	}
        });
	}
	
	
	/*  Function that given a mouse even and a surface advances 
     *  the line drawing process on that surface by one click.
     */
    public void drawLine(MouseEvent event) {
        System.out.println(event.getX() + ", " + event.getY());
        if (context.clickCount == 0){
            context.clickCount++;
            context.storedx = event.getX();
            context.storedy = event.getY();
        }
        else {
            context.clickCount = 0;
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
    
    /* Function that given successive mouse clicks adds nodes to
     *  a new group.
     */
    public void newGroup(MouseEvent event) {
    	if (context.clickCount == 0 || context.storedGroup == null) {
    		context.storedGroup = new Group();
    		this.getChildren().add(context.storedGroup);
    		context.clickCount++;
    	}
    	if (event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface) 
    			&& !context.storedGroup.getChildren().contains(event.getTarget())) {
    		Node node = (Node) event.getTarget();
    		context.storedGroup.getChildren().add(node);
    		System.out.println("Added a Node to a Group!");
    	}
    }
    
    public void removeFromGroup(MouseEvent event) {
    	if (event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
    		this.getChildren().add((Node) event.getTarget());
    		System.out.println("Removed a Node from a group.");
    	}
    }
    
    
    // Object / group rotation functions.
    public void rotateClick(MouseEvent event) {
    	if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface) && context.clickCount == 0) {
			Node node = (Node) event.getTarget();
			if(node.getParent() instanceof Group) node = node.getParent();
			
			context.storedNode = node;
			context.clickCount++;
			System.out.println("Rotating a Node");
				
    	} else if(context.clickCount == 1) {
			Rotate rotate = new Rotate(0, event.getSceneX(), event.getSceneY());
			context.storedNode.getTransforms().add(rotate);
			context.transform = rotate;
			context.clickCount++;
		} else {
			context.clickCount = 0;
		}
    }
    
    // Rotate is a little broken. This function is to blame but the solution is not obvious yet.
    public void rotateMove(MouseEvent event) {
    	if(context.clickCount == 2 && context.transform != null && context.transform instanceof Rotate) {
    		Rotate rotate = (Rotate) context.transform;
    		double angle = Math.toDegrees(Math.atan2(-context.storedy + event.getSceneY(), context.storedx - event.getSceneX()));
    		angle = (angle < 0) ? (360d + angle) : angle;
    		rotate.setAngle(angle);
    	}
    }
    
    
    // Object / group drag functions. Need one for initial click, drag and release.
    public void dragClick(MouseEvent event) {
    	if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
			Node node = (Node) event.getTarget();
			if(node.getParent() instanceof Group) node = node.getParent();
			
			context.storedx = node.getTranslateX() - event.getSceneX();
			context.storedy = node.getTranslateX() - event.getSceneY();
    	}
    }
    
    public void dragDrag(MouseEvent event) {
    	if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
			Node node = (Node) event.getTarget();
			if(node.getParent() instanceof Group) node = node.getParent();
			
			node.setTranslateX(event.getSceneX() + context.storedx);
			node.setTranslateY(event.getSceneY() + context.storedy);
    	}
    }
    
    public void dragRelease(MouseEvent event) {
    	if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
			context.storedx = -1;
			context.storedy = -1;
    	}
    }
}
