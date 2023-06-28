package re.como.itsmyblock.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import re.como.itsmyblock.manager.BlockManager;

public class BlockListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        if (event.isCancelled()) {
            return;
        }

        if (item.getType() != Material.PLAYER_HEAD) {
            return;
        }

        if (!meta.getPersistentDataContainer().has(BlockManager.TYPE, PersistentDataType.STRING)) {
            return;
        }

        BlockManager.placeBlock(block, player, item);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.BARRIER) {
            return;
        }

        BlockManager.removeBlock(event.getBlock().getLocation());
    }
}
