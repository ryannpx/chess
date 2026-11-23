package websocket.messages;

public class NotificiationMessage extends ServerMessage{
    private final String serverMessage;

    public NotificationMessage(String message){
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
