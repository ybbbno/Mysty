package me.deadybbb.mysty.clocknoise;

import me.deadybbb.customzones.events.ZoneEnterEvent;
import me.deadybbb.customzones.events.ZoneExitEvent;
import me.deadybbb.customzones.prefixes.CustomZonePrefix;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CustomZonePrefix("clocknoise")
public class ClockNoiseListener implements Listener {
    private final PluginProvider plugin;
    private final Map<UUID, BukkitTask> playerTasks = new HashMap<>();

    public ClockNoiseListener(PluginProvider plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onZoneEnter(ZoneEnterEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        UUID uuid = player.getUniqueId();
        // Cancel any existing task for this player
        if (playerTasks.containsKey(uuid)) {
            playerTasks.get(uuid).cancel();
            playerTasks.remove(uuid);
        }

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                player.setPlayerTime((long) (Math.random() * 24000), false);
            }
        }.runTaskTimer(plugin, 0L, 1L);

        playerTasks.put(uuid, task);
    }

    @EventHandler
    public void onZoneExit(ZoneExitEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        UUID uuid = player.getUniqueId();
        if (playerTasks.containsKey(uuid)) {
            playerTasks.get(uuid).cancel();
            playerTasks.remove(uuid);
        }
        player.resetPlayerTime();
    }
}
