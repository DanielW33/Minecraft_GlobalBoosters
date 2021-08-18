package me.MuchDan.GlobalBoosters.Events;

import com.gamingmesh.jobs.api.JobsPrePaymentEvent;
import me.MuchDan.GlobalBoosters.ConfigManager.ActiveBoostDataManager;
import me.MuchDan.GlobalBoosters.ConfigManager.BoostersFileManager;
import me.MuchDan.GlobalBoosters.GlobalBoosters;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JobsMoneyGainListener implements Listener {
    private GlobalBoosters plugin;
    private ActiveBoostDataManager ActiveData;
    private BoostersFileManager BoosterData;
    public JobsMoneyGainListener(GlobalBoosters plugin){

        this.plugin = plugin;
        ActiveData = this.plugin.getActiveBoostData();
        BoosterData = this.plugin.getBoosterData();
    }
    @EventHandler
    public void JobsMoneyGainEvent(JobsPrePaymentEvent Event){
        double Amount = Event.getAmount();
        if(ActiveData.getConfig().contains("Active.Jobs") && BoosterData.getConfig().getBoolean("JobsMoneyMultiplier")){
            Amount = Amount * ActiveData.getConfig().getDouble("Active.Jobs.Multiplier");
        }
        Event.setAmount(Amount);
    }
}
