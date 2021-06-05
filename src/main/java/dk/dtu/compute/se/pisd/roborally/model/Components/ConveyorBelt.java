package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.controller.*;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class ConveyorBelt extends FieldAction {

    private int velocity;

    Heading heading;

    public int getVelocity() {
        return velocity;
    }

    public Heading getHeading() {
        return heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space){

        for(int i = 0; i < velocity; i++) {
            try {
                Space neighbourOfConveyorHeading = gameController.board.getNeighbour(space.getPlayer().getSpace(), this.heading);

                gameController.moveToSpace(space.getPlayer(), neighbourOfConveyorHeading, this.heading);
            }
            catch(Exception e){
                return false;
            }
        }
        return false;
    }
}
