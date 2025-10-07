package me.deadybbb.mysty;

import me.deadybbb.customzones.listeners.ZoneListenerRegistry;
import me.deadybbb.mysty.compasnoise.CompassNoiseListener;
import me.deadybbb.ybmj.PluginProvider;

public final class Mysty extends PluginProvider {

    @Override
    public void onEnable() {
        ZoneListenerRegistry.registerListener(this, new CompassNoiseListener(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
