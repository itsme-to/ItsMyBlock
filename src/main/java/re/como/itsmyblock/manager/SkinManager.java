package re.como.itsmyblock.manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import re.como.itsmyblock.Main;
import re.como.itsmyblock.skin.parser.Base64Parser;
import re.como.itsmyblock.skin.parser.HDBParser;
import re.como.itsmyblock.skin.parser.SkinParser;
import re.como.itsmyblock.skin.parser.TextureParser;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class SkinManager {
    private static SkinManager instance;

    private Main main = Main.getInstance();

    private final Map<String, SkinParser> parsers = new HashMap<>();
    private final Map<String, ItemStack> skins = new HashMap<>();

    private SkinManager() {
    }

    public void loadHeadDatabaseParser() {
        parsers.put("hdb", new HDBParser());
    }

    public void load(FileConfiguration configuration) {
        parsers.clear();

        parsers.put("basehead", new Base64Parser());
        parsers.put("texture", new TextureParser());

        if (main.getServer().getPluginManager().isPluginEnabled("HeadDatabase")) {
            loadHeadDatabaseParser();
        }

        configuration.getConfigurationSection("block").getKeys(false)
                .stream()
                .forEach(key -> loadProfile(key, configuration.getConfigurationSection("block." + key)));
    }

    public void reload(FileConfiguration configuration) {
        skins.clear();
        load(configuration);
    }

    private void loadProfile(String key, ConfigurationSection section) {
        String value = section.getString("head");
        String[] splitValue = value.split("-");

        if (splitValue.length != 2) {
            main.getLogger().info("Couldn't load block " + key + ", its value (" + value + ") is wrong!");
            return;
        }

        String type = splitValue[0].toLowerCase();
        String skin = splitValue[1];

        if (!parsers.containsKey(type)) {
            main.getLogger().info("Couldn't load block " + key + ", " + type + " is not a valid type!");
            return;
        }

        try {
            skins.put(key, parsers.get(type).createSkullHead(key, section, skin));
        } catch (Exception e) {
            main.getLogger().log(Level.INFO, "Couldn't load block " + key + ", an error occured!", e);
        }
    }

    public boolean hasSkin(String type) {
        return skins.containsKey(type);
    }

    public ItemStack getSkin(String type) {
        return skins.get(type);
    }

    public Map<String, ItemStack> getSkins() {
        return skins;
    }

    public static SkinManager getInstance() {
        if (instance == null) {
            instance = new SkinManager();
        }

        return instance;
    }
}
