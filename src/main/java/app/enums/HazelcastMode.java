package app.enums;

public enum HazelcastMode {
    CLIENT_SERVER("CS"),
    PEER_TO_PEER("P2P");

    private final String value;

    HazelcastMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
