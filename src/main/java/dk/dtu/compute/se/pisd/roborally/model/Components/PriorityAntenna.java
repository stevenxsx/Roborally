package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PriorityAntenna extends FieldAction {
    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
