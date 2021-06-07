package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * @author s205444, Lucas
 */
public class EnergyCube extends FieldAction {
    private int energy;



    public int getEnergy() {
        return this.energy;
    }
    public void setEnergy(int energy){
        this.energy = energy;
    }

    /**
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return boolean used to indicate whether action was executed.
     */
    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        if (player != null) {
            player.setEnergy(this.getEnergy());
            this.setEnergy(0);
            return true;
        }
        return false;
    }
}

