package me.deadybbb.mysty.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class ItemsListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();

        CustomModelData cmd = item.getData(DataComponentTypes.CUSTOM_MODEL_DATA);
        if (cmd != null && cmd.strings().contains("mysty")) {
            if (item.getType() == Material.CARVED_PUMPKIN) {
                event.setCancelled(true);
                event.getPlayer().getInventory().removeItem(item);
                event.getPlayer().getInventory().setHelmet(item);
            }
        }
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack item = event.getInventory().getFirstItem();
        if (item == null) return;

        CustomModelData cmd = item.getData(DataComponentTypes.CUSTOM_MODEL_DATA);
        if (cmd != null && cmd.strings().contains("mysty")) {
            event.setResult(item);
        }
    }
}
