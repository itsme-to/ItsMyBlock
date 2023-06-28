package re.como.itsmyblock.manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import re.como.itsmyblock.Main;

public class BlockManager {
    private static final Vector3f TRANSLATION = new Vector3f(0.5f, 1, 0.5f);
    private static final Vector3f SCALE = new Vector3f(2f, 2f,2f);

    public static final NamespacedKey TYPE = NamespacedKey.fromString("type", Main.getInstance());
    public static final NamespacedKey POSITION = NamespacedKey.fromString("position", Main.getInstance());

    public static void placeBlock(Block block, Player player, ItemStack stack) {
        block.setType(Material.BARRIER);
        block.getWorld().spawn(block.getLocation(), ItemDisplay.class, (entity) -> {
            entity.setItemStack(stack);

            entity.getPersistentDataContainer().set(POSITION, PersistentDataType.INTEGER_ARRAY, new int[] {block.getX(), block.getY(), block.getZ() });

            entity.setDisplayWidth(0f);
            entity.setDisplayHeight(0f);

            Transformation transformation = entity.getTransformation();
            long yaw = 90 * (Math.round(player.getLocation().getYaw() / 90.0));
            if (yaw == 0 || yaw == -180 || yaw == 180) {
                yaw += 180; // Don't try to understand this, it works :'(
            }

            transformation.getScale().set(SCALE);
            transformation.getTranslation().set(TRANSLATION);
            transformation.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(yaw), 0f, 1f, 0f));

            entity.setTransformation(transformation);
        });
    }

    public static void removeBlock(Location location) {
        location.getWorld().getNearbyEntities(location, 2, 2, 2)
                .stream()
                .filter(entity -> entity.getPersistentDataContainer().has(POSITION, PersistentDataType.INTEGER_ARRAY))
                .filter(entity -> checkPosition(location, entity.getPersistentDataContainer().get(POSITION, PersistentDataType.INTEGER_ARRAY)))
                .forEach(Entity::remove);
    }

    private static boolean checkPosition(Location location, int[] position) {
        if (position.length != 3) {
            return false;
        }

        return location.getBlockX() == position[0] && location.getBlockY() == position[1]
                && location.getBlockZ() == position[2];
    }
}
