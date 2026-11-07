package client;

import org.junit.jupiter.api.*;
import server.Server;
import model.RegisterResult;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;
import static passoff.server.StandardAPITests.serverFacade;


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

}
