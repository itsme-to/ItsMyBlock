package re.como.itsmyblock.skin.parser;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import re.como.itsmyblock.skin.util.SkinUtil;

import java.net.MalformedURLException;
import java.net.URL;

public class TextureParser implements SkinParser {
    @Override
    public ItemStack createSkullHead(String key, ConfigurationSection section, String value) throws MalformedURLException {
        PlayerProfile profile = Bukkit.createPlayerProfile(key).update().join();
        URL url = new URL("http://textures.minecraft.net/texture/" + value);
        PlayerTextures textures = profile.getTextures();
        textures.setSkin(url);
        profile.setTextures(textures);
        return SkinUtil.createSkull(profile, section, key);
    }
}
