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

        //Bør give dmg til robotten der falder i Pit
        //player.setdmg(player.getdmg());

        //burde tømme registeret
        int step = space.board.getStep();
        for (int i = step; i < 5; i++)
        player.clearProgramField(i);

        //Working on how to move to a specific token/space spot on the board
        // gameController.moveToSpace(space.getPlayer(), space , player.getHeading());




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

*/
