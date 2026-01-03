package me.deadybbb.mysty.mobcontrol;

import me.deadybbb.customzones.Zone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Checker {
    public static final Random random = new Random();
    private static final double DETECTION_RADIUS = 128.0;

    public static EntityType chooseEntity(List<EntityType> entities) {
        return entities.get(random.nextInt(entities.size()));
    }

    public static boolean isSpawnChance(double amount_per_minute) {
        double spawn_chance = amount_per_minute / 60;
        double chance = random.nextDouble();

        return chance > spawn_chance;
    }

    /**
     * Проверяет, находится ли хотя бы один игрок в радиусе DETECTION_RADIUS от зоны
     */
    public static boolean isPlayerNearby(Zone zone, World world) {
        // Центр зоны
        Location zoneCenter = getZoneCenter(zone);

        // Проверяем всех игроков в мире
        for (Player player : world.getPlayers()) {
            double distance = zoneCenter.distance(player.getLocation());
            if (distance <= DETECTION_RADIUS) {
                return true;
            }
        }

        return false;
    }

    /**
     * Получает центр зоны
     */
    public static Location getZoneCenter(Zone zone) {
        double centerX = (zone.min.getX() + zone.max.getX()) / 2.0;
        double centerY = (zone.min.getY() + zone.max.getY()) / 2.0;
        double centerZ = (zone.min.getZ() + zone.max.getZ()) / 2.0;

        return new Location(zone.min.getWorld(), centerX, centerY, centerZ);
    }

    public static int getCountEntitiesWithoutPlayer(List<UUID> entities) {
        return Math.toIntExact(entities.stream()
                .map(Bukkit::getEntity).filter(Objects::nonNull)
                .filter(entity -> entity.getType() != EntityType.PLAYER && entity instanceof LivingEntity).count());
    }
}
