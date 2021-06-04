package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Pit extends FieldAction {

    @Override
    //TODO pit programming
    //En pit skal give 2 spam dmg og man skal tømme registeret af resterende kort,
    //samt man sendes til en reboot-token på samme board man er på.
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        //player.setdmg(player.getdmg());
        //tøm registeret
        gameController.moveToSpace(space.getPlayer(), space.board., player.getHeading());




        return false;
    }
}

/*
        for(int i = 0; i < velocity; i++) {
        Space neighbourOfConveyorHeading = gameController.board.getNeighbour(space.getPlayer().getSpace(), this.heading);
        try {
        gameController.moveToSpace(space.getPlayer(), neighbourOfConveyorHeading, this.heading);
        }
        catch(Exception e){
