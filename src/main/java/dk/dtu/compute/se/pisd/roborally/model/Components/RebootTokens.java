package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class RebootTokens extends FieldAction {

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();

        clearPlayerRegister(player);

       //player.getProgramField(player.NO_REGISTERS).setCard(new CommandCard(Command.CHOOSE_HEADING));




        return false;
    }

    /** @Auther Mike
     * This method should remove all the cards in the players hand and register so no action with them should be able
     * @param player
     */
    public void clearPlayerRegister(Player player){
        for (int i = 0; i < Player.NO_REGISTERS; i++)
            player.clearRegister(i);
        for (int j = 0; j < Player.NO_CARDS; j++)
            player.clearCards(j);
    }
}
