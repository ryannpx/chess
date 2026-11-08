package client;

//import;

import java.util.Arrays;

public class ChessClient {
    private final ServerFacade facade;
    private State state = State.SIGNEDOUT;
    private String authToken = null;
    private String username = null;



    public ChessClient(int port) {
        facade = new ServerFacade(port);



    }

    public State getState() {
        return state;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split("\\s+");
            var command = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);


            return switch (state) {
                case SIGNEDOUT -> switch (command) {
                    case "register" -> register(params);
                    case "login" -> login(params);
                    case "quit" -> "quit";
                    default -> help();
                };
                case SIGNEDIN -> switch (command) {
                    case "logout" -> logout();
                    case "quit" -> "quit";
                    default -> help();
                };
            };


        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

        private String help () {
            if (state == State.SIGNEDOUT) {
                return """
                        register <USERNAME> <PASSWORD> <EMAIL> - create an account
                        login <USERNAME> <PASSWORD> - sign in
                        help - show this help
                        quit - exit the program
                        """;
            }
            return """
                    logout - sign out
                    help - show this help
                    quit - exit the program
                    """;
        }
        private String register (String[]params) throws ResponseException {
            if (params.length != 3) {
                return "Expected: register <USERNAME> <PASSWORD> <EMAIL>\n";
            }


            var result = facade.register(params[0], params[1], params[2]);

            state = State.SIGNEDIN;
            authToken = result.authToken();
            username = result.username();


            return "Registered + logged in as " + username + "\n";

        }
        private String login (String[]params) throws ResponseException {
            if (params.length != 2) {
                return "Expected: login <USERNAME> <PASSWORD>\n";
            }


            var result = facade.login(params[0], params[1]);

            state = State.SIGNEDIN;
            authToken = result.authToken();
            username = result.username();


            return "Logged in as " + username + "\n";
        }
        private String logout () throws ResponseException {
            facade.logout(authToken);
            state = State.SIGNEDOUT;

            authToken = null;
            username = null;


            return "Logged out\n";
        }




// run server first, then client



}
