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
import dk.dtu.compute.se.pisd.roborally.model.Components.Pit;
import dk.dtu.compute.se.pisd.roborally.model.Components.RebootTokens;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;

    public boolean winnerFound = false;

    final private List<String> UPGRADE_CARDS = Arrays.asList("Brakes", "Virus_Module", "Trojan Needler", "Rear Laser");

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space) {
        // TODO Assignment V1: method should be implemented by the students:
        //   - the current player should be moved to the given space
        //     (if it is free()
        //   - and the current player should be set to the player
        //     following the current player
        //   - the counter of moves in the game should be increased by one
        //     if the player is moved
        Player current_player = board.getCurrentPlayer();
        // Check this not moves the player and they cant land on another person/robot.
        if (space.getPlayer() == null && space.getPlayer() != current_player) { //return null if free
            current_player.setSpace(space);//sets players position
            board.setCounter(board.getCounter() + 1);
            //to change the player
            int number = board.getPlayerNumber(current_player);
            board.setCurrentPlayer(board.getPlayer((number + 1) % board.getPlayersNumber()));
        } else if (space.getPlayer() != current_player) {
            push(space.getPlayer(), current_player.getHeading());
            current_player.setSpace(space);//sets players position
            board.setCounter(board.getCounter() + 1);
            //to change the player
            int number = board.getPlayerNumber(current_player);
            board.setCurrentPlayer(board.getPlayer((number + 1) % board.getPlayersNumber()));
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
                    if(!player.NeedReboot())
                        field.setCard2(null);

                    }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    //if statement to ensure that the player that is rebooting cant get any cards
                    if(!player.NeedReboot()) {
                        if (!player.getDamagecards().isEmpty()) {
                            if (player.getDamagecards().size() > j)
                                field.setCard(new CommandCard(player.getDamagecards().get(j)));
                            else
                                field.setCard(generateRandomCommandCard());
                        }
                        else
                            field.setCard(generateRandomCommandCard());
                    }
                    else
                        field.setCard(null);
                    field.setVisible(true);
                }
            }
        }
    }


    /**
     * @author s205444, Lucas
     * @return returns a command card
     */
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        ArrayList<Command> commandArrayList = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            commandArrayList.add(commands[i]);
        }
        int random = (int) (Math.random() * commandArrayList.size());
        return new CommandCard(commandArrayList.get(random));
    }

    /**
     * @author s205444, Lucas
     * Checks for a winner in the game.
     * @param player takes the current player
     *
     */

    public void chooseWinner(Player player) {
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

    /**
     * @author s205444, Lucas
     * Executes the next steps in the game.
     */
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
                rebootCard choice = currentPlayer.getProgramField(step).getCard2();
                if (card != null) {
                    Command command = card.command;
                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    executeCommand(currentPlayer, command);
                }
                /** @Author Mike
                 * this else if statement is to makesure that the player only can activate the effect of the reboot once
                 */
                else if (choice != null && currentPlayer.NeedReboot()!=false) {
                    RebootTokens.Choose choose = choice.choose;
                    if (choose.Interactive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    PickReboot_heading(currentPlayer, choose);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {

                    for (Player player : this.board.getPlayers()) {
                        for (FieldAction fieldAction : player.getSpace().getActions()) {
                            if (winnerFound) {
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

    /**
     * Executes a specific command for a robot.
     *
     * @param option One of the command ENUMS, i.e. LEFT, RIGHT, FORWARD.
     */
    public void executeCommandOptionAndContinue(@NotNull Command option) {
        Player currentPlayer = board.getCurrentPlayer();
        if (currentPlayer != null && board.getPhase() == Phase.PLAYER_INTERACTION && option != null) {
            board.setPhase(Phase.ACTIVATION);
            executeCommand(currentPlayer, option);


            int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
            if (nextPlayerNumber < board.getPlayersNumber()) {
                board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
            } else {
                int step = board.getStep() + 1;
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


    /**
     * Method for adding an upgrade to a player.
     * @param player the player who will receive the upgrade
     * @param upgrade the type of upgrade to be added to the player
     *
     * Currently using print statements for debugging. This should be changed later
     */
    /*
    private void addUpgrade(@NotNull Player player, Upgrade upgrade) {
        if (player.board == board && upgrade != null) {
            switch (upgrade) {
                case BRAKES:
                    System.out.println(player.addUpgrade(1));
                    break;
                case VIRUS_MODULE:
                    System.out.println(player.addUpgrade(2));
                    break;
                case TROJAN_NEEDLER:
                    System.out.println(player.addUpgrade(3));
                    break;
                case REAR_LASER:
                    System.out.println(player.addUpgrade(4));
                    break;
            }
        }
    }
*/
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {

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
                    break;
                case SPAM:
                    this.spam(player);
                    break;
                case VIRUS:
                    this.virus(player);
                    break;
                case TROJAN_HORS:
                    this.trojan(player);
                    break;
                case WORM:
                    this.worm(player);
                    break;
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    /**
     * Recursive method that moves a player to a certain space depending on the parameters's values.
     * @author s205444 Lucas
     * @param player  the player being moved
     * @param space   the space being moved to.
     * @param heading the heading of the intially moving player.
     * @throws ImpossibleMoveException If a move is not possible, an impossibleMoveException is thrown.
     */
    public void moveToSpace(Player player, Space space, Heading heading) throws ImpossibleMoveException {
        if(space == null){
            Pit tempPit = new Pit();
            tempPit.doAction(this, player.getSpace());
            return;
        }
        if(player.NeedReboot()){
            return;
        }

        Player neighbourPlayer = space.getPlayer();
        boolean hasAnyWalls = player.getSpace().getWalls().isEmpty();

        if (!hasAnyWalls) {
            for (Heading header : player.getSpace().getWalls()) {
                if (player.getHeading() == header) {
                    space = player.getSpace();
                    throw new ImpossibleMoveException(player, space, heading);
                }
            }
        }
        Space target = space;
        boolean targetHasWalls = target.getWalls().isEmpty();
        if (!targetHasWalls) {
            for (Heading header : target.getWalls()) {
                Heading headerlist = header.next().next();
                if (headerlist == heading) {
                    throw new ImpossibleMoveException(player, space, heading);
                }
            }
        }
        for(FieldAction fa: player.getSpace().getActions()){
            if(fa instanceof Pit){
                fa.doAction(this, player.getSpace());
                return;
            }
        }
        if (target != null && neighbourPlayer != null) {
            try {
                Space neighbourSpace = board.getNeighbour(neighbourPlayer.getSpace(), heading);

                moveToSpace(neighbourPlayer, neighbourSpace, heading);
            } catch (Exception e) {
                throw new ImpossibleMoveException(player, space, heading);
            }
        }
        if(target !=null){
            player.setSpace(space);
        }
        else {
            throw new ImpossibleMoveException(player, space, heading);
        }
    }

    /** Moves robot one space forward in the heading it is facing*/

    public void moveForward(@NotNull Player player) {
        Space neighbourSpace = board.getNeighbour(player.getSpace(), player.getHeading());

            try
            {
                moveToSpace(player, neighbourSpace, player.getHeading());
            } catch (ImpossibleMoveException e){
                System.out.println("Move impossible");
            }


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

    /**
     * Moves a player two steps forward.
     * @param player the currentplayer trying to move.
     */

    public void fastForward(@NotNull Player player) {
        for (int i=0;i<2;i++) {
            moveForward(board.getCurrentPlayer());
        }

    }

    /**
     * Moves a player three steps forward.
     * @param player the current player being moved.
     */

    public void tripleForward(@NotNull Player player) {
        moveForward(board.getCurrentPlayer());
        moveForward(board.getCurrentPlayer());
        moveForward(board.getCurrentPlayer());

       /* for (int i=0;i<3;i++) {
            moveForward(board.getCurrentPlayer()); } */

    }
    /**
     *
     * Makes the player move one space backwards
     but does not change heading
     The method should in the future be
     changed as for now there is no boundaries on the board
     @param player the current player.
     */

    public void backUp(@NotNull Player player){
        uTurn(player);
        moveForward(player);
        uTurn(player);

    }

    /** Makes the robot shift heading to the right, but stays on the same space
     * @param player  the current player.
     * */

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

    /** Makes the robot shift heading to the left, but stays on the same space
     * @param player current player
     *
     * */

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
    /** Rotates the players heading 180 degrees
     * @param player the current player.
     * */

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

    /** @Author Mike
     * Made to use in the player Interaction when choosing the heading for the player on the reboot token
     * @param option
     */
    public void Reboot_choose(@NotNull RebootTokens.Choose option){
        Player player = board.getCurrentPlayer();
        if (player != null && board.getPhase() == Phase.PLAYER_INTERACTION && option != null){
            board.setPhase(Phase.ACTIVATION);
            PickReboot_heading(player, option);


            int nextPlayerNumber = board.getPlayerNumber(player) + 1;
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


    /** @Author Mike
     * This method is meant to when the player choose their heading once when they are on the reboot token
      * @param player
     * @param option
     */
    public void PickReboot_heading(@NotNull Player player, RebootTokens.Choose option) {
        if (player.NeedReboot != false &&  player != null && player.board == board && option != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (option) {
                case EAST:
                    this.East(player);
                    break;
                case WEST:
                    this.West(player);
                    break;
                case SOUTH:
                    this.South(player);
                    break;
                case NORTH:
                    this.North(player);
                default:
                    // DO NOTHING (for now)
            }
            player.setNeedReboot(false);
        }

    }

    public void North(@NotNull Player player) {
        player.setHeading(Heading.NORTH);
    }

    public void East(@NotNull Player player) {
        player.setHeading(Heading.EAST);
    }

    public void South(@NotNull Player player) {
        player.setHeading(Heading.SOUTH);
    }

    public void West(@NotNull Player player) {
        player.setHeading(Heading.WEST);
    }


    /**
     * Used to open a shop for upgrade cards. Can be bought if energy is sufficient.
     * @author s205444, Lucas
     */
    public void spam(Player player){
        player.getDamagecards().remove(Command.SPAM);
        CommandCard card = generateRandomCommandCard();
        executeCommand(player,card.command);
    }
    public void trojan(Player player){
        player.getDamagecards().remove(Command.TROJAN_HORS);
        player.getDamagecards().add(Command.SPAM);
        player.getDamagecards().add(Command.SPAM);
        CommandCard card = generateRandomCommandCard();
        executeCommand(player,card.command);
    }

    public void virus(Player player){

    }

    public void worm(Player player) {
        Space space = player.getSpace();
        int step = space.board.getStep();
        for (int i = step+1; i < Player.NO_REGISTERS; i++)
            player.clearRegister(i);
        player.setNeedReboot(true);
        Space rebootSpace;
        Board board = this.board;
        for(int i = 0; i < board.width; i++) {
            for(int k = 0; k < board.height; k++){
                rebootSpace = board.getSpace(i,k);
                for(FieldAction fa: rebootSpace.getActions()){
                    if(fa instanceof RebootTokens){
                        player.setSpace(rebootSpace);
                        player.getDamagecards().add(Command.SPAM);
                        player.getDamagecards().add(Command.SPAM);
                    }
                }
            }
        }
    }
    public void shop(){
        ChoiceDialog<String> dialog = new ChoiceDialog<>(UPGRADE_CARDS.get(0), UPGRADE_CARDS);
        dialog.setTitle("Upgrade shop");
        dialog.setHeaderText("Select a card to buy:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()){
            String upgradeChosen = result.get();
        }
    }

}

