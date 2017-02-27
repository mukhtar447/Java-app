package app;

public enum OperationType {
    ADD("add"),
    SUBTRACT("subtract"),
    MULTIPLY("multiply"),
    UNKNOW("unkown");
    private String type;

    OperationType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static OperationType createMessageType(String type) {
        for (OperationType value : OperationType.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return UNKNOW;
    }
}


