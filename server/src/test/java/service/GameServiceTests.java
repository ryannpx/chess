package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {
    private GameService gameService;
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;
    private final String token = "goodToken";

    @BeforeEach
    public void setup() throws Exception {
        gameDAO = new MemoryGameDAO();
        authDAO = new MemoryAuthDAO();
        gameService = new GameService(gameDAO, authDAO);

        authDAO.createAuth(new AuthData(token, "bob"));
    }


    @Test //positive create a game
    public void createGameSuccess() throws Exception {
        CreateGameResult result = gameService.createGame(token, new CreateGameRequest("myGame"));

        assertTrue(result.gameID() > 0);
        assertNotNull(gameDAO.getGame(result.gameID()));
    }

    @Test //negative create a game w bad token
    public void createGameBadAuth() {
        assertThrows(UnauthorizedException.class, () ->
                gameService.createGame("fakeToken", new CreateGameRequest("myGame")));
    }


    @Test
    public void createBadGameRequest() {
        assertThrows(BadRequestException.class, () ->
                gameService.createGame(token, new CreateGameRequest(null)));
    }

    @Test
    public void listGameSuccess() throws Exception {
        gameService.createGame(token, new CreateGameRequest("game1"));
        gameService.createGame(token, new CreateGameRequest("game2"));

        ListGamesResult result = gameService.listGames(token);

        assertEquals(2, result.games().size());
    }

    @Test
    public void listGamesBadAuth() {
        assertThrows(UnauthorizedException.class, () ->
                gameService.listGames("fakeToken"));
    }

    @Test
    public void joinGameWhiteSuccess() throws Exception {
        int id = gameService.createGame(token, new CreateGameRequest("g")).gameID();

        gameService.joinGame(token, new JoinGameRequest("WHITE", id));

        assertEquals("bob", gameDAO.getGame(id).whiteUsername());
    }

    @Test
    public void joinGameBlackSuccess() throws Exception {
        int id = gameService.createGame(token, new CreateGameRequest("g")).gameID();

        gameService.joinGame(token, new JoinGameRequest("BLACK", id));

        assertEquals("bob", gameDAO.getGame(id).blackUsername());
    }

    @Test
    public void joinGameAlreadyTaken() throws Exception {
        int id = gameService.createGame(token, new CreateGameRequest("g")).gameID();
        gameService.joinGame(token, new JoinGameRequest("WHITE", id));

        assertThrows(AlreadyTakenException.class, () ->
                gameService.joinGame(token, new JoinGameRequest("WHITE", id)));
    }

    @Test
    public void joinGameBadColor() throws Exception {
        int id = gameService.createGame(token, new CreateGameRequest("g")).gameID();

        assertThrows(BadRequestException.class, () ->
                gameService.joinGame(token, new JoinGameRequest("GREEN", id)));
    }

    @Test
    public void joinGameBadGameID() {
        assertThrows(BadRequestException.class, () ->
                gameService.joinGame(token, new JoinGameRequest("WHITE", 999)));
    }

    @Test
    public void joinGameBadAuth() throws Exception {
        int id = gameService.createGame(token, new CreateGameRequest("g")).gameID();

        assertThrows(UnauthorizedException.class, () ->
                gameService.joinGame("fakeToken", new JoinGameRequest("WHITE", id)));
    }
}