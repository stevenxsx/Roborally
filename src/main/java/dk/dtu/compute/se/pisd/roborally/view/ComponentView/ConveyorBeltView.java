package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Components.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ConveyorBeltView {
    public static void drawConveyorBeltView(SpaceView spaceView, Space space) {
        ConveyorBelt tempSpace = (ConveyorBelt) space;
        spaceView.getChildren().clear();

            Polygon arrow = new Polygon(1.0, 1.0,
                    25.0, 50.0,
                    50.0, 0.0 );
            arrow.setFill(Color.BLUE);
            arrow.setRotate((90*tempSpace.getHeading().ordinal())%360);
            spaceView.getChildren().add(arrow);
    }
}
