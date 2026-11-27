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
import websocket.commands.MakeTheMoveCommand;

import static javax.management.remote.JMXConnectorFactory.connect;

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


    public void onMessage(WsMessageContext context) {
        try {
            UserGameCommand command = gson.fromJson(context.message(), UserGameCommand.class);

            if (command.getCommandType() == UserGameCommand.CommandType.CONNECT) {
                connect(context, command);

            } else if (command.getCommandType() == UserGameCommand.CommandType.MAKE_MOVE) {
                MakeTheMoveCommand moveCommand =
                        gson.fromJson(context.message(), MakeTheMoveCommand.class);

                makeMove(context, moveCommand);

            } else {
                connections.send(
                        context,
                        new ErrorMessage("Error: This command isn't implemented yet")
                );
            }


        } catch (Exception exception) {
            String exceptDetails = exception.getMessage() == null
                    ? "invalid command"
                    : exception.getLocalizedMessage();

            connections.send(
                    context,
                    new ErrorMessage("Error: " + exceptDetails)
            );

        }
    }



    private void connect(WsMessageContext context, UserGameCommand command) throws Exception {
        AuthData auth = authDAO.getAuth(command.getAuthToken());
        GameData game = gameDAO.getGame(command.getGameID());



        if (auth == null) {
            throw new IllegalArgumentException("Error: Empty authentication token.");
        }

        if (game == null) {
            throw new IllegalArgumentException("Error: Empty or invalid game.");
        }


        String username = auth.username(); //get username
        String role;

        if (username.equals(game.whiteUsername())) {
            role = "WHITE";
        }
        else if (username.equals(game.blackUsername())) {
            role = "BLACK";
        }
        else {
            role = "OBSERVER";
        }

        private void makeMove(
                WsMessageContext context,
                MakeTheMoveCommand command
        ) throws Exception {
            // next
        }


        connections.add(username, game.gameID(), context);
        connections.send(context, new LoadGameMessage(game));


        String notification = role.equals("OBSERVER")
                ? username + " connected as an observer"
                : username + " connected as " + role;
        connections.broadcast(
                game.gameID(),
                context,
                new NotificationMessage(notification)
        );
    }

    public void onClose(WsCloseContext context) {
        connections.remove(context);
    }
}

