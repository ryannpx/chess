package websocket;

import dataaccess.GameDAO;
import dataaccess.AuthDAO;

import com.google.gson.Gson;

import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsMessageContext;

import model.AuthData;
import model.GameData;

import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

public class WebsocketHandler {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;
    private final ConnectionManager connections;
    private final Gson gson;

    public WebsocketHandler(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO; // validate tokens and usernames
        this.gameDAO = gameDAO; // load and update games
        this.connections = new ConnectionManager(); //tracks clients connected rn
        this.gson = new Gson();
    }

}
