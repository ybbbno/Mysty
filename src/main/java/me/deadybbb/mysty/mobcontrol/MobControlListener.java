package me.deadybbb.mysty.mobcontrol;

import me.deadybbb.customzones.Zone;
import me.deadybbb.customzones.events.ZoneSpawnEvent;
import me.deadybbb.customzones.events.ZoneTickEvent;
import me.deadybbb.customzones.prefixes.CustomZonePrefix;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

@CustomZonePrefix("mobcontrol")
public class MobControlListener implements Listener {

    private static final Random random = new Random();
    private static final int MAX_ATTEMPTS = 10;
    private static final double SPAWN_CHANCE = 0.1;
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

        Location spawnLoc = findSpawnLocation(zone);
        if (spawnLoc != null) {
            world.spawnEntity(spawnLoc, EntityType.ZOMBIE, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }

    private Location findSpawnLocation(Zone zone) {
        World world = zone.min.getWorld();

        double minXd = Math.min(zone.min.getX(), zone.max.getX());
        double maxXd = Math.max(zone.min.getX(), zone.max.getX());
        double minZd = Math.min(zone.min.getZ(), zone.max.getZ());
        double maxZd = Math.max(zone.min.getZ(), zone.max.getZ());
        double minYd = Math.min(zone.min.getY(), zone.max.getY());
        double maxYd = Math.max(zone.min.getY(), zone.max.getY());

        int minY = (int) Math.ceil(minYd);
        int maxY = (int) Math.floor(maxYd);
        int midY = (minY + maxY) / 2;

        // Assuming a typical monster height of 2 blocks
        int entityHeight = 2;

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            double x = minXd + random.nextDouble() * (maxXd - minXd);
            double z = minZd + random.nextDouble() * (maxZd - minZd);

            int bx = (int) Math.floor(x);
            int bz = (int) Math.floor(z);

            // Go down from mid
            for (int y = midY; y >= minY; y--) {
                if (isSuitable(world, bx, y, bz, entityHeight)) {
                    return new Location(world, bx + 0.5, y, bz + 0.5);
                }
            }

            // Go up from mid + 1
            for (int y = midY + 1; y <= maxY; y++) {
                if (isSuitable(world, bx, y, bz, entityHeight)) {
                    return new Location(world, bx + 0.5, y, bz + 0.5);
                }
            }
        }

        return null;
    }

    private boolean isSuitable(World world, int bx, int by, int bz, int height) {
        // Check below is solid
        Block below = world.getBlockAt(bx, by - 1, bz);
        if (!below.getType().isSolid()) {
            return false;
        }

        // Check the entity space is air
        for (int i = 0; i < height; i++) {
            Block space = world.getBlockAt(bx, by + i, bz);
            if (!space.getType().isAir()) {
                return false;
            }
        }

        return true;
    }
}
