package websocket;

import dataaccess.GameDAO;
import dataaccess.AuthDAO;

import com.google.gson.Gson;

public class WebsocketHandler {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;
    private final ConnectionManager connnctions;
    private final Gson gson;

    public WebsocketHandler(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.connnctions = new ConnectionManager();
        this.gson = new Gson();
    }

}
