package ga.justreddy.wiki.rwitchwars.enums;

import ga.justreddy.wiki.rwitchwars.utils.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TeamColor {

    RED("red", "&cRed", "&c[RED]", "&c"),
    BLUE("blue","&9Blue", "&9[BLUE]", "&9"),
    GREEN("green","&aGreen", "&a[GREEN]", "&a"),
    YELLOW("yellow","&eYellow", "&e[YELLOW]", "&e"),
    AQUA("aqua","&bAqua", "&b[AQUA]", "&b"),
    WHITE("white","&fWhite", "&f[WHITE]", "&f"),
    PINK("pink","&dPink", "&d[PINK]", "&d"),
    GRAY("gray","&7Gray", "&7[GRAY]", "&7");

    private final String identifier;
    private final String displayName;
    private final String prefix;
    private final String code;

    public String getDisplayName() {
        return Utils.format(displayName);
    }

    public String getPrefix() {
        return Utils.format(prefix);
    }

    public String getCode() {
        return code;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static TeamColor getById(String id) {
        for (TeamColor teamColor : values()) {
            if (teamColor.getIdentifier().equalsIgnoreCase(id)) return teamColor;
        }
        return null;
    }

}
