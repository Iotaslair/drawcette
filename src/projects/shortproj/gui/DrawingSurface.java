package projects.shortproj.gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import projects.shortproj.util.Context;
import projects.shortproj.util.ElementGroup;
import java.lang.Math;
import java.util.Optional;

public class DrawingSurface extends Pane {

	private Context context;
	private int groupID, prevGroupID;
	
	Group freeHand;
	Path path;	
	
	public DrawingSurface(Context c) {
		this.context = c;
		groupID = prevGroupID = -1;
		path = new Path();

		// Add an event handler to the pane that checks what button is pressed for what to do on mouseclick.
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Asks sidebar what button is down to call appropriate function on surface.
                String depressedButton = context.menuBox.getDepressedButtonGroup1();
                
                switch (depressedButton) {
                	case "freehand": freeHandDraw(event);
                					break;
                	case "line": 	lineClick(event);
                			 	 	break;
                	case "drag": 	dragClick(event);
                				 	break;
                	case "group":	newGroup(event);
                					break;
                	case "rotate":	rotateClick(event);
                					break;
                	case "remove":	removeFromGroup(event);
                					break;
                    case "delete":  delete(event);
                                    break;
                    case "text":	textClick(event);
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
        		String depressedButton = context.menuBox.getDepressedButtonGroup1();
        		
        		switch (depressedButton) {
        			case "drag":	dragDrag(event);
        							break;
        			case "freehand": freeHandDrawDrag(event);
        							break;
        		}
        	}
        });
        
        // Add an event handler for mouse release.
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		String depressedButton = context.menuBox.getDepressedButtonGroup1();
        		
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
        		String depressedButton = context.menuBox.getDepressedButtonGroup1();
        		
        		switch (depressedButton) {
        			case "rotate":	rotateMove(event);
        							break;
        			case "line":	lineMove(event);
        							break;
        		}
        	}
        });
	}
	
	public void freeHandDraw(MouseEvent event) {
		freeHand = new Group();
		path = new Path();
		
		this.getChildren().add(freeHand);
		freeHand.getChildren().add(path);
		
		path.setStrokeWidth(context.menuBox.getThiccness());
		path.setStroke(context.colorPicker.getColor());

		path.getElements().clear();
        path.getElements().add(new MoveTo(event.getX(), event.getY()));
	}
	
	public void freeHandDrawDrag(MouseEvent event) {
		path.getElements().add(new LineTo(event.getX(), event.getY()));
	}
		
	/*  Function that given a mouse even and a surface advances 
     *  the line drawing process on that surface by one click.
     */
    public void lineClick(MouseEvent event) {
        if (context.clickCount == 0){
            context.clickCount++;
            
            Line line = new Line();
            
            line.setStroke(context.colorPicker.getColor());
            line.setStrokeWidth(context.menuBox.getThiccness());
            
            line.setStartX(event.getX());
            line.setStartY(event.getY());
            
    		line.setEndX(event.getX());
    		line.setEndY(event.getY());
            
            this.getChildren().add(line);
            context.storedNode = line;
        }
        else {
        	// Reset the context
            context.clickCount = 0;

            context.storedNode = null;
                                
            context.storedx = -1;
            context.storedy = -1;
        }
    }
    
    public void lineMove(MouseEvent event) {
    	if (context.clickCount == 1) {
    		Line line = (Line) context.storedNode;
    		line.setEndX(event.getX());
    		line.setEndY(event.getY());
    	}
    }
    
    /* Function that given successive mouse clicks adds nodes to
     *  a new group.
     */
    public void newGroup(MouseEvent event) {
    	if (context.clickCount == 0 || context.storedGroup == null) {
    		context.storedGroup = new Group();
    		groupID++;
    		this.getChildren().add(context.storedGroup);
    		context.clickCount++;
    	}
    	if (event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface) 
    			&& !context.storedGroup.getChildren().contains(event.getTarget())) {
    		Node node = (Node) event.getTarget();
    		context.storedGroup.getChildren().add(node);
    		System.out.println("Added a Node to a Group!");
    		if (groupID == prevGroupID) {
        		//context.sidebarRight.items.get(groupID).getGroup().getChildren().add(node);
        	}
        	else {
        		context.sidebarRight.items.add(new ElementGroup(context.storedGroup, "Group " + groupID));
        		prevGroupID = groupID;
        	}
    	}
    }
    
    
    public void removeFromGroup(MouseEvent event) {
    	if (event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
    		Node node = (Node) event.getTarget();
    		
    		// Move the group transforms down onto the removed element.
    		node.getTransforms().addAll(node.getParent().getTransforms());
    		this.getChildren().add(node);
    		
    		// If group is now empty delete it.
    		if(node.getParent().getChildrenUnmodifiable().isEmpty()) this.getChildren().remove(node.getParent());
    		
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
    		double angle = Math.toDegrees(Math.atan2(context.storedy - event.getSceneY(), context.storedx - event.getSceneX()));
    		angle = (angle < 0) ? (360d + angle) : angle;
    		rotate.setAngle(angle);
    	}
    }
    
    
    // Object / group drag functions. Need one for initial click, drag and release.
    public void dragClick(MouseEvent event) {
    	if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
			Node node = (Node) event.getTarget();
			if(node.getParent() instanceof Group) node = node.getParent();
			
			context.transform = new Translate();
			node.getTransforms().add(context.transform);
			context.storedx = node.getTranslateX() - event.getSceneX();
			context.storedy = node.getTranslateX() - event.getSceneY();
    	}
    }
    
    public void dragDrag(MouseEvent event) {
    	if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
			Node node = (Node) event.getTarget();
			if(node.getParent() instanceof Group) node = node.getParent();
			Translate transform = (Translate) context.transform;
			
			transform.setX(event.getSceneX() + context.storedx);
			transform.setY(event.getSceneY() + context.storedy);
    	}
    }
    
    public void dragRelease(MouseEvent event) {
    	if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
			context.storedx = -1;
			context.storedy = -1;
			
			context.transform = null;
    	}
    }
    //delete function (Allows for deletion of groups)
    public void delete(MouseEvent event)
    {
        Node node = (Node) event.getTarget();
        if(node.getParent() instanceof Group) node = node.getParent();
        this.getChildren().remove(node);
    }
    
    public void textClick(MouseEvent event) {
    	TextInputDialog dialog = new TextInputDialog("text");
    	dialog.setTitle("Text Input Dialog");
    	dialog.setHeaderText("Look, a Text Input Dialog");
    	dialog.setContentText("Please enter desired text:");
    	
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    Text text = new Text();
    	    text.setText(result.get());
    	    
    	    text.setX(event.getX());
    	    text.setY(event.getY());
    	    
    	    text.setStroke(context.colorPicker.getColor());
    	    text.setScaleX(context.menuBox.getThiccness());
    	    text.setScaleY(context.menuBox.getThiccness());
    	    
    	    this.getChildren().add(text);
    	}
    }
}
