package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class RebootTokensView {
    public static void drawRebootTokens(SpaceView spaceView, FieldAction fieldAction) {

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(4);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeOval(2,2,45,45);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(0.5);
        gc.strokeText("RbT", SpaceView.SPACE_WIDTH/5, SpaceView.SPACE_WIDTH/2);
        spaceView.getChildren().add(canvas);
    }
}
