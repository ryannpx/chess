package client;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import model.*;

public class ServerFacade {
    private final String serverUrl;
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public ServerFacade(int port) {
        serverUrl = "http://localhost:" + port;
    }

    private <T> T makeRequest(String method, String path, Object body, String authToken, Class<T>
            responseclass) throws ResponseException {
        var builder = HttpRequest.newBuilder(URI.create(serverUrl + path));

        if (body == null) {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        } else {
            builder.method(method, HttpRequest.BodyPublishers.ofString(gson.toJson(body)));
        }
        if (authToken != null) {
            //auth
            builder.header("authorization", authToken);
        }

        HttpResponse<String> response;

        try {
            response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            throw new ResponseException(500, "Error, not able to reach the server");

        }
        if (response.statusCode() != 200) {
            var error = gson.fromJson(response.body(), Map.class);
            throw new ResponseException(response.statusCode(), (String) error.get("message"));
        }

        if (responseclass == null) {
            return null;
        }

        return gson.fromJson(response.body(), responseclass);

    }

    public RegisterResult register(String username, String password, String email) throws ResponseException {
        return makeRequest("POST", "/user", new RegisterRequest(username, password, email), null, RegisterResult.class);
    }

    public void clear() throws ResponseException {
        makeRequest("DELETE", "/db", null, null, null);
    }

    public LoginResult login(String username, String password) throws ResponseException{
        return makeRequest("POST", "/session", new LoginRequest(username, password), null, LoginResult.class);

    }

    public void logout(String authToken) throws ResponseException {
        makeRequest("DELETE", "/session", null, authToken, null);
    }

//create list join

    public CreateGameResult createGame(String authToken, String gameName) throws ResponseException{
        return makeRequest("POST", "/game", new CreateGameRequest(gameName), authToken, CreateGameRequest.class);
    }



    public ListGamesResult listGames(String authToken) throws ResponseException {
        return makeRequest("GET", "/game", null, authToken, ListGamesResult.class);
    }

}
