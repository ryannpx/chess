package websocket;

import io.javalin.websocket.WsContext;

public record WebsocketConnection(//one person connected to the database
    String username,
    int gameID,
    WsContext context //context sends json back and forth thru the websocket
) {}
