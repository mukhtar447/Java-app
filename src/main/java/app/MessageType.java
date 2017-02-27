package app;
public enum MessageType {
    MESSAGE_TYPE_1("Message Type 1"),
    MESSAGE_TYPE_2("Message Type 2"),
    MESSAGE_TYPE_3("Message Type 3"),
    UNKNOW("unkown");
    private String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static MessageType createMessageType(String type) {
        for (MessageType value : MessageType.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return UNKNOW;
    }
}


