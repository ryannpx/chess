package websocket;

import com.google.gson.Gson;
import io.javalin.websocket.WsContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

    private final Map<WsContext, WebsocketConnection> connections =
            new ConcurrentHashMap<>();



    private final Gson gson = new Gson();
}
