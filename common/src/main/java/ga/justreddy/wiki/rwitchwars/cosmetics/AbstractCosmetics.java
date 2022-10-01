package ga.justreddy.wiki.rwitchwars.cosmetics;

import lombok.Data;

@Data
public abstract class AbstractCosmetics {

    private final String name;
    private final int id;
    private final int cost;
    private final String permission;

}
