package client;

import org.junit.jupiter.api.*;
import server.Server;
import model.RegisterResult;

import model.LoginResult;

import static org.junit.jupiter.api.Assertions.*;
import model.CreateGameResult;
import model.ListGamesResult;

public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;





    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        facade = new ServerFacade(port);
        System.out.println("Started the test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {

        server.stop();
    }

    @BeforeEach
    public void setup() throws Exception {
        facade.clear();
    }



 // register tests
    @Test
    public void registerSuccess() throws Exception {
        RegisterResult result = facade.register("bruh", "password", "bruh@gmail.com");

        assertNotNull(result);
        assertEquals("bruh", result.username());
        assertNotNull(result.authToken());
    }

    @Test
    public void registerDuplicate() throws Exception {
        facade.register("bruh", "password", "bruh@gmail.com");




        assertThrows(ResponseException.class, () ->
                facade.register("bruh", "password", "bruh@gmail.com"));
    }


    //login tests positive neg for login and logout

    @Test
    public void loginSuccess() throws Exception {
        facade.register("bruh", "password", "bruh@gmail.com");

        LoginResult result = facade.login("bruh", "password");


        assertNotNull(result);
        assertEquals("bruh", result.username());
        assertNotNull(result.authToken());
    }

    @Test
    public void loginWrongPassword() throws Exception {
        facade.register("bruh", "password", "bruh@gmail.com");

        assertThrows(ResponseException.class, () ->
                facade.login("bruh", "wrongPassword"));
    }

    @Test
    public void logoutSuccess() throws Exception {
        RegisterResult registered = facade.register("bruh", "password", "bruh@gmail.com");



        facade.logout(registered.authToken());

        assertThrows(ResponseException.class, () ->
                facade.logout(registered.authToken()));
    }

    @Test
    public void logoutBadToken() throws Exception {
        assertThrows(ResponseException.class, () ->
                facade.logout("fake-token"));
    }


    //more tests ugufhurgggh
    @Test
    public void createGameSuccess() throws Exception {
        RegisterResult registered = facade.register("yay", "password", "yay@gmail.com");

        CreateGameResult result = facade.createGame(registered.authToken(), "myGame");

        assertNotNull(result);
        assertTrue(result.gameID() > 0);
    }

    @Test
    public void createGameUnauthorized() throws Exception {
        assertThrows(ResponseException.class, () ->
                facade.createGame("fake-token", "myGame"));
    }



    @Test
    public void listGamesSuccess() throws Exception {
        RegisterResult registered = facade.register("woo", "password", "woo@gmail.com");
        facade.createGame(registered.authToken(), "game1");
        facade.createGame(registered.authToken(), "game2");

        ListGamesResult result = facade.listGames(registered.authToken());

        assertEquals(2, result.games().size());
    }

    @Test
    public void listGamesUnauthorized() throws Exception {
        assertThrows(ResponseException.class, () ->
                facade.listGames("fake-token"));
    }

    @Test
    public void joinGameSuccess() throws Exception {
        RegisterResult registered = facade.register("meow", "password", "meow@gmail.com");
        CreateGameResult created = facade.createGame(registered.authToken(), "myGame");

        facade.joinGame(registered.authToken(), "WHITE", created.gameID());

        ListGamesResult listed = facade.listGames(registered.authToken());
        var game = listed.games().iterator().next();
        assertEquals("meow", game.whiteUsername());
    }

    @Test
    public void joinGameBadColor() throws Exception {
        RegisterResult registered = facade.register("oye", "password", "oye@gmail.com");
        CreateGameResult created = facade.createGame(registered.authToken(), "myGame");

        assertThrows(ResponseException.class, () ->
                facade.joinGame(registered.authToken(), "GREEN", created.gameID()));
    }
}
