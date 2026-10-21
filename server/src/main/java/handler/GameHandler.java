package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import model.*;
import service.GameService;

public class GameHandler {
    private final GameService gameService;
    private final Gson gson = new Gson();

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public void listGames(Context ctx) throws Exception {
        String authToken = ctx.header("authorization");
        ListGamesResult result = gameService.listGames(authToken);
        ctx.status(200).result(gson.toJson(result));
    }

    public void createGame(Context ctx) throws Exception {
        String authToken = ctx.header("authorization");
        CreateGameRequest request = gson.fromJson(ctx.body(), CreateGameRequest.class);
        CreateGameResult result = gameService.createGame(authToken, request);
        ctx.status(200).result(gson.toJson(result));
    }

    public void joinGame(Context ctx) throws Exception {
        String authToken = ctx.header("authorization");
        JoinGameRequest request = gson.fromJson(ctx.body(), JoinGameRequest.class);
        gameService.joinGame(authToken, request);
        ctx.status(200).result("{}");
    }
}
