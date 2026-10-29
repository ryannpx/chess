package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;


import java.sql.ResultSet;
import java.util.ArrayList;

public class MySQLGameDAO implements GameDAO {

    public MySQLGameDAO() throws DataAccessException {
        DatabaseManager.createDatabase();
        String sql = """
                CREATE TABLE IF NOT EXISTS game (
                    gameID INT NOT NULL AUTO_INCREMENT,
                    whiteUsername VARCHAR(255),
                    blackUsername VARCHAR(255),
                    gameName VARCHAR(255) NOT NULL,
                    game LONGTEXT NOT NULL,
                    PRIMARY KEY (gameID)
                )
                """;
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unable to configure database", ex);
        }
    }

    @Override
    public int createGame(String gameName) throws DataAccessException {
        String sql = "INSERT INTO game (gameName, game) VALUES (?, ?)";
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, gameName);
            ps.setString(2, new Gson().toJson(new ChessGame()));
            ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unable to create game", ex);
        }
    }






    private GameData readGame(ResultSet rs) throws SQLException {
        int gameID = rs.getInt("gameID");

        String whiteUsername = rs.getString("whiteUsername");
        String blackUsername = rs.getString("blackUsername");
        String gameName = rs.getString("gameName");

        ChessGame game = new Gson().fromJson(rs.getString("game"), ChessGame.class);

        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        String sql = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game WHERE gameID = ?";
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            // set ? 1 to gameID (it's an int, so use setInt)
            ps.setInt(1, gameID);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return readGame(rs);// return the game, using your helper
                }
                return null;// no row found, so return null
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unable to get game", ex);
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        var games = new ArrayList<GameData>();
        String sql = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game";
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            while (rs.next()) {
                games.add(readGame(rs));// add readGame(rs) to the games list
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unable to list games", ex);
        }
        return games;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {
        String sql = "UPDATE game SET whiteUsername = ?, blackUsername = ?, gameName = ?, game = ? WHERE gameID = ?";
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.setString(1, game.whiteUsername()); // first ?
            ps.setString(2, game.blackUsername()); // second ?
            ps.setString(3, game.gameName()); // third ?
            ps.setString(4, new Gson().toJson(game.game()));
            ps.setInt(5, game.gameID());
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error, unable to update game", ex);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        String sql = "TRUNCATE game"; //sql command
        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            throw new DataAccessException("Error, unable to clear the game", ex);
        }
    }
}