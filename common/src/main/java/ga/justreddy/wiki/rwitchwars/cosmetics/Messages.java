package ga.justreddy.wiki.rwitchwars.cosmetics;

public abstract class Messages extends AbstractCosmetics {

    public Messages(String name, int id, int cost, String permission) {
        super(name, id, cost, permission);
    }

    public abstract String playerKill();

    public abstract String witchKill();

    public abstract String eliminatedKill();

    public abstract String voidKill();

}
