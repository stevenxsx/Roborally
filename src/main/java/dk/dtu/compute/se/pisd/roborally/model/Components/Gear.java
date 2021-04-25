package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Blueprint for Gear component in RoboRally.
 */
public class Gear extends FieldAction {
    private Heading heading;
    
    public Heading getHeading(){
        return heading;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Heading pHeading = space.getPlayer().getHeading();
        Heading gearheading = this.heading;
        switch(gearheading){
            case EAST -> {
                space.getPlayer().setHeading(pHeading.next());
                break;
            }
            case WEST -> {
                space.getPlayer().setHeading(pHeading.prev());
                break;
            }
        }
        return false;
    }
}