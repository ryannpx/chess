package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTests {
    private ClearService clearService;
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;

    @BeforeEach
    public void setup() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        gameDAO = new MemoryGameDAO();
        clearService = new ClearService(userDAO, authDAO, gameDAO);
    }

    @Test
    public void clearSuccess() throws Exception {
        userDAO.createUser(new UserData("bob", "pass", "bob@mail.com"));
        authDAO.createAuth(new AuthData("goodToken", "bob"));
        gameDAO.createGame("g");

        clearService.clear();

        assertNull(userDAO.getUser("bob"));
        assertNull(authDAO.getAuth("goodToken"));
        assertTrue(gameDAO.listGames().isEmpty());
    }
}
