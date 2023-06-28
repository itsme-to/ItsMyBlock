package re.como.itsmyblock.skin.parser;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public interface SkinParser {
    ItemStack createSkullHead(String key, ConfigurationSection section, String value) throws Exception;
}
