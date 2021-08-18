package me.MuchDan.GlobalBoosters.Events;

import me.MuchDan.GlobalBoosters.ConfigManager.ActiveBoostDataManager;
import me.MuchDan.GlobalBoosters.GlobalBoosters;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class MinecraftExpListener implements Listener {
    public GlobalBoosters plugin;
    public ActiveBoostDataManager ActiveData;

    public MinecraftExpListener(GlobalBoosters plugin){
        this.plugin = plugin;
        this.ActiveData = this.plugin.getActiveBoostData();
    }

    @EventHandler
    public void GainExpEvent(PlayerExpChangeEvent Event){
        double Exp = Event.getAmount();
        if(ActiveData.getConfig().contains("Active.Minecraft")){
            Exp = Exp * ActiveData.getConfig().getDouble("Active.Minecraft.Multiplier");
        }
        Event.setAmount((int) Exp); //This will round depending on the compiler.
    }

}
