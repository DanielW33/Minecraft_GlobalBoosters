package me.MuchDan.GlobalBoosters.Events;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import me.MuchDan.GlobalBoosters.ConfigManager.ActiveBoostDataManager;
import me.MuchDan.GlobalBoosters.GlobalBoosters;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class mcMMOExpGainListener implements Listener {
    private GlobalBoosters plugin;
    private ActiveBoostDataManager ActiveData;
    public mcMMOExpGainListener(GlobalBoosters plugin){
        this.plugin = plugin;
        ActiveData = this.plugin.getActiveBoostData();
    }
    @EventHandler
    public void mcMMOexpGain(McMMOPlayerXpGainEvent Event){
        float Exp = Event.getRawXpGained();
        if(ActiveData.getConfig().contains("Active.mcMMO")){
            String Multiplier = ActiveData.getConfig().getString("Active.mcMMO.Multiplier");
            Exp = Exp * Float.valueOf(Multiplier);
        }
        Event.setRawXpGained(Exp);
    }
}
