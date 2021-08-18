package me.MuchDan.GlobalBoosters.Events;

import com.gamingmesh.jobs.api.JobsExpGainEvent;
import me.MuchDan.GlobalBoosters.ConfigManager.ActiveBoostDataManager;
import me.MuchDan.GlobalBoosters.GlobalBoosters;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobsexpGainListener implements Listener {
    private GlobalBoosters plugin;
    private ActiveBoostDataManager ActiveData;

    public JobsexpGainListener(GlobalBoosters plugin){
        this.plugin = plugin;
        ActiveData = this.plugin.getActiveBoostData();
    }
    @EventHandler
    public void JobsGainExpEvent(JobsExpGainEvent Event){
        double exp = Event.getExp();
        if(ActiveData.getConfig().contains("Active.Jobs")){
            exp = exp * ActiveData.getConfig().getDouble("Active.Jobs.Multiplier");
        }
        Event.setExp(exp);
    }
}
