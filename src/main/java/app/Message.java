package app;


public class Message {
    private MessageType messageType;
    private String[] command;

    public Message(MessageType messageType, String ... command){
        this.messageType = messageType;
        this.command = command;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String[] getCommand() {
        return command;
    }
    public static Message from (String message){
        String[] array = message.split("-|\\–");
        return array.length == 2 ? new Message(MessageType.createMessageType(array[0].trim()), array[1].trim().split(" ")):null;
    }
}
