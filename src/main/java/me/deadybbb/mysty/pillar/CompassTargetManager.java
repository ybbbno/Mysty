package me.deadybbb.mysty.pillar;

import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class CompassTargetManager {
    private double INTERPOLATION_SPEED = 0.02;
    private final long INTERPOLATION_INTERVAL = 1L;
    private final long TARGET_UPDATE_INTERVAL = 4L;

    private static Random rand = new Random();
    private final Player player;
    private Location currentLocation;
    private Location finalLocation;
    private final Location basicLocation;
    private boolean isStop = false;

    private BukkitRunnable interpolationTask;
    private BukkitRunnable targetUpdateTask;

    public CompassTargetManager(PluginProvider plugin, Player player, Location currentLocation) {
        this.player = player;
        this.currentLocation = currentLocation;
        this.basicLocation = currentLocation;
        this.finalLocation = getRandLocation(player.getWorld());
        startInterpoltaion(plugin);
        startTargetUpdate(plugin);
    }

    public void setFinalLocation(Location newLocation) {
        this.finalLocation = newLocation;
    }

    public void stop() {
        if (interpolationTask != null) {
            isStop = true;
            finalLocation = basicLocation;
            INTERPOLATION_SPEED += 0.2;
//            interpolationTask.cancel();
//            interpolationTask = null;
        }
        if (targetUpdateTask != null) {
            targetUpdateTask.cancel();
            targetUpdateTask = null;
        }
    }

    private void startInterpoltaion(PluginProvider plugin) {
        interpolationTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                double newX = interpolate(currentLocation.getX(), finalLocation.getX(), INTERPOLATION_SPEED);
                double newY = interpolate(currentLocation.getY(), finalLocation.getY(), INTERPOLATION_SPEED);
                double newZ = interpolate(currentLocation.getZ(), finalLocation.getZ(), INTERPOLATION_SPEED);

                currentLocation = new Location(currentLocation.getWorld(), newX, newY, newZ);

                player.setCompassTarget(currentLocation);

                if (isStop && currentLocation.distance(finalLocation) < 0.1) {
                    INTERPOLATION_SPEED -= 0.2;
                    cancel();
                    return;
                }
            }
        };
        interpolationTask.runTaskTimer(plugin, 0L, INTERPOLATION_INTERVAL);
    }

    private void startTargetUpdate(PluginProvider plugin) {
        targetUpdateTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }

                setFinalLocation(getRandLocation(player.getWorld()));
            }
        };
        targetUpdateTask.runTaskTimer(plugin, 0L, TARGET_UPDATE_INTERVAL);
    }

    private double interpolate(double current, double target, double speed) {
        return current + (target - current) * speed;
    }

    private Location getRandLocation(World world) {
        double randX = rand.nextDouble(10001) - 5000;
        double randY = rand.nextDouble(101) - 50;
        double randZ = rand.nextDouble(10001) - 5000;

        if (finalLocation != null) {
            randX = finalLocation.getX() >= 0 ? -Math.abs(randX) : Math.abs(randX);
            randY = finalLocation.getY() >= 0 ? -Math.abs(randY) : Math.abs(randY);
            randZ = finalLocation.getZ() >= 0 ? -Math.abs(randZ) : Math.abs(randZ);
        }

        return new Location(world, randX, randY, randZ);
    }
}
