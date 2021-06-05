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
package dk.dtu.compute.se.pisd.roborally.dal;

import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import dk.dtu.compute.se.pisd.roborally.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ...
 *
 * @author s205444, Lucas
 *
 */
class Repository implements IRepository {

    private static final String GAME_GAMEID = "gameID";

    private static final String GAME_NAME = "name";

    private static final String GAME_CURRENTPLAYER = "currentPlayer";

    private static final String GAME_PHASE = "phase";

    private static final String GAME_STEP = "step";

    private static final String PLAYER_PLAYERID = "playerID";

    private static final String PLAYER_NAME = "name";

    private static final String PLAYER_COLOUR = "colour";

    private static final String PLAYER_GAMEID = "gameID";

    private static final String PLAYER_POSITION_X = "positionX";

    private static final String PLAYER_POSITION_Y = "positionY";

    private static final String PLAYER_HEADING = "heading";

    private static final String CHECKPOINT = "checkpoint";

    private Connector connector;

    Repository(Connector connector){
        this.connector = connector;
    }

    /**
     * @param game game board used to save details about the board such as name in the database.
     * @return true if game is saved successfully.
     */

    @Override
    public boolean createGameInDB(Board game) {
        if (game.getGameId() == null) {
            Connection connection = connector.getConnection();
            try {
                connection.setAutoCommit(false);

                PreparedStatement ps = getInsertGameStatementRGK();
                // TODO: the name should eventually set by the user
                //       for the game and should be then used
                //       game.getName();
                ps.setString(1, game.getName()); // instead of name
                ps.setNull(2, Types.TINYINT); // game.getPlayerNumber(game.getCurrentPlayer())); is inserted after players!
                ps.setInt(3, game.getPhase().ordinal());
                ps.setInt(4, game.getStep());
                ps.setString(5, game.getBoardName());

                // If you have a foreign key constraint for current players,
                // the check would need to be temporarily disabled, since
                // MySQL does not have a per transaction validation, but
                // validates on a per row basis.
                // Statement statement = connection.createStatement();
                // statement.execute("SET foreign_key_checks = 0");

                int affectedRows = ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (affectedRows == 1 && generatedKeys.next()) {
                    game.setGameId(generatedKeys.getInt(1));
                }
                generatedKeys.close();

                // Enable foreign key constraint check again:
                // statement.execute("SET foreign_key_checks = 1");
                // statement.close();

                createPlayersInDB(game);
				/* TOODO this method needs to be implemented first
				createCardFieldsInDB(game);
				 */
                createCardRegisterInDB(game);

                // since current player is a foreign key, it can oly be
                // inserted after the players are created, since MySQL does
                // not have a per transaction validation, but validates on
                // a per row basis.
                ps = getSelectGameStatementU();
                ps.setInt(1, game.getGameId());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    rs.updateInt(GAME_CURRENTPLAYER, game.getPlayerNumber(game.getCurrentPlayer()));
                    rs.updateRow();
                } else {
                    // TODO error handling
                }
                rs.close();

                connection.commit();
                connection.setAutoCommit(true);
                return true;
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
                System.err.println("Some DB error");

                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException e1) {
                    // TODO error handling
                    e1.printStackTrace();
                }
            }
        } else {
            System.err.println("Game cannot be created in DB, since it has a game id already!");
        }
        return false;
    }

    /**
     * @author s205444, Lucas
     * @param game Object of type Game that is currently being played with.
     * @return true if updated sucesfully.
     */
    @Override
    public boolean updateGameInDB(Board game) {
        assert game.getGameId() != null;

        Connection connection = connector.getConnection();
        try {
            connection.setAutoCommit(false);

            PreparedStatement ps = getSelectGameStatementU();
            ps.setInt(1, game.getGameId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.updateInt(GAME_CURRENTPLAYER, game.getPlayerNumber(game.getCurrentPlayer()));
                rs.updateInt(GAME_PHASE, game.getPhase().ordinal());
                rs.updateInt(GAME_STEP, game.getStep());
                rs.updateRow();
            } else {
                // TODO error handling
            }
            rs.close();
            updateCardsInDB(game);
            updatePlayersInDB(game);

			/* TOODO this method needs to be implemented first
			updateCardFieldsInDB(game);
			*/

            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            // TODO error handling
            e.printStackTrace();
            System.err.println("Some DB error");

            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                // TODO error handling
                e1.printStackTrace();
            }
        }

        return false;
    }

    /**
     * @author s205444, Lucas
     * @param id chosen id amongst the games in the database already.
     * @return returns the current gamestate from the database if no errors occured. Otherwise, returns NULL.
     */
    @Override
    public Board loadGameFromDB(int id) {
        Board game;
        try {
            PreparedStatement ps = getSelectGameStatementU();
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            int playerNo = -1;
            if (rs.next()) {
                game = LoadBoard.loadBoard(null);
                if (game == null) {
                    return null;
                }
                playerNo = rs.getInt(GAME_CURRENTPLAYER);

                game.setPhase(Phase.values()[rs.getInt(GAME_PHASE)]);
                game.setStep(rs.getInt(GAME_STEP));
            } else {
                System.out.println("Error: database is empty");
                return null;
            }
            rs.close();

            game.setGameId(id);
            loadPlayersFromDB(game);
            loadCards(game);

            if (playerNo >= 0 && playerNo < game.getPlayersNumber()) {
                game.setCurrentPlayer(game.getPlayer(playerNo));
            } else {
                System.out.println("Error: Wrong game fetched from database");
                return null;
            }

            return game;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Some DB error - SQL Exception");
        }
        return null;
    }

    @Override
    public List<GameInDB> getGames() {
        List<GameInDB> result = new ArrayList<>();
        try {
            PreparedStatement ps = getSelectGameIdsStatement();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(GAME_GAMEID);
                String name = rs.getString(GAME_NAME);
                result.add(new GameInDB(id,name));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Could not fetch games form database");
            e.printStackTrace();
        }
        return result;
    }

    private void createPlayersInDB(Board game) throws SQLException {
        try {
            PreparedStatement ps = getSelectPlayersStatementU();
            ps.setInt(1, game.getGameId());

            ResultSet rs = ps.executeQuery();
            for (int i = 0; i < game.getPlayersNumber(); i++) {
                Player player = game.getPlayer(i);
                rs.moveToInsertRow();
                rs.updateInt(PLAYER_GAMEID, game.getGameId());
                rs.updateInt(PLAYER_PLAYERID, i);
                rs.updateString(PLAYER_NAME, player.getName());
                rs.updateString(PLAYER_COLOUR, player.getColor());
                rs.updateInt(PLAYER_POSITION_X, player.getSpace().x);
                rs.updateInt(PLAYER_POSITION_Y, player.getSpace().y);
                rs.updateInt(PLAYER_HEADING, player.getHeading().ordinal());
                rs.updateInt(CHECKPOINT, player.getCheckpoints());

                rs.insertRow();
            }

            rs.close();
        }
        catch(SQLException e){
            System.out.print("Could not save game. An error occurred.");
            e.printStackTrace();
        }
    }

    private void createCardRegisterInDB(Board game) throws SQLException{
        PreparedStatement ps = getSelectPlayerHandStatement();
        ps.setInt(1, game.getGameId());
        ResultSet rs = ps.executeQuery();


        for (int i = 0; i < game.getPlayersNumber(); i++) {
            for(int j=0; j<8; j++) {
                rs.moveToInsertRow();
                rs.updateInt(PLAYER_GAMEID, game.getGameId());
                rs.updateInt(PLAYER_PLAYERID, i);
                rs.updateInt("Number", j);
                CommandCard commandCard = game.getPlayer(i).getCardField(j).getCard();
                if(commandCard != null){
                    rs.updateInt("Ordinal", commandCard.command.ordinal());
                }
                else{
                    rs.updateInt("Ordinal", -5);
                }
                rs.insertRow();
            }
        }
        rs.close();

        PreparedStatement ps2 = getSelectPlayerRegisterStatement();
        ps2.setInt(1, game.getGameId());
        ResultSet rs2 = ps2.executeQuery();

        for (int n = 0; n < game.getPlayersNumber(); n++){
            Player player = game.getPlayer(n);
            for(int k = 0; k < 5; k++){
                rs2.moveToInsertRow();
                rs2.updateInt(PLAYER_GAMEID, game.getGameId());
                rs2.updateInt(PLAYER_PLAYERID, n);
                rs2.updateInt("RegNumber", k);

                CommandCard commandCard = game.getPlayer(n).getProgramField(k).getCard();
                if(commandCard != null){
                    rs2.updateInt("Ordinal", commandCard.command.ordinal());
                }
                else{
                    rs2.updateInt("Ordinal",-99);
                }
                rs2.insertRow();
            }

        }
        rs2.close();
    }

    private void loadPlayersFromDB(Board game) throws SQLException {
        PreparedStatement ps = getSelectPlayersASCStatement();
        ps.setInt(1, game.getGameId());

        ResultSet rs = ps.executeQuery();
        int i = 0;
        while (rs.next()) {
            int playerId = rs.getInt(PLAYER_PLAYERID);
            if (i++ == playerId) {
                // TODO this should be more defensive
                String name = rs.getString(PLAYER_NAME);
                String colour = rs.getString(PLAYER_COLOUR);
                Player player = new Player(game, colour ,name);
                game.addPlayer(player);

                int x = rs.getInt(PLAYER_POSITION_X);
                int y = rs.getInt(PLAYER_POSITION_Y);
                player.setSpace(game.getSpace(x,y));
                int heading = rs.getInt(PLAYER_HEADING);
                player.setHeading(Heading.values()[heading]);

                // TODO  should also load players program and hand here
            } else {
                // TODO error handling
                System.err.println("Game in DB does not have a player with id " + i +"!");
            }
        }
        rs.close();
    }

    private void loadCards(Board game) throws SQLException{
        PreparedStatement ps = getSelectPlayerRegisterStatement();
        ps.setInt(1, game.getGameId());
        ResultSet rs = ps.executeQuery();

        Command[] cArray = Command.values();
        while (rs.next()){
            int ID = rs.getInt(PLAYER_PLAYERID);
            int cardOrdinal = rs.getInt("Ordinal");
            int number = rs.getInt("RegNumber");
            if(cardOrdinal >= 0){
                game.getPlayer(ID).getProgramField(number).setCard(new CommandCard(cArray[cardOrdinal]));
            }
        }
        rs.close();
        PreparedStatement ps2 = getSelectPlayerHandStatement();
        ps2.setInt(1, game.getGameId());
        ResultSet rs2 = ps2.executeQuery();
        while(rs2.next()){
            int ID = rs2.getInt(PLAYER_PLAYERID);
            int cardOrdinal = rs2.getInt("Ordinal");
            int number = rs2.getInt("Number");
            if(cardOrdinal >= 0){
                game.getPlayer(ID).getCardField(number).setCard(new CommandCard(cArray[cardOrdinal]));
            }
        }
        rs2.close();
    }

    private void updateCardsInDB(Board game) throws SQLException{

        try {
            PreparedStatement ps1 = getSelectPlayerHandStatement();
            ps1.setInt(1, game.getGameId());


            ResultSet rs1 = ps1.executeQuery();
            int m = 0;
                while (rs1.next()) {
                    int playerId = rs1.getInt(PLAYER_PLAYERID);
                    Player player = game.getPlayer(playerId);
                    if(m == 8){
                        m = 0;
                    }
                        //rs1.updateInt("Number", m);
                        CommandCard cmdCard = player.getCardField(m).getCard();
                        if (cmdCard != null) {
                            rs1.updateInt("Ordinal", player.getCardField(m).getCard().command.ordinal());
                        } else {
                            rs1.updateInt("Ordinal", -99);
                        }
                    rs1.updateRow();
                        m++;
                }

            rs1.close();

            PreparedStatement ps2 = getSelectPlayerRegisterStatement();
            ps2.setInt(1, game.getGameId());

            ResultSet rs2 = ps2.executeQuery();
            m = 0;
                while (rs2.next()) {
                    int playerId = rs2.getInt(PLAYER_PLAYERID);
                    Player player = game.getPlayer(playerId);
                    if(m == 5){
                        m = 0;
                    }
                        rs2.moveToCurrentRow();
                        //rs2.updateInt("RegNumber", m);
                        CommandCardField cmdCard = player.getProgramField(m);
                        if (cmdCard.getCard() != null) {
                            rs2.updateInt("Ordinal", player.getProgramField(m).getCard().command.ordinal());
                        } else {
                            rs2.updateInt("Ordinal", -99);
                        }

                    rs2.updateRow();
                        m++;
                }

            rs2.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private void updatePlayersInDB(Board game) throws SQLException {
        try {
            PreparedStatement ps = getSelectPlayersStatementU();
            ps.setInt(1, game.getGameId());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int playerId = rs.getInt(PLAYER_PLAYERID);
                // TODO should be more defensive
                Player player = game.getPlayer(playerId);
                // rs.updateString(PLAYER_NAME, player.getName()); // not needed: player's names does not change
                rs.updateInt(PLAYER_POSITION_X, player.getSpace().x);
                rs.updateInt(PLAYER_POSITION_Y, player.getSpace().y);
                rs.updateInt(PLAYER_HEADING, player.getHeading().ordinal());
                rs.updateInt("checkpoint", player.getCheckpoints());
                rs.updateRow();
            }
            rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static final String SQL_INSERT_GAME =
            "INSERT INTO Game(name, currentPlayer, phase, step, board) VALUES (?, ?, ?, ?, ?)";

    private PreparedStatement insert_game_stmt = null;

    private PreparedStatement getInsertGameStatementRGK() {
        if (insert_game_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                insert_game_stmt = connection.prepareStatement(
                        SQL_INSERT_GAME,
                        Statement.RETURN_GENERATED_KEYS);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return insert_game_stmt;
    }

    private static final String SQL_SELECT_GAME =
            "SELECT * FROM Game WHERE gameID = ?";

    private PreparedStatement select_game_stmt = null;

    private PreparedStatement getSelectGameStatementU() {
        if (select_game_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_game_stmt = connection.prepareStatement(
                        SQL_SELECT_GAME,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_game_stmt;
    }

    private static final String SQL_SELECT_PLAYERS =
            "SELECT * FROM Player WHERE gameID = ?";

    private PreparedStatement select_players_stmt = null;

    private PreparedStatement getSelectPlayersStatementU() {
        if (select_players_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_players_stmt = connection.prepareStatement(
                        SQL_SELECT_PLAYERS,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_players_stmt;
    }

    private static final String SQL_CREATE_PLAYER_HAND =
            "INSERT INTO playerHand(playerID, gameID, card0, card1, card2, card3, card4, card5, card6, card7) VALUES (?,?,?,?,?,?,?,?,?,?)";

    private PreparedStatement create_player_hand_stmt = null;

    private PreparedStatement getCreatePlayerHandStatement() {
        if (create_player_hand_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                create_player_hand_stmt = connection.prepareStatement(SQL_CREATE_PLAYER_HAND);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return create_player_hand_stmt;
    }

    private static final String SQL_SELECT_PLAYER_HAND =
            "SELECT * FROM PlayerHand WHERE gameID = ?";

    private PreparedStatement select_player_hand_stmt = null;

    private PreparedStatement getSelectPlayerHandStatement() {
        if (select_player_hand_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_player_hand_stmt = connection.prepareStatement(SQL_SELECT_PLAYER_HAND,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_player_hand_stmt;
    }

    private static final String SQL_SELECT_PLAYER_REGISTER =
            "SELECT * FROM PlayerRegister WHERE gameID = ?";

    private PreparedStatement select_player_register_stmt = null;

    private PreparedStatement getSelectPlayerRegisterStatement() {
        if (select_player_register_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_player_register_stmt = connection.prepareStatement(SQL_SELECT_PLAYER_REGISTER,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_player_register_stmt;
    }

    private static final String SQL_CREATE_PLAYER_REGISTER =
            "INSERT INTO playerRegister(playerID, gameID, card0, card1, card2, card3, card4) VALUES (?,?,?,?,?,?,?)";

    private PreparedStatement create_player_register_stmt = null;

    private PreparedStatement getCreatePlayerRegisterStatement() {
        if (create_player_register_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                create_player_register_stmt = connection.prepareStatement(SQL_CREATE_PLAYER_REGISTER);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return create_player_register_stmt;
    }

    private static final String SQL_SELECT_PLAYERS_ASC =
            "SELECT * FROM Player WHERE gameID = ? ORDER BY playerID ASC";

    private PreparedStatement select_players_asc_stmt = null;

    private PreparedStatement getSelectPlayersASCStatement() {
        if (select_players_asc_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                // This statement does not need to be updatable
                select_players_asc_stmt = connection.prepareStatement(
                        SQL_SELECT_PLAYERS_ASC);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_players_asc_stmt;
    }

    private static final String SQL_SELECT_GAMES =
            "SELECT gameID, name FROM Game";

    private PreparedStatement select_games_stmt = null;

    private PreparedStatement getSelectGameIdsStatement() {
        if (select_games_stmt == null) {
            Connection connection = connector.getConnection();
            try {
                select_games_stmt = connection.prepareStatement(
                        SQL_SELECT_GAMES);
            } catch (SQLException e) {
                // TODO error handling
                e.printStackTrace();
            }
        }
        return select_games_stmt;
    }
}
