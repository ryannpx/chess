package client;

import

public class ChessClient {
    private final ServerFacade serverFacade;
    private State state = State.SIGNEDOUT;
    private String authToken = null;
    private String username = null;



    public ChessClient(int port) {
        facade = new ServerFacade(port);



    }

    public State getState() {
        return state;
    }








}
