package me.deadybbb.mysty.mobcontrol;

import me.deadybbb.customzones.Zone;
import me.deadybbb.customzones.events.ZoneSpawnEvent;
import me.deadybbb.customzones.events.ZoneTickEvent;
import me.deadybbb.customzones.prefixes.CustomZonePrefix;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.Random;

@CustomZonePrefix("biobunkerspawner")
public class BiobunkerListener implements Listener {

    private static final Random random = new Random();
    private static final double SPAWN_CHANCE = 0.2;
    private static final int MAX_MOBS = 10;

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

        long monsterCount = event.getEntitiesInZone().size();
        if (monsterCount >= MAX_MOBS) {
            return;
        }

        if (random.nextDouble() > SPAWN_CHANCE) {
            return;
        }

        EntityType mob = random.nextBoolean() ? EntityType.SLIME : EntityType.BOGGED;
        List<Location> spawnLoc = MobController.findGroupSpawnLocations(zone, mob, false);
        for (Location loc : spawnLoc) {
            world.spawnEntity(loc, mob, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
}
