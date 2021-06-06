package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

/**
 * @author Mike
 */
public class RebootTokensView {
    /**
     * Updates the view of a space with reboot tokens.
     * @param spaceView Used to update the view.
     * @param fieldAction Not needed here - delete at some point.
     */
    public static void drawRebootTokens(SpaceView spaceView, FieldAction fieldAction) {

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image rebootToken = new Image("Components/Reboot.png", 50,50,true,true);
        gc.drawImage(rebootToken,0,0);

        spaceView.getChildren().add(canvas);
    }
}
