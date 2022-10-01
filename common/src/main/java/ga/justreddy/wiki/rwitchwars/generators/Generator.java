package ga.justreddy.wiki.rwitchwars.generators;

import ga.justreddy.wiki.rwitchwars.RWitchWars;
import ga.justreddy.wiki.rwitchwars.enums.GeneratorType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
public class Generator extends BukkitRunnable {

    private final Location location;
    private final GeneratorType generatorType;
    private double time;
    private double timeX;
    private int level;

    public Generator(Location location, GeneratorType generatorType, double time, int level) {
        this.location = location;
        this.generatorType = generatorType;
        this.time = time;
        this.timeX = time;
        this.level = level;
    }

    @Override
    public void run() {
        time = time - 0.05;
        if (time <= 0) {
            Bukkit.getScheduler().runTask(RWitchWars.getWitchWars(), () -> {
                location.getWorld().dropItem(location.add(0.5, 1, 0.5), generatorType.getDropItem());
            });
            time = timeX;
        }
    }

    public void setLevel(int level) {
        this.level = level;
        switch (generatorType) {
            case WART:
                switch (level) {
                    case 2:
                        this.timeX = 1;
                        break;
                    case 3:
                        this.timeX = 0.7;
                        break;
                    case 4:
                        this.timeX = 0.2;
                }
                break;
            case GUNPOWDER:
                switch (level) {
                    case 2:
                        this.timeX = 2;
                        break;
                    case 3:
                        this.timeX = 1;
                    case 4:
                        this.timeX = 0.6;
                }
                break;
            case CRYSTAL:
                switch (level) {
                    case 2:
                        this.timeX = 15;
                        break;
                    case 3:
                        this.timeX = 5;
                        break;
                }
                break;
        }
    }

    /*  @Override
    public void run() {
        if (time <= 0) {
            time = timeX;
            if (generatorType == GeneratorType.BASE) {
                location.getWorld().dropItemNaturally(location.add(0.5, 1, 0.5), new ItemStack(Material.NETHER_WARTS));
            } else if (generatorType == GeneratorType.DIAMOND) {
                location.getWorld().dropItemNaturally(location.add(0.5, 1, 0.5), new ItemStack(Material.DIAMOND));
            } else if (generatorType == GeneratorType.CRYSTAL) {
                location.getWorld().dropItemNaturally(location.add(0.5, 1, 0.5), new ItemStack(Material.PRISMARINE_SHARD));
            }
        }
        time--;
    }

    public void setTime(int level) {
        this.level = level;
        if (generatorType == GeneratorType.DIAMOND) {
            switch (level) {
                case 1:
                    this.time = 30;
                    break;
                case 2:
                    this.time = 20;
                    break;
                case 3:
                    this.time = 10;
                    break;
                default: // Because yes
                    this.time = 15;
                    break;
            }
        } else if (generatorType == GeneratorType.CRYSTAL) {
            switch (level) {
                case 1:
                    this.time = 60;
                    break;
                case 2:
                    this.time = 40;
                    break;
                case 3:
                    this.time = 20;
                    break;
                default:
                    this.time = 25;
            }
        }
        this.timeX = time;
    }*/

}
