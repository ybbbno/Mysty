// src/main/java/me/deadybbb/mysty/commands/FogCommand.java
package me.deadybbb.mysty.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import me.deadybbb.mysty.packets.PacketManager;
import org.bukkit.entity.Player;

import java.util.Collection;

public class FogCommand {

    private final PacketManager fogManager;

    public FogCommand(PacketManager fogManager) {
        this.fogManager = fogManager;
    }

    public void register() {
        new CommandAPICommand("fog")
                .withPermission("mysty.fog")
                .withSubcommand(
                        new CommandAPICommand("on")
                                .withOptionalArguments(new EntitySelectorArgument.ManyPlayers("targets"))
                                .executes((sender, args) -> {
                                    @SuppressWarnings("unchecked")
                                    Collection<Player> targets = (Collection<Player>) args.getOrDefault("targets", getSelf(sender));
                                    targets.forEach(p -> fogManager.sendFogPacket(p, true));
                                    sendFeedback(sender, targets, true);
                                })
                )
                .withSubcommand(
                        new CommandAPICommand("off")
                                .withOptionalArguments(new EntitySelectorArgument.ManyPlayers("targets"))
                                .executes((sender, args) -> {
                                    @SuppressWarnings("unchecked")
                                    Collection<Player> targets = (Collection<Player>) args.getOrDefault("targets", getSelf(sender));
                                    targets.forEach(p -> fogManager.sendFogPacket(p, false));
                                    sendFeedback(sender, targets, false);
                                })
                )
                .register();
    }

    private Collection<Player> getSelf(org.bukkit.command.CommandSender sender) {
        if (sender instanceof Player player) {
            return java.util.List.of(player);
        }
        throw new IllegalArgumentException("Укажи цель или используй @a");
    }

    private void sendFeedback(org.bukkit.command.CommandSender sender, Collection<Player> targets, boolean enabled) {
        String state = enabled ? "включён" : "выключен";
        String target = targets.size() == 1
                ? targets.iterator().next().getName()
                : targets.size() + " игрокам";
        sender.sendMessage("§aТуман " + state + " для §f" + target + "§a.");
    }
}