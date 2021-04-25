package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Checkpoint extends FieldAction {
    private int checkpoints;

    public void setCheckpoints(int setterValue) {
        this.checkpoints = setterValue;
    }

    public int getCheckpoints() {
        return checkpoints;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        if (player != null) {
            if (player.getCheckpoints() == this.checkpoints - 1) {
                space.getPlayer().setCheckpoints(space.getPlayer().getCheckpoints() + 1);
            }

            if(player.getCheckpoints() == gameController.board.getCheckpointCounter());
            gameController.chooseWinner(player);
        }
        return false;
    }
}


