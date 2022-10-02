package ga.justreddy.wiki.rwitchwars.enums;

public enum GameType {

    SOLO("Solo"), // 8
    DUO("Doubles"), // 16
    TRIO("3v3v3v3"), // 12
    SQUAD("4v4v4v4"); // 16

    private final String identifier;

    GameType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
