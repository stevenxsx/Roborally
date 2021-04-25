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
        try {
            gameController.moveToSpace(space.getPlayer(), space, pushHeading);
        }
        catch(Exception e){}

        return false;
    }
}
