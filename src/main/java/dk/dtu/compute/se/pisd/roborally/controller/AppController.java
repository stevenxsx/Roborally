package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.roborally.RoboRally;

import dk.dtu.compute.se.pisd.roborally.dal.GameInDB;
import dk.dtu.compute.se.pisd.roborally.dal.RepositoryAccess;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
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
public class AppController implements Observer {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    final private RoboRally roboRally;

    private GameController gameController;

    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    public void newGame() {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }

            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            // TODO use method loadBoard(String) from LoadBoard class to create a new board
            Board board = new Board(8,8);
            gameController = new GameController(board); //replace board parameter with loadBoard(DEFAULTBOARD)
            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                board.addPlayer(player);
                player.setSpace(board.getSpace(i % board.width, i));
            }

            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    public void saveGame() {
        boolean savedGame = false;

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Save Game");
        dialog.setHeaderText("If you wish to save, \n please type a name for your game:");
        dialog.setContentText("Enter name");


        Optional<String> result = dialog.showAndWait();

        String Realresult = dialog.getResult();

        List<GameInDB> gameIDs = RepositoryAccess.getRepository().getGames();

        gameController.board.setName(Realresult);

        for(GameInDB gameID : gameIDs){
            if(gameController.board.getGameId() != null) {
                if (gameID.id == gameController.board.getGameId()) {
                    savedGame = true;
                }
            }
        }
        if(savedGame){
            RepositoryAccess.getRepository().updateGameInDB(gameController.board);
        }
        else{
            RepositoryAccess.getRepository().createGameInDB(gameController.board);
        }

    }

    public void loadGame() {
        // XXX needs to be implememted eventually
        // for now, we just create a new game
        if (gameController == null) {
            GameInDB currentGame = null;
            List<GameInDB> gameIDs = RepositoryAccess.getRepository().getGames();
            List<String>  gameName = new ArrayList<String>();
            for(GameInDB game : gameIDs){
                gameName.add(game.name);
            }
            ChoiceDialog<String> dialog = new ChoiceDialog<>(gameName.get(0),gameName);

            dialog.setTitle("Player number");
            dialog.setHeaderText("Select number of players");
            Optional<String> result = dialog.showAndWait();
            for(GameInDB game : gameIDs){
                if(game.name.equals(result.get())){
                    currentGame = game;
                }
            }
            //todo same name
            //todo add a try/catch statement to handle nullpointer exception.
            gameController = new GameController(RepositoryAccess.getRepository().loadGameFromDB(currentGame.id));

            roboRally.createBoardView(gameController);

        }
    }

    /**
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     */
    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user).
            saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    public void exit() {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game
        if (gameController == null || stopGame()) {
            Platform.exit();
        }
    }

    public boolean isGameRunning() {
        return gameController != null;
    }


    @Override
    public void update(Subject subject) {
        // XXX do nothing for now
    }

}

