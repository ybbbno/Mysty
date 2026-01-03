package me.deadybbb.mysty.pillar;

import me.deadybbb.customzones.Zone;
import me.deadybbb.customzones.events.ZoneEnterEvent;
import me.deadybbb.customzones.events.ZoneExitEvent;
import me.deadybbb.customzones.events.ZoneTickEvent;
import me.deadybbb.customzones.prefixes.CustomZonePrefix;
import me.deadybbb.ybmj.PluginProvider;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CustomZonePrefix("magnet")
public class MagnetListener implements Listener {

    private final PluginProvider plugin;
    private final Map<UUID, BukkitRunnable> tasks = new HashMap<>();

    public MagnetListener(PluginProvider plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onZoneEnter(ZoneEnterEvent event) {
        UUID uuid = event.getEntityUUID();
//        plugin.logger.info(uuid.toString());
        Vector center = event.getZone().getBoundingBox().getCenter();

        if (isAliveMagnetic(uuid)) {
            runTask(uuid, center, 0.01);
        }
        if (isItemMagnetic(uuid)) {
            runTask(uuid, center, 0.01);
        }
    }

    @EventHandler
    public void onZoneExit(ZoneExitEvent event) {
        UUID uuid = event.getEntityUUID();
//        plugin.logger.info(uuid.toString());
        if (tasks.containsKey(uuid)) {
            removeTask(uuid);
        }
    }

    private boolean isAliveMagnetic(UUID uuid) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity == null) return false;
        return entity.getType() == EntityType.IRON_GOLEM;
    }

    private boolean isItemMagnetic(UUID uuid) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity == null) return false;
        if (entity.getType() == EntityType.ITEM) {
            Item item = (Item) entity;
            String name = item.name().toString();
            return isItemNameMagnetic(name);
        }
        return false;
    }

    private boolean isItemNameMagnetic(String name) {
        return name.contains("iron")
                || name.contains("rail")
                || name.contains("chain")
                || name.contains("anvil");
    }

    private void runTask(UUID uuid, Vector center, double m) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity == null) return;
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                Vector direction = center.clone().subtract(entity.getLocation().toVector()).normalize().multiply(m);
//                plugin.logger.info(direction.toString());
                entity.setVelocity(direction);
            }
        };

        tasks.put(uuid, task);
        task.runTaskTimer(plugin, 0L, 1L);
    }

    private void removeTask(UUID uuid) {
        tasks.get(uuid).cancel();
        tasks.remove(uuid);
    }
}
