package me.deadybbb.mysty.mobcontrol.instances;

import me.deadybbb.customzones.Zone;
import me.deadybbb.customzones.events.ZoneSpawnEvent;
import me.deadybbb.customzones.events.ZoneTickEvent;
import me.deadybbb.customzones.prefixes.CustomZonePrefix;
import me.deadybbb.mysty.mobcontrol.Checker;
import me.deadybbb.mysty.mobcontrol.MobController;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.Random;

@CustomZonePrefix("netherbunkerspawner")
public class NetherbunkerListener implements Listener {

    private static final int MAX_MOBS = 3;

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

        if (Checker.isSpawnChance(10.0)) {
            return;
        }

        EntityType mob = EntityType.ZOGLIN;
        List<Location> spawnLoc = MobController.findGroupSpawnLocations(zone, mob, 3, false);
        for (Location loc : spawnLoc) {
            world.spawnEntity(loc, mob, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
}
