package handler;

import com.google.gson.Gson;
import model.RegisterRequest;
import model.RegisterResult;
import service.UserService;
import io.javalin.http.Context;
import model.*;

public class UserHandler {
    private final UserService userService;
    private final Gson gson = new Gson();

    public UserHandler(UserService userService){
        this.userService = userService;
    }

    public void register(Context ctx) throws Exception {
        RegisterRequest request = gson.fromJson(ctx.body(), RegisterRequest.class);
        RegisterResult result = userService.register(request);
        ctx.status(200).result(gson.toJson(result));
    }
}
