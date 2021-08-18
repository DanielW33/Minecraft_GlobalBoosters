package me.MuchDan.GlobalBoosters.Util;

import me.MuchDan.GlobalBoosters.ConfigManager.ActiveBoostDataManager;
import me.MuchDan.GlobalBoosters.ConfigManager.BoostersFileManager;
import me.MuchDan.GlobalBoosters.GlobalBoosters;
import me.MuchDan.GlobalBoosters.ObjectFiles.BoosterObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Queue;

public class Timer {
    private int runTime;
    private int TaskID;
    private String InstanceType;
    private GlobalBoosters plugin;
    private ActiveBoostDataManager ActiveData;
    private BoostersFileManager BoostersData;
    private boolean added;
    private BoosterObject BoosterObj;

    public Timer(GlobalBoosters plugin){
        this.plugin = plugin;
        ActiveData = this.plugin.getActiveBoostData();
        BoostersData = this.plugin.getBoosterData();
        runTime = 0;
    }
    public void StartTimer(int StartTime){
        runTime = StartTime;
        BukkitScheduler Scheduler = Bukkit.getServer().getScheduler();
        TaskID = Scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                runTime = ActiveData.getConfig().getInt("Active." + getInstanceType() + ".Time");
                if(runTime <= 0){
                    ActiveData.getConfig().set("Active." + getInstanceType() + ".Time", null);
                    ActiveData.getConfig().set("Active." + getInstanceType() + ".Multiplier", null);
                    ActiveData.getConfig().set("Active." + getInstanceType(), null);
                    ActiveData.saveConfig();
                    String OOT = BoostersData.getConfig().getString("OutofTime");
                    OOT = OOT.replace("%Booster%", getInstanceType());
                    OOT = ChatColor.translateAlternateColorCodes('&', OOT);
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.sendMessage(OOT);
                    }

                    if(!GlobalBoosters.Queue.isEmpty()){
                        added = false;
                        GlobalBoosters.Queue.forEach(Booster ->{
                            if(Booster.getType().equals(getInstanceType()) && !added){
                                added = true;
                                ActivateBooster Boost = new ActivateBooster(plugin);
                                Boost.StartBoost(Booster);
                                BoosterObj = Booster;
                                if(ActiveData.getConfig().contains("Queue." + Booster.getSectionName())){
                                    int InQueue = ActiveData.getConfig().getInt("Queue." + Booster.getSectionName() + ".InQueue");
                                    InQueue = InQueue -1;
                                    if(InQueue == 0){
                                        ActiveData.getConfig().set("Queue." + Booster.getSectionName() + ".InQueue", null);
                                        ActiveData.getConfig().set("Queue." + Booster.getSectionName(), null);
                                    }
                                    else {
                                        ActiveData.getConfig().set("Queue." + Booster.getSectionName() + ".InQueue", InQueue);
                                    }
                                }
                            }
                        });
                        if(BoosterObj != null){
                            GlobalBoosters.Queue.remove(BoosterObj);
                        }
                    }

                    Bukkit.getScheduler().cancelTask(TaskID);
                }
                if(runTime > 0) {
                    runTime--;
                    ActiveData.getConfig().set("Active." + getInstanceType() + ".Time", runTime);
                    ActiveData.saveConfig();
                }
            }
        }, 20L, 20L);

    }
    public void setTime(int Time){
        this.runTime = Time;
    }
    public int getTime() {
        return runTime;
    }
    public void setInstanceType(String Type){
        this.InstanceType = Type;
    }
    public String getInstanceType(){
        return InstanceType;
    }
}
