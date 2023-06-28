package re.como.itsmyblock.skin.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import re.como.itsmyblock.manager.BlockManager;

public class SkinUtil {
    public static ItemStack createSkull(PlayerProfile profile, ConfigurationSection section, String type) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = ((SkullMeta) skull.getItemMeta());
        meta.getPersistentDataContainer().set(BlockManager.TYPE, PersistentDataType.STRING, type);
        meta.setOwnerProfile(profile);

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
