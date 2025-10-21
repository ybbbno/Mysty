package me.deadybbb.mysty;

import me.deadybbb.customzones.listeners.ZoneListenerRegistry;
import me.deadybbb.mysty.clocknoise.ClockNoiseListener;
import me.deadybbb.mysty.compasnoise.CompassNoiseListener;
import me.deadybbb.mysty.items.CraftRegistry;
import me.deadybbb.mysty.items.ItemsListener;
import me.deadybbb.mysty.mobcontrol.BiobunkerListener;
import me.deadybbb.mysty.mobcontrol.DrownedbunkerListener;
import me.deadybbb.mysty.mobcontrol.NetherbunkerListener;
import me.deadybbb.mysty.mobcontrol.OutpostListener;
import me.deadybbb.ybmj.PluginProvider;

public final class Mysty extends PluginProvider {

    @Override
    public void onEnable() {
        CraftRegistry.registerItems(this);
        getServer().getPluginManager().registerEvents(new ItemsListener(this), this);

        ZoneListenerRegistry.registerListener(this, new CompassNoiseListener(this));
        ZoneListenerRegistry.registerListener(this, new ClockNoiseListener(this));
        ZoneListenerRegistry.registerListener(this, new BiobunkerListener());
        ZoneListenerRegistry.registerListener(this, new DrownedbunkerListener());
        ZoneListenerRegistry.registerListener(this, new OutpostListener());
        ZoneListenerRegistry.registerListener(this, new NetherbunkerListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
