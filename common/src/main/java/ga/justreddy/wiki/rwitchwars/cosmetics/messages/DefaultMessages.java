package ga.justreddy.wiki.rwitchwars.cosmetics.messages;

import ga.justreddy.wiki.rwitchwars.cosmetics.Messages;

public class DefaultMessages extends Messages {

    // <killer> shows the players name
    // <team> shows the team name

    public DefaultMessages() {
        super("Default", 0, 0, "rwitchwars.cosmetics.witchwars.default");
    }

    @Override
    public String playerKill() {
        return "<player> &7got killed by <killer>";
    }

    @Override
    public String witchKill() {
        return "<team>'s &7witch has been killed by <killer>";
    }

    @Override
    public String eliminatedKill() {
        return "<player> &7got eliminated by <killer>";
    }

    @Override
    public String voidKill() {
        return "<player> &7got knocked into the void by <killer>";
    }
}
