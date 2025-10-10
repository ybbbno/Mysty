package me.deadybbb.mysty.compasnoise;

import me.deadybbb.compasstargets.CompassMode;
import me.deadybbb.compasstargets.CompassTargets;
import me.deadybbb.customzones.events.ZoneEnterEvent;
import me.deadybbb.customzones.events.ZoneExitEvent;
import me.deadybbb.customzones.events.ZoneStayEvent;
import me.deadybbb.customzones.events.ZoneTickEvent;
import me.deadybbb.customzones.prefixes.CustomZonePrefix;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@CustomZonePrefix("compassnoise")
public class CompassNoiseListener implements Listener {
    private final PluginProvider plugin;

    private Map<UUID, Location> basicLocations = new HashMap<>();
    private Map<UUID, CompassTargetManager> compassManagers = new HashMap<>();

    public CompassNoiseListener(PluginProvider plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onZoneEnter(ZoneEnterEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        CompassTargets.changeModeForPlayer(player, CompassMode.TARGET);
//        boolean isCompass = false;
//        for (ItemStack item : player.getInventory().getContents()) {
//            if (item != null && item.getType() == Material.COMPASS) {
//                isCompass = true;
//                break;
//            }
//        }
//        if (!isCompass) return;

        UUID uuid = player.getUniqueId();
        Location basic = player.getCompassTarget();
        basicLocations.put(uuid, basic);
        compassManagers.put(uuid, new CompassTargetManager(plugin, player, basic));
    }

    @EventHandler
    public void onZoneExit(ZoneExitEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        CompassTargets.changeModeForPlayer(player, CompassMode.SELF);

        UUID uuid = player.getUniqueId();
        Location basic = basicLocations.get(uuid);

        CompassTargetManager manager = compassManagers.get(uuid);
        if (manager != null) {
            manager.stop();
        } else {
            player.setCompassTarget(basic);
        }
        basicLocations.remove(uuid);
        compassManagers.remove(uuid);
    }
}
