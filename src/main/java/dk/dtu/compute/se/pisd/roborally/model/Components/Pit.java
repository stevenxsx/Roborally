package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
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

        //Clears the register for the player landing on the Pit
        clearRegister(player, space);

        //Working on how to move to a specific token/space spot on the board
        // gameController.moveToSpace(space.getPlayer(), space , player.getHeading());




        return false;
    }

    /** Author Mike
     * Clears the register for the currentplayer on the pit from the next card to execute
     * when they land on the Pit
     * @param player
     * @param space
     */
    public void clearRegister(Player player, Space space){
        int step = space.board.getStep();
        for (int i = step+1; i < Player.NO_REGISTERS; i++)
            player.clearRegister(i);
    }

}


