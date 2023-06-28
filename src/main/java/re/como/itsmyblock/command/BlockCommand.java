package re.como.itsmyblock.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import re.como.itsmyblock.Main;
import re.como.itsmyblock.manager.SkinManager;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BlockCommand implements CommandExecutor, TabCompleter {

    private Main main = Main.getInstance();
    private FileConfiguration config = main.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            config.getStringList("messages.usage").forEach(message -> {
                sendConfigMessage(sender, ChatColor.translateAlternateColorCodes('&', message));
            });
            return false;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            Main.getInstance().reloadConfig();
            SkinManager.getInstance().reload(Main.getInstance().getConfig());
            sendConfigMessage(sender, config.getString("messages.reload"));
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            Player player = Bukkit.getPlayer(args[1]);
            String type = args[2].toLowerCase();

            if (!SkinManager.getInstance().hasSkin(type)) {
                sendConfigMessage(sender, config.getString("messages.typeDoesntExist"));
                return false;
            }

            if (!player.getInventory().addItem(SkinManager.getInstance().getSkin(type)).isEmpty()) {
                sendConfigMessage(sender, config.getString("messages.inventoryFull"));
                return false;
            }

            sendConfigMessage(sender, config.getString("messages.success"));
        }

        return true;
    }

    private void sendConfigMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return List.of("give", "reload");
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 2) {
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            } else if (args.length == 3) {
                return SkinManager.getInstance().getSkins().keySet().stream().toList();
            }
        }

        return Collections.emptyList();
    }
}
