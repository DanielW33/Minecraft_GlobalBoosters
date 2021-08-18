package me.MuchDan.GlobalBoosters.Events;

import me.MuchDan.GlobalBoosters.GlobalBoosters;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerClickBoostersInventoryEvent implements Listener {
    public GlobalBoosters plugin;

    public PlayerClickBoostersInventoryEvent(GlobalBoosters plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void BoosterInventoryClick(InventoryClickEvent Event) {
        if (!plugin.getInventoryName().equalsIgnoreCase(Event.getView().getTitle())) {
            return;
        }
        if (Event.getCurrentItem() == null) {
            return;
        }
        Event.setCancelled(true);
        GlobalBoosters.Boosters.forEach(Booster -> {
            if (Event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Booster.getDisplayName())){
                Event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',this.plugin.getInventoryData().getConfig().getString("onClickMessage")));
                Event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getInventoryData().getConfig().getString("WebsiteLink")));
                Event.getView().close();
                return;
            }
        });
    }
}
