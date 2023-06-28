package re.como.itsmyblock;

import org.bukkit.plugin.java.JavaPlugin;
import re.como.itsmyblock.command.BlockCommand;
import re.como.itsmyblock.listener.BlockListener;
import re.como.itsmyblock.manager.SkinManager;

public class Main extends JavaPlugin {
    private static Main instance;
    private SkinManager skinManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        skinManager = SkinManager.getInstance();
        skinManager.load(this.getConfig());

        getCommand("itsmyblock").setExecutor(new BlockCommand());
        getCommand("itsmyblock").setTabCompleter(new BlockCommand());
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }
}
