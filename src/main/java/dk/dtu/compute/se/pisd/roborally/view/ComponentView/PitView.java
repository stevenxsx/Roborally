package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;



public class PitView {
    public static void drawPit(SpaceView spaceView, FieldAction fieldAction){
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(25);
        gc.setLineCap(StrokeLineCap.SQUARE);
        gc.strokeOval(2,2,48,48);
        spaceView.getChildren().add(canvas);
    }
}
