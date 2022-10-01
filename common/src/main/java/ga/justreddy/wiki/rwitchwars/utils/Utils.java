package ga.justreddy.wiki.rwitchwars.utils;

import com.google.gson.Gson;
import ga.justreddy.wiki.rwitchwars.RWitchWars;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;

public class Utils {

    private static final int CENTER_PX = 154;
    public static final String CHAT_LINE = "&m-----------------------------------------------------";
    public static final String CONSOLE_LINE = "*-----------------------------------------------------*";
    public static final String LORE_LINE = "&m--------------------------";


    public static String format(String line) {
        return ChatColor.translateAlternateColorCodes('&', line);
    }

    public static List<String> format(List<String> input) {
        List<String> list = new ArrayList<>();
        for (String line : input) list.add(format(line.replace("%line%", LORE_LINE)));
        return list;
    }
    public static String toJson(Object object, Class<?> clazz) {
        return (new Gson()).toJson(object, clazz);
    }

    public static <Z> Z formJson(String string, Class<Z> clazz) {
        return new Gson().fromJson(string, clazz);
    }

    public static <Z> Class<? extends Z> findClass(File file, Class<Z> clazz) throws Exception {
        final URL url = file.toURI().toURL();
        List<Class<? extends Z>> classes = new ArrayList<>();
        List<String> matches = new ArrayList<>();
        JarInputStream jarInputStream = new JarInputStream(url.openStream());
        URLClassLoader loader = new URLClassLoader(new URL[]{url}, clazz.getClassLoader());
        JarEntry jarEntry = jarInputStream.getNextJarEntry();

        String name = jarEntry.getName();
        if (name.endsWith(".class")) {
            matches.add(name.substring(0, name.lastIndexOf(46)).replace('/', '.'));
        }
        for (String match : matches) {
            Class<?> loaded = Class.forName(match, true, loader);
            if (clazz.isAssignableFrom(loaded)) {
                classes.add(loaded.asSubclass(clazz));
            }
        }
        if (!classes.isEmpty()) {
            return classes.get(0);
        }
        return null;
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(format(message.replace("%line%", CHAT_LINE)));
    }

    public static void sendMessage(CommandSender sender, String... message) {
        for (String line : message) {
            sendMessage(sender, line);
        }
    }

    public static void error(Throwable throwable, String description, boolean disable) {
        if (throwable != null) throwable.printStackTrace();

        if (disable) {
            sendConsole(
                "&4%line%",
                "&cAn internal error has occurred in " + RWitchWars.getWitchWars().getDescription().getName() + "!",
                "&cContact the plugin author if you cannot fix this error.",
                "&cDescription: &6" + description,
                "&cThe plugin will now disable.",
                "&4%line%"
            );
        } else {
            sendConsole(
                "&4%line%",
                "&cAn internal error has occurred in " + RWitchWars.getWitchWars().getDescription().getName() + "!",
                "&cContact the plugin author if you cannot fix this error.",
                "&cDescription: &6" + description,
                "&4%line%"
            );
        }

        if (disable && Bukkit.getPluginManager().isPluginEnabled(RWitchWars.getWitchWars())) {
            Bukkit.getPluginManager().disablePlugin(RWitchWars.getWitchWars());
        }
    }

    public static void errorCommand(CommandSender sender, String description) {
        sendMessage(sender, "&4%line%", "&cAn error occurred while running this command", "&cDescription: &6" + description, "&4%line%");
    }

    public static void sendConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(format(message.replace("%line%", CONSOLE_LINE)));
    }

    public static void sendConsole(String... message) {
        Arrays.stream(message).forEach(Utils::sendConsole);
    }

}
