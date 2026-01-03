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

    private static final int MAX_MOBS_PER_GROUP = 3;
    private static final int MAX_ENTITIES = 10;
    private static final double CHANCE_PER_MINUTE = 7.0;

    @EventHandler
    public void onZoneSpawn(ZoneSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onZoneTick(ZoneTickEvent event) {
        Zone zone = event.getZone();
        World world = zone.min.getWorld();

        if (!Checker.isPlayerNearby(zone, world)) {
            return;
        }

        int entitiesCount = Checker.getCountEntitiesWithoutPlayer(event.getEntitiesUUIDsInZone());
        if (entitiesCount >= MAX_ENTITIES) {
            return;
        }

        if (Checker.isSpawnChance(CHANCE_PER_MINUTE)) {
            return;
        }

        Pillager p = null;
        EntityType mob = EntityType.PILLAGER;

        int max_mobs_per_group = MAX_MOBS_PER_GROUP;
        int range = MAX_ENTITIES - entitiesCount;
        if (max_mobs_per_group >= range) {
            max_mobs_per_group = range;
        }

        List<Location> spawnLoc = MobController.findGroupSpawnLocations(zone, mob, max_mobs_per_group, true);
        for (Location loc : spawnLoc) {
            p = (Pillager) world.spawnEntity(loc, mob, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }

        if (Checker.random.nextDouble() <= 0.06 && p != null) {
            p.setPatrolLeader(true);
        }
    }


}
