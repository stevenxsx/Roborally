package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * TODO look better
 * */

public class PitView {
    public static void drawPit(SpaceView spaceView, Space space){
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREY);
        gc.setLineWidth(50);
        gc.setLineCap(StrokeLineCap.SQUARE);
        gc.strokeOval(2,2,70,70);
        spaceView.getChildren().add(canvas);
    }
}
