package me.deadybbb.mysty.mobcontrol;

import me.deadybbb.customzones.Zone;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

public class MobController {
    private static final Random random = new Random();
    private static final int MAX_ATTEMPTS = 10;
    private static final Map<EntityType, EntitySize> ENTITY_SIZES = new HashMap<>();
    private static final Map<EntityType, Integer> ENTITY_LIGHT_LEVELS = new HashMap<>();

    static {
        ENTITY_SIZES.put(EntityType.SLIME, new EntitySize(2.04, 2.04, 2.04));
        ENTITY_SIZES.put(EntityType.BOGGED, new EntitySize(0.6, 1.99 , 0.6));
        ENTITY_SIZES.put(EntityType.DROWNED, new EntitySize(0.6, 1.95 , 0.6));
        ENTITY_SIZES.put(EntityType.ZOGLIN, new EntitySize(1.3965, 1.4 , 1.3965));
        ENTITY_SIZES.put(EntityType.PILLAGER, new EntitySize(0.6, 1.95, 0.6));

        ENTITY_LIGHT_LEVELS.put(EntityType.SLIME, 0);
        ENTITY_LIGHT_LEVELS.put(EntityType.PILLAGER, 8);
    }

    public static List<Location> findGroupSpawnLocations(Zone zone, EntityType entity, boolean isBorder) {
        double minXd = Math.min(zone.min.getX(), zone.max.getX());
        double maxXd = Math.max(zone.min.getX(), zone.max.getX());
        double minZd = Math.min(zone.min.getZ(), zone.max.getZ());
        double maxZd = Math.max(zone.min.getZ(), zone.max.getZ());

        List<Location> spawnLocations = new ArrayList<>();

        int groupSize = random.nextInt(3) + 1;

        Location initialLocation;
        if (isBorder) {
            initialLocation = findBorderSpawnLocation(zone, entity);
        } else {
            initialLocation = findSpawnLocation(zone, entity);
        }

        if (initialLocation == null) {
            return spawnLocations;
        }
        spawnLocations.add(initialLocation);

        for (int i = 1; i < groupSize; i++) {
            for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
                double offsetX = random.nextDouble() * 6 - 3;
                double offsetZ = random.nextDouble() * 6 - 3;
                double x = initialLocation.getX() + offsetX;
                double z = initialLocation.getZ() + offsetZ;

                if (x < minXd || x > maxXd || z < minZd || z > maxZd) {
                    continue;
                }

                int bx = (int) Math.floor(x);
                int bz = (int) Math.floor(z);

                Location loc = checkXZ(zone, entity, bx, bz);
                if (loc != null) {
                    spawnLocations.add(loc);
                    break;
                }
            }
        }

        return spawnLocations;
    }

    public static Location findBorderSpawnLocation(Zone zone, EntityType entity) {
        double minXd = Math.min(zone.min.getX(), zone.max.getX());
        double maxXd = Math.max(zone.min.getX(), zone.max.getX());
        double minZd = Math.min(zone.min.getZ(), zone.max.getZ());
        double maxZd = Math.max(zone.min.getZ(), zone.max.getZ());

        double centerX = (minXd + maxXd) / 2;
        double centerZ = (minZd + maxZd) / 2;
        double radiusX = (maxXd - minXd) / 2;
        double radiusZ = (maxZd - minZd) / 2;

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            double theta = random.nextDouble() * 2 * Math.PI;
            double x = centerX + radiusX * Math.cos(theta);
            double z = centerZ + radiusZ * Math.sin(theta);

            if (x < minXd || x > maxXd || z < minZd || z > maxZd) {
                continue;
            }

            int bx = (int) Math.floor(x);
            int bz = (int) Math.floor(z);

            Location loc = checkXZ(zone, entity, bx, bz);
            if (loc != null) return loc;
        }

        return null;
    }

    public static Location findSpawnLocation(Zone zone, EntityType entity) {
        double minXd = Math.min(zone.min.getX(), zone.max.getX());
        double maxXd = Math.max(zone.min.getX(), zone.max.getX());
        double minZd = Math.min(zone.min.getZ(), zone.max.getZ());
        double maxZd = Math.max(zone.min.getZ(), zone.max.getZ());

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            double x = minXd + random.nextDouble() * (maxXd - minXd);
            double z = minZd + random.nextDouble() * (maxZd - minZd);

            int bx = (int) Math.floor(x);
            int bz = (int) Math.floor(z);

            Location loc = checkXZ(zone, entity, bx, bz);
            if (loc != null) return loc;
        }

        return null;
    }

    public static Location checkXZ(Zone zone, EntityType entity, int x, int z) {
        World world = zone.min.getWorld();

        double minYd = Math.min(zone.min.getY(), zone.max.getY());
        double maxYd = Math.max(zone.min.getY(), zone.max.getY());

        int minY = (int) Math.ceil(minYd);
        int maxY = (int) Math.floor(maxYd);
        int midY = (minY + maxY) / 2;

        // Go down from mid
        for (int y = midY; y >= minY; y--) {
            if (isSuitable(entity, world, x, y, z)) {
                return new Location(world, x + 0.5, y, z + 0.5);
            }
        }

        // Go up from mid + 1
        for (int y = midY + 1; y <= maxY; y++) {
            if (isSuitable(entity, world, x, y, z)) {
                return new Location(world, x + 0.5, y, z + 0.5);
            }
        }

        return null;
    }

    public static boolean isSuitable(EntityType entity, World world, int bx, int by, int bz) {
        // Assuming a typical monster height of 2 blocks
        EntitySize size = ENTITY_SIZES.getOrDefault(entity, new EntitySize(0.6, 2.0, 0.6));
        int light_level = ENTITY_LIGHT_LEVELS.getOrDefault(entity, 0);

        // Check below is solid
        Block below = world.getBlockAt(bx, by - 1, bz);
        if (!below.getType().isSolid()) {
            return false;
        }

        // Check light level at the block where the entity will spawn
        Block spawnBlock = world.getBlockAt(bx, by, bz);
        if (spawnBlock.getLightLevel() > light_level) {
            return false;
        }

        // Check the entity space is air
        int heightBlocks = (int) Math.ceil(size.y());
        int widthXBlocks = (int) Math.ceil(size.x() / 2.0); // Половина ширины в каждую сторону
        int widthZBlocks = (int) Math.ceil(size.z() / 2.0); // Половина ширины в каждую сторону

        for (int y = 0; y < heightBlocks; y++) {
            for (int xOffset = -widthXBlocks; xOffset <= widthXBlocks; xOffset++) {
                for (int zOffset = -widthZBlocks; zOffset <= widthZBlocks; zOffset++) {
                    Block space = world.getBlockAt(bx + xOffset, by + y, bz + zOffset);
                    if (!space.getType().isAir()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
