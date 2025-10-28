package me.deadybbb.mysty.mobcontrol.instances;

import me.deadybbb.customzones.Zone;
import me.deadybbb.customzones.events.ZoneSpawnEvent;
import me.deadybbb.customzones.events.ZoneTickEvent;
import me.deadybbb.customzones.prefixes.CustomZonePrefix;
import me.deadybbb.mysty.mobcontrol.Checker;
import me.deadybbb.mysty.mobcontrol.MobController;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.Random;

@CustomZonePrefix("outpostspawner")
public class OutpostListener implements Listener {

    private static final int MAX_MOBS = 10;

    private PluginProvider plugin;

    public OutpostListener(PluginProvider plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onZoneSpawn(ZoneSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onZoneTick(ZoneTickEvent event) {
        Zone zone = event.getZone();
        World world = zone.min.getWorld();

        if (!Checker.isPlayerNearby(zone, world)) {
            return;
        }

        long monsterCount = event.getEntitiesInZone().size();
        if (monsterCount >= MAX_MOBS) {
            return;
        }

        if (Checker.isSpawnChance(7)) {
            return;
        }

        Pillager p = null;
        EntityType mob = EntityType.PILLAGER;
        List<Location> spawnLoc = MobController.findGroupSpawnLocations(zone, mob, 3, true);
        for (Location loc : spawnLoc) {
            p = (Pillager) world.spawnEntity(loc, mob, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }

        if (Checker.random.nextDouble() <= 0.06 && p != null) {
            p.setPatrolLeader(true);
        }
    }


}
