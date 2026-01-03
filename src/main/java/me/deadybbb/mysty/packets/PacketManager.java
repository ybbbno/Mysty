package me.deadybbb.mysty.packets;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.deadybbb.ybmj.PluginProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PacketManager {

    private final PluginProvider plugin;

    public PacketManager(PluginProvider plugin) {
        this.plugin = plugin;
    }

    public void sendFogPacket(Player player, boolean enabled) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeBoolean(enabled);

        player.sendPluginMessage(
                plugin,
                "mysty:fog_toggle",
                out.toByteArray()
        );
    }

    public void broadcastFogToggle(boolean enabled) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            sendFogPacket(player, enabled);
        });
    }
}
