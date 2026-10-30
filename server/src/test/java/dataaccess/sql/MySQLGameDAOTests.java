package dataaccess.sql;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MySQLGameDAOTests {
    private MySQLGameDAO gameDAO;

    @BeforeEach
    public void setup() throws DataAccessException {
        gameDAO = new MySQLGameDAO();
        gameDAO.clear();
    }




    @Test
    public void createGameSuccess() throws Exception {
        int id = gameDAO.createGame("myGame");

        GameData found = gameDAO.getGame(id);


        assertNotNull(found);
        assertEquals("myGame", found.gameName());
    }

    @Test
    public void createGameNullName() throws Exception {

        assertThrows(DataAccessException.class, () -> gameDAO.createGame(null));
    }

    @Test
    public void getGameSuccess() throws Exception {
        int id = gameDAO.createGame("myGame");

        GameData game = gameDAO.getGame(id);


        assertEquals(id, game.gameID());
        assertEquals("myGame", game.gameName());
        assertNull(game.whiteUsername());
        assertNull(game.blackUsername());
        assertNotNull(game.game());
    }

    @Test
    public void getGameNotFound() throws Exception {
        assertNull(gameDAO.getGame(999));
    }

    @Test
    public void listGamesSuccess() throws Exception {
        gameDAO.createGame("game1");
        gameDAO.createGame("game2");

        Collection<GameData> games = gameDAO.listGames();
        assertEquals(2, games.size());
    }

    @Test
    public void listGamesEmpty() throws Exception {
        Collection<GameData> games = gameDAO.listGames();

        assertEquals(0, games.size());
    }

    @Test
    public void updateGameSuccess() throws Exception {
        int id = gameDAO.createGame("myGame");

        gameDAO.updateGame(new GameData(id, "steve", null, "myGame", new ChessGame()));

        GameData updated = gameDAO.getGame(id);

        assertEquals("steve", updated.whiteUsername());
        assertNull(updated.blackUsername());
    }

    @Test
    public void updateGameNullName() throws Exception {
        int id = gameDAO.createGame("myGame");

        assertThrows(DataAccessException.class, () ->
                gameDAO.updateGame(new GameData(id, "steve", null, null, new ChessGame())));
    }

    @Test
    public void clearSuccess() throws Exception {
        gameDAO.createGame("myGame");

        gameDAO.clear();

        assertEquals(0, gameDAO.listGames().size());
    }
}
