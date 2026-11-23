package websocket.messages;

public class ErrorMessage extends ServerMessage{

    private final String errorMessage;

    public ErrorMessage(String errorMessage){
        super(ServerMessageType.ERROR); //identifies message type
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
