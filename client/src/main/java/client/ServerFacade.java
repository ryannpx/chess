package client;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {
    private final String serverUrl;
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public ServerFacade(int port) {
        serverUrl = "http://localhost: " + port;
    }

    private <T> T makeRequest(String method, String path, Object body, String authToken, Class<T>
            responseclass) throws ResponseException{
        var builder = HttpRequest.newBuilder(URI.create(serverUrl + path));

        if (body == null){
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        }
        else {
            builder.method(method,HttpRequest.BodyPublishers.ofString(gson.toJson(body)));
        }
        if (authToken != null) {
            //auth
            builder.header("authorization", authToken);
        }

        HttpResponse<String> response;

        try{
            response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        }
        catch(IOException | InterruptedException ex) {
            throw new ResponseException

        }
    }

}
