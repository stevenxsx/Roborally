/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;

    public boolean winnerFound = false;

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        // TODO Assignment V1: method should be implemented by the students:
        //   - the current player should be moved to the given space
        //     (if it is free()
        //   - and the current player should be set to the player
        //     following the current player
        //   - the counter of moves in the game should be increased by one
        //     if the player is moved
        Player current_player = board.getCurrentPlayer();
        // Check this not moves the player and they cant land on another person/robot.
        if (space.getPlayer() == null && space.getPlayer() != current_player){ //return null if free
            current_player.setSpace(space);//sets players position
            board.setCounter(board.getCounter()+1);
            //to change the player
            int number = board.getPlayerNumber(current_player);
            board.setCurrentPlayer(board.getPlayer((number+1) % board.getPlayersNumber() ));
        }
        else if (space.getPlayer() != current_player){
            push(space.getPlayer(), current_player.getHeading());
            current_player.setSpace(space);//sets players position
            board.setCounter(board.getCounter()+1);
            //to change the player
            int number = board.getPlayerNumber(current_player);
            board.setCurrentPlayer(board.getPlayer((number+1) % board.getPlayersNumber() ));
        }
    }

    // XXX: V2
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    public void endOfTurn(@NotNull Player player) throws ImpossibleMoveException{
        Space playerSpace = player.getSpace();
        /*
        if(playerSpace instanceof ConveyorBelt){
            Heading conveyorHeading = ((ConveyorBelt) playerSpace).getHeading();
            int velocity = ((ConveyorBelt) playerSpace).getVelocity();
            for(int i = 0; i < velocity; i++) {
                Space neighbourOfConveyorHeading = board.getNeighbour(player.getSpace(), ((ConveyorBelt) playerSpace).getHeading());
                moveToSpace(player, neighbourOfConveyorHeading, conveyorHeading);
            }
        }

        if(playerSpace instanceof Gear){
            Heading pHeading = player.getHeading();
            Heading gearheading = ((Gear) playerSpace).getHeading();
            switch(gearheading){
                case EAST -> {
                    player.setHeading(pHeading.next());
                    break;
                }
                case WEST -> {
                    player.setHeading(pHeading.prev());
                    break;
                }
            }
        }

        if(playerSpace instanceof PushPanel){
            Heading pushHeading = ((PushPanel) playerSpace).getHeading();
            moveToSpace(player, playerSpace, pushHeading);
        }

        if(playerSpace instanceof Pit){
            //TODO fix pit
            //Do nothing for now - needs to be implemented with reboot token?
        }
        if(playerSpace instanceof Checkpoint){
            if(player.getCheckpoints() == ((Checkpoint) playerSpace).getCheckpoints() -1){
                player.setCheckpoints(player.getCheckpoints()+1);
            }
        }

         */


    }


    // XXX: V2
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    /**
     *
     *
     * @param player takes the current player
     *
     */

    public void chooseWinner(Player player){
        Alert winMsg = new Alert(Alert.AlertType.INFORMATION, "Player \"" + player.getName() + "\" won.");
        this.winnerFound = true;
        winMsg.showAndWait();
    }

    // XXX: V2
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    // XXX: V2
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    // XXX: V2
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX: V2
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    // XXX: V2
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    // XXX: V2
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX: V2
    private void executeNextStep() {
        /*
        try {
            endOfTurn(board.getCurrentPlayer());
        }
        catch(ImpossibleMoveException m){
            System.out.println("Move exception");
        }
         */
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    if(command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {

                    for(Player player : this.board.getPlayers()){
                        for(FieldAction fieldAction : player.getSpace().getActions()){
                            if(winnerFound){
                                break;
                            }
                            fieldAction.doAction(this, player.getSpace());
                        }
                    }
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    public void executeCommandOptionAndContinue(@NotNull Command option){
        Player currentPlayer = board.getCurrentPlayer();
        if (currentPlayer != null && board.getPhase() == Phase.PLAYER_INTERACTION && option != null){
            board.setPhase(Phase.ACTIVATION);
            executeCommand(currentPlayer, option);


            int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
            if (nextPlayerNumber < board.getPlayersNumber()) {
                board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
            } else {
                int step = board.getStep() +1;
                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step);
                    board.setStep(step);
                    board.setCurrentPlayer(board.getPlayer(0));
                } else {
                    startProgrammingPhase();
                }
            }
        }

    }

    // XXX: V2
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                case TRIPLE_FORWARD:
                    this.tripleForward(player);
                    break;
                case U_TURN:
                    this.uTurn(player);
                    break;
                case BACK_UP:
                    this.backUp(player);
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    public void moveToSpace(Player player, Space space, Heading heading) throws ImpossibleMoveException {
        Player neighbourPlayer = space.getPlayer();
        boolean movePossible = true;
        boolean hasAnyWalls = player.getSpace().getWalls().isEmpty();

        if (!hasAnyWalls){
            for(Heading header: player.getSpace().getWalls()){
                if (player.getHeading() == header){
                    movePossible = false;
                    space = player.getSpace();
                    break;
                }
            }
        }

        if(movePossible && neighbourPlayer!= null ){
            Space target = space;
            boolean targetHasWalls = target.getWalls().isEmpty();
            if (target != null && targetHasWalls){
                try {
                    moveToSpace(neighbourPlayer, target, heading);
                }
                catch(Exception e){
                    System.out.println("Player is null");
                }
            }
            else if(targetHasWalls){
                //List<Heading> wallHeadings2 = ((Wall) target).getHeading();

                for(Heading header: target.getWalls()){
                    Heading headerlist = header.next().next();
                    if (headerlist == heading){
                        movePossible = false;
                    }
                }
                if (movePossible){
                    moveToSpace(neighbourPlayer,target,heading);
                }

            }
            else{
                throw new ImpossibleMoveException(player,space,heading);
            }
        }
        if(movePossible)
        player.setSpace(space);
    }

    /** Moves robot one space forward in the heading it is facing*/

    public void moveForward(@NotNull Player player) {
        Space current = player.getSpace();
        Space neighbourSpace = board.getNeighbour(player.getSpace(), player.getHeading());
        if(neighbourSpace != null)
        {
            try
            {
                moveToSpace(player, neighbourSpace, player.getHeading());
            } catch (ImpossibleMoveException e){
            }

        }/*
        if(current!= null && player.board == current.board){
            Space target = board.getNeighbour(current,player.getHeading());
            if (target.getPlayer() != null){
                push(target.getPlayer(), current.getPlayer().getHeading());
                player.setSpace(target);
            }
            if (target != null && target.getPlayer() == null){
                player.setSpace(target);
            }
        }*/
    }

    public void push(@NotNull Player player, Heading direction) {
        Space current = player.getSpace();
        Space target = board.getNeighbour(current, direction);
         while(target.getPlayer()!= null) {
             Space currenttemp1 = target.getPlayer().getSpace();
             Space temp1 = board.getNeighbour(currenttemp1, direction);
             while(temp1.getPlayer()!= null) {
                 Space currenttemp2 = temp1.getPlayer().getSpace();
                 Space temp2 = board.getNeighbour(currenttemp2, direction);
                 while(temp2.getPlayer()!= null) {
                     Space currenttemp3 = temp2.getPlayer().getSpace();
                     Space temp3 = board.getNeighbour(currenttemp3, direction);
                     while(temp3.getPlayer()!= null) {
                         Space currenttemp4 = temp3.getPlayer().getSpace();
                         Space temp4 = board.getNeighbour(currenttemp4, direction);
                         currenttemp4.getPlayer().setSpace(temp4);
                     }
                     currenttemp3.getPlayer().setSpace(temp3);
                 }
                 currenttemp2.getPlayer().setSpace(temp2);
             }
             currenttemp1.getPlayer().setSpace(temp1);
         }
        current.getPlayer().setSpace(target);
    }

    /** Moves robot 2 spaces forward in the heading it is facing */

    public void fastForward(@NotNull Player player) {
        for (int i=0;i<2;i++) {
            moveForward(board.getCurrentPlayer());
        }

    }
    /** Moves robot 3 spaces forward in the heading it is facing*/

    public void tripleForward(@NotNull Player player) {
        moveForward(board.getCurrentPlayer());
        moveForward(board.getCurrentPlayer());
        moveForward(board.getCurrentPlayer());

       /* for (int i=0;i<3;i++) {
            moveForward(board.getCurrentPlayer()); } */

    }
    /** Makes the player move one space backwards
     but does not change heading
     The method should in the future be
     changed as for now there is no boundaries on the board*/

    public void backUp(@NotNull Player player){
        uTurn(player);
        moveForward(player);
        uTurn(player);

    }

    /** Makes the robot shift heading to the right, but stays on the same space  */

    public void turnRight(@NotNull Player player) {
        switch (player.getHeading()) {
            case SOUTH:
                player.setHeading(Heading.WEST);
                break;
            case WEST:
                player.setHeading(Heading.NORTH);
                break;
            case NORTH:
                player.setHeading(Heading.EAST);
                break;
            case EAST:
                player.setHeading(Heading.SOUTH);
        }
    }

    /** Makes the robot shift heading to the left, but stays on the same space */

    public void turnLeft(@NotNull Player player) {
        switch (player.getHeading()) {
            case SOUTH:
                player.setHeading(Heading.EAST);
                break;
            case EAST:
                player.setHeading(Heading.NORTH);
                break;
            case NORTH:
                player.setHeading(Heading.WEST);
                break;
            case WEST:
                player.setHeading(Heading.SOUTH);
        }
    }
    /** Rotates the players heading 180 degrees*/

    public void uTurn(@NotNull Player player){
        switch (player.getHeading()){
            case SOUTH:
                player.setHeading(Heading.NORTH);
                break;
            case EAST:
                player.setHeading(Heading.WEST);
                break;
            case WEST:
                player.setHeading(Heading.EAST);
                break;
            case NORTH:
                player.setHeading(Heading.SOUTH);

        }

    }



    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

}