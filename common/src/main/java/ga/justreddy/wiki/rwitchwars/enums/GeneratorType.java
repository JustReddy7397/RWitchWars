package ga.justreddy.wiki.rwitchwars.enums;

import com.cryptomorin.xseries.XMaterial;
import ga.justreddy.wiki.rwitchwars.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public enum GeneratorType {
    /* Iron */
    WART("&cBlood", 1, 4, XMaterial.NETHER_WART.parseItem(), "Blood"),
    /* Gold */
    GUNPOWDER("&7Dust", 1, 4, XMaterial.GUNPOWDER.parseItem(), "Dust"),
    /* Emerald */
    CRYSTAL("&aCrystal", 1, 3, XMaterial.PRISMARINE_SHARD.parseItem(), "Crystal");

    private int level;
    private int maxLevel;
    private final String name;
    private final ItemStack dropItem;
    private final String rawName;
    GeneratorType(String name, int level, int maxLevel, ItemStack dropItem, String rawName){
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.dropItem = dropItem;
        this.rawName = rawName;
    }

    public String getRawName() {
        return rawName;
    }

    public String getName() {
        return Utils.format(name);
    }

    public int getLevel() {
        return level;
    }

    public String getDisplay() {
        if (level == 1) {
            return Utils.format("&cBroken");
        } else {
            return Utils.format(name + " &7 - &aLevel " + level);
        }
    }

    public ItemStack getDropItem() {
        return dropItem;
    }

}
