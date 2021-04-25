package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Laser extends FieldAction {

    private int laserStrength;
    private Heading heading;

    public Heading getHeadin() {
        return heading;
    }


    public int getLaserStrength() {
        return laserStrength;
    }


    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
