package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Components.PushPanel;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class PushPanelView {
    /**
     *
     */
    // TODO: Fix headings (reverse)
    public static void drawPushPanel(SpaceView spaceView, FieldAction fa) {
        PushPanel pushPanel = (PushPanel) fa;
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.DARKTURQUOISE);
        gc.setLineWidth(10);
        gc.setLineCap(StrokeLineCap.ROUND);
        switch (pushPanel.getHeading()) {
            case SOUTH -> gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 48, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 48);
            case NORTH -> gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
            case WEST -> gc.strokeLine(48, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 48);
            case EAST -> gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 48, SpaceView.SPACE_HEIGHT - 48);
        }
        spaceView.getChildren().add(canvas);

    }
}
