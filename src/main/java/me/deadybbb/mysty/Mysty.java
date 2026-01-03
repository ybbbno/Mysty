package me.deadybbb.mysty;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.deadybbb.customzones.listeners.ZoneListenerRegistry;
import me.deadybbb.mysty.pillar.ClockNoiseListener;
import me.deadybbb.mysty.commands.FogCommand;
import me.deadybbb.mysty.pillar.CompassNoiseListener;
import me.deadybbb.mysty.items.CraftRegistry;
import me.deadybbb.mysty.items.ItemsListener;
import me.deadybbb.mysty.mobcontrol.instances.*;
import me.deadybbb.mysty.packets.PacketManager;
import me.deadybbb.mysty.pillar.MagnetListener;
import me.deadybbb.ybmj.PluginProvider;

public final class Mysty extends PluginProvider {
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "mysty:fog_toggle");

        PacketManager fog = new PacketManager(this);
        new FogCommand(fog).register();

        CraftRegistry.registerItems(this);
        getServer().getPluginManager().registerEvents(new ItemsListener(this), this);

        ZoneListenerRegistry.registerListener(this, new portal_bunker_first());
        ZoneListenerRegistry.registerListener(this, new portal_bunker_second());
        ZoneListenerRegistry.registerListener(this, new portal_bunker_third());

        ZoneListenerRegistry.registerListener(this, new bio_bunker_first());
        ZoneListenerRegistry.registerListener(this, new bio_bunker_second());
        ZoneListenerRegistry.registerListener(this, new bio_bunker_third());

        ZoneListenerRegistry.registerListener(this, new drowned_bunker_first());
        ZoneListenerRegistry.registerListener(this, new drowned_bunker_second());
        ZoneListenerRegistry.registerListener(this, new drowned_bunker_third());

        ZoneListenerRegistry.registerListener(this, new CompassNoiseListener(this));
        ZoneListenerRegistry.registerListener(this, new ClockNoiseListener(this));
        ZoneListenerRegistry.registerListener(this, new MagnetListener(this));
        ZoneListenerRegistry.registerListener(this, new OutpostListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }
}