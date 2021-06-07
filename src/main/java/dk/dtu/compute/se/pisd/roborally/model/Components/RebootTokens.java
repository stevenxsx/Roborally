package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RebootTokens extends FieldAction {

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        Board board = gameController.board;

        if (player.NeedReboot()!= false){
            clearPlayerRegister(player);
            CommandCardField field = player.getProgramField(space.getPlayer().board.getStep());
            field.setCard2(new rebootCard(Choose.CHOOSE_HEADING));
            //player.setNeedReboot(false);
            //board.setPhase(Phase.PLAYER_INTERACTION);
        }
        //gameController.Reboot_choose(Choose.CHOOSE_HEADING);
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




    public enum Choose{
        NORTH("North"),
        SOUTH("South"),
        EAST("East"),
        WEST("West"),
        CHOOSE_HEADING("North, South, East or West", Choose.NORTH, Choose.SOUTH, Choose.EAST, Choose.WEST);

        final public String displayName;

        final private List<Choose> options;

        Choose(String displayName, Choose... choose) {
            this.displayName = displayName;
            this.options = Collections.unmodifiableList(Arrays.asList(choose));
        }

        public boolean Interactive() {
            return !options.isEmpty();
        }

        public List<Choose> getOptions() {
            return options;
        }
}

}