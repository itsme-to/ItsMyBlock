package re.como.itsmyblock.skin.parser;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import re.como.itsmyblock.manager.BlockManager;

public class HDBParser implements SkinParser {
    private HeadDatabaseAPI api = new HeadDatabaseAPI();

    @Override
    public ItemStack createSkullHead(String key, ConfigurationSection section, String value) {
        ItemStack skull = api.getItemHead(value);
        ItemMeta meta = skull.getItemMeta();
        meta.getPersistentDataContainer().set(BlockManager.TYPE, PersistentDataType.STRING, key);

        if (section.isString("displayName")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes(
                    '&',
                    section.getString("displayName")
            ));
        }

        skull.setItemMeta(meta);
        return skull;
    }
}
