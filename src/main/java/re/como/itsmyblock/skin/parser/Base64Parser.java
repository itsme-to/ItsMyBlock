package re.como.itsmyblock.skin.parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import re.como.itsmyblock.skin.util.SkinUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class Base64Parser implements SkinParser {
    @Override
    public ItemStack createSkullHead(String key, ConfigurationSection section, String value) throws MalformedURLException {
        PlayerProfile profile = Bukkit.createPlayerProfile(key).update().join();
        URL url = fetchURLInValue(value);
        PlayerTextures textures = profile.getTextures();
        textures.setSkin(url);
        profile.setTextures(textures);
        return SkinUtil.createSkull(profile, section, key);
    }

    private URL fetchURLInValue(String value) throws MalformedURLException {
        String decodedValue = new String(Base64.getDecoder().decode(value));
        JsonObject parsedElement = JsonParser.parseString(decodedValue).getAsJsonObject();
        JsonObject textures = parsedElement.getAsJsonObject("textures");
        JsonObject skin = textures.getAsJsonObject("SKIN");
        return new URL(skin.get("url").getAsString());
    }
}
