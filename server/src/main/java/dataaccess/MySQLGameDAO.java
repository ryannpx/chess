package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.xml.crypto.Data;

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


        String json = new Gson().toJson(chessGame);
        ChessGame chessGame = new Gson().fromJson(json, ChessGame.class);


        try (var connection = DatabaseManager.getConnection();
             var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // ... set the ?s ...
            ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);   // the new gameID
            }
        }


        @Override
        public void createGame (String gameName) throws DataAccessException {
            String sql = "INSERT INTO game (gameName, game) VALUES (?, ?)";
            try (var connection = DatabaseManager.getConnection();
                 var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, game.gameName()); // first ?
                ps.setString(2, new Gson().toJson(new ChessGame()); // second ?
                ps.executeUpdate();
            }
            try (var rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
            catch (SQLException ex) {
                throw new DataAccessException("Error, unable to create game", ex);
            }
        }
    }
