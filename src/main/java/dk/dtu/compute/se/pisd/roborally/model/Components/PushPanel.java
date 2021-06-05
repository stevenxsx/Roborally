package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Class for the push panel board element
 */
public class PushPanel extends FieldAction {
    private Heading heading;


    public Heading getHeading(){
        return heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Heading pushHeading = this.heading;
        Space neighourSpace = gameController.board.getNeighbour(space, pushHeading);
        try {
            gameController.moveToSpace(space.getPlayer(), neighourSpace, pushHeading);
            return true;
        }
        catch(Exception e){}

        return false;
    }
}
