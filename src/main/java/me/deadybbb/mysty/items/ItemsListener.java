package me.deadybbb.mysty.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.deadybbb.mysty.items.instances.Respirator;
import me.deadybbb.ybmj.PluginProvider;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemsListener implements Listener {
    private final PluginProvider plugin;
    private static final int FILTER_MAX_DUR = 100;
    private static final ItemStack FILLER = createFiller();

    public ItemsListener(PluginProvider plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack cursor = event.getCursor();
        ItemStack current = event.getCurrentItem();

        if (cursor == null || current == null) return;

        ItemStack resp = null;
        ItemStack filter = null;

        if (event.getClick() == ClickType.LEFT) {
            if (cursor.getType() == Material.AIR || current.getType() == Material.AIR) return;

            if (CraftRegistry.isItemEqual(current, "resp")) {
                if (CraftRegistry.isItemEqual(cursor, "filter")) {
                    filter = cursor;
                    resp = current;
                } else {
                    event.setCancelled(true);
                    return;
                }
            } else if (CraftRegistry.isItemEqual(cursor, "resp")) {
                if (CraftRegistry.isItemEqual(current, "filter")) {
                    filter = current;
                    resp = cursor;
                } else {
                    event.setCancelled(true);
                    return;
                }
            }

            if (resp == null || filter == null) return;

            Integer maxDur = filter.getData(DataComponentTypes.MAX_DAMAGE);
            Integer damage = filter.getData(DataComponentTypes.DAMAGE);
            if (maxDur == null || damage == null) return;

            int remainingDur = maxDur - damage;
            if (remainingDur <= 0) return;

            BundleMeta meta = (BundleMeta) resp.getItemMeta();
            int size = meta.getItems().size();
            if (size == 2) {
                event.setCancelled(true);
                return;
            }

            ItemStack fillStack = FILLER.clone();
            int fillAmount = (int) Math.round(remainingDur * 0.32);
            fillStack.setAmount(Math.min(fillAmount, 32));
            meta.addItem(fillStack);
            size = meta.getItems().size();
            if (size == 0) {
                meta.setItemModel(Respirator.getModel2());
            } else if (size == 1) {
                meta.setItemModel(Respirator.getModel1());
            } else {
                meta.setItemModel(Respirator.getModel0());
            }
            resp.setItemMeta(meta);

            // Consume the filter
            if (filter == cursor) {
                event.setCursor(new ItemStack(Material.AIR));
            } else {
                event.setCurrentItem(new ItemStack(Material.AIR));
            }

            event.setCancelled(true);
        } else if (event.getClick() == ClickType.RIGHT) {
            if (current.getType() == Material.AIR) {
                if (CraftRegistry.isItemEqual(cursor, "resp")) {
                    if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
                        event.setCancelled(true);
                        return;
                    }
                    resp = cursor;
                } else {
                    return;
                }
            } else if (cursor.getType() == Material.AIR) {
                if (CraftRegistry.isItemEqual(current, "resp")) {
                    resp = current;
                } else {
                    return;
                }
            }

            if (resp == null) return;

            BundleMeta meta = (BundleMeta) resp.getItemMeta();
            List<ItemStack> items = meta.getItems();

            ItemStack smallestFiller = null;
            int smallestAmount = Integer.MAX_VALUE;
            for (ItemStack item : items) {
                int amount = item.getAmount();
                if (amount < smallestAmount) {
                    smallestAmount = amount;
                    smallestFiller = item;
                }
            }

            if (smallestFiller == null) return;

            int fillerAmount = smallestFiller.getAmount();
            int restoredDurability = (int) Math.round(fillerAmount / 0.32);

            ItemStack newFilter = CraftRegistry.getItem("filter");
            newFilter.setData(DataComponentTypes.DAMAGE, Math.max(0, FILTER_MAX_DUR - restoredDurability));

            List<ItemStack> newItems = new ArrayList<>();
            for (ItemStack item : items) {
                if (item != smallestFiller) {
                    newItems.add(item);
                }
            }
            meta.setItems(newItems);
            int size = meta.getItems().size();
            if (size == 0) {
                meta.setItemModel(Respirator.getModel2());
            } else if (size == 1) {
                meta.setItemModel(Respirator.getModel1());
            } else {
                meta.setItemModel(Respirator.getModel0());
            }

            resp.setItemMeta(meta);

            if (resp == current) {
                event.setCursor(newFilter);
            } else {
                event.setCurrentItem(newFilter);
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item != null && CraftRegistry.isItemEqual(item, "resp")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        CraftingInventory inv = event.getInventory();
        ItemStack result = inv.getResult();

        if (result == null || result.getType() == Material.AIR) {
            return;
        }

        boolean hasResp = false;
        boolean hasDye = false;

        for (ItemStack item : inv.getMatrix()) {
            if (item == null || item.getType() == Material.AIR) continue;

            if (CraftRegistry.isItemEqual(item, "resp")) {
                hasResp = true;
            } else if (item.getType().name().toLowerCase().contains("dye")) {
                hasDye = true;
            }
        }

        if (hasResp && hasDye) {
            inv.setResult(new ItemStack(Material.AIR));
        }
    }

    private static ItemStack createFiller() {
        ItemStack filler = ItemStack.of(Material.GRAY_DYE);
        filler.setData(DataComponentTypes.ITEM_NAME, Component.text("хуй"));
        filler.setData(DataComponentTypes.HIDE_TOOLTIP);
        return filler;
    }
}
