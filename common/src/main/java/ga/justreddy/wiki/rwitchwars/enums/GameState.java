package ga.justreddy.wiki.rwitchwars.enums;

import ga.justreddy.wiki.rwitchwars.utils.Utils;

public enum GameState {

    WAITING("&aWaiting"),
    STARTING("&6Starting"),
    PLAYING("&cPlaying"),
    ENDING("&cEnding"),
    RESTARTING("&9Restarting"),
    DISABLED("&7Disabled");

    private final String identifier;

    GameState(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return Utils.format(identifier);
    }
}
