package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.DamageCards;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Laser extends FieldAction {

    private int laserStrength;
    private Heading heading;
    boolean middle;

    public Heading getHeading() {
        return heading;
    }


    public int getLaserStrength() {
        return laserStrength;
    }

    public boolean getMiddle(){
        return middle;
    }


    @Override
    public boolean doAction(GameController gameController, Space space) {

        for (int i = 0; i < laserStrength; i++) {
            Player player = space.getPlayer();
            try {
                player.getSpace();
                DamageCards.SPAMCard(player);
            } catch (Exception e) {

            }
        }
        return false;
    }
}