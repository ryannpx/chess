package websocket;

import com.google.gson.Gson;
import io.javalin.websocket.WsContext;
import websocket.messages.ServerMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    private final Map<WsContext, WebsocketConnection> connections =
            new ConcurrentHashMap<>();



    private final Gson gson = new Gson();

    public void add(String username, int gameID, WsContext context) {
        connections.put(
                context,
                new WebsocketConnection(username, gameID, context)
        );
    }

    public void remove(WsContext context) {
        connections.remove(context);
    }

    public void send(WsContext context, ServerMessage message) {
        context.send(gson.toJson(message));

    }


    public void broadcast(
            int gameID,
            WsContext excludedContext,
            ServerMessage message
    ) {String json = gson.toJson(message);

        for (WebsocketConnection connection : connections.values()){
            boolean sameGame = connection.gameID() == gameID;
            boolean notExcludedContent = connection.context() != excludedContext;

            if (sameGame && notExcludedContent) {
                connection.context().send(json);
            }
        }


    }


}
