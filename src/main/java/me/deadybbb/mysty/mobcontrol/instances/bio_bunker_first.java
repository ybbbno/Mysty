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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

@CustomZonePrefix("bio_bunker_first")
public class bio_bunker_first implements Listener {

    private static final int MAX_MOBS_PER_GROUP = 3;
    private static final int MAX_ENTITIES = 8;
    private static final double CHANCE_PER_MINUTE = 5.0;

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

        EntityType mob = Checker.chooseEntity(List.of(EntityType.SLIME, EntityType.BOGGED));

        int max_mobs_per_group = MAX_MOBS_PER_GROUP;
        int range = MAX_ENTITIES - entitiesCount;
        if (max_mobs_per_group >= range) {
            max_mobs_per_group = range;
        }

        List<Location> spawnLoc = MobController.findGroupSpawnLocations(zone, mob, max_mobs_per_group, false);
        for (Location loc : spawnLoc) {
            world.spawnEntity(loc, mob, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }
}