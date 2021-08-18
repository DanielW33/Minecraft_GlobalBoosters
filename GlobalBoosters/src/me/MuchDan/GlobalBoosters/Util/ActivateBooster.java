package me.MuchDan.GlobalBoosters.Util;

import me.MuchDan.GlobalBoosters.ConfigManager.ActiveBoostDataManager;
import me.MuchDan.GlobalBoosters.ConfigManager.BoostersFileManager;
import me.MuchDan.GlobalBoosters.GlobalBoosters;
import me.MuchDan.GlobalBoosters.ObjectFiles.BoosterObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class ActivateBooster {
    private GlobalBoosters plugin;
    private ActiveBoostDataManager ActiveData;
    private BoostersFileManager BoosterData;
    public ActivateBooster(GlobalBoosters plugin){
        this.plugin = plugin;
        ActiveData = this.plugin.getActiveBoostData();
        BoosterData = this.plugin.getBoosterData();
    }
    ///////////////////////////////////////////
    //
    //  StartBoost
    //  Checks to see if a boost is already active. If so, it adds more time to the clock.
    //  if boost is not active, it adds the booster to the config file and starts the timer.
    //
    public void StartBoost(BoosterObject Booster){
        int Time;
        if(CheckBoosterStatus(Booster)){
            if(!Double.toString(ActiveData.getConfig().getDouble("Active." + Booster.getType() + ".Multiplier")).equals(Double.toString(Booster.getMultiplier()))){
                GlobalBoosters.Queue.add(Booster);
                if(ActiveData.getConfig().contains("Queue." + Booster.getSectionName())){
                    int Current = ActiveData.getConfig().getInt("Queue." + Booster.getSectionName() + ".InQueue");
                    Current = Current + 1;
                    ActiveData.getConfig().set("Queue." + Booster.getSectionName() + ".InQueue", Current);
                }
                else{
                    ActiveData.getConfig().set("Queue." + Booster.getSectionName() + ".InQueue", 1);
                }
            }
            else {
                Time = ActiveData.getConfig().getInt("Active." + Booster.getType() + ".Time");
                Time = Time + Booster.getTime();
                ActiveData.getConfig().set("Active." + Booster.getType() + ".Time", Time);
                ActiveData.saveConfig();

                plugin.getLogger().log(Level.INFO, Booster.getType() + " Booster has been Extended.");
                String Message = BoosterData.getConfig().getString("ExtendMessage");
                Message = Message.replace("%Booster%", Booster.getType());
                Message = ChatColor.translateAlternateColorCodes('&', Message);
                SendMessage(Message);
            }
        }
        else{
            //Adding Booster to config.
            ActiveData.getConfig().set("Active." + Booster.getType() + ".Time", Booster.getTime());
            ActiveData.getConfig().set("Active." + Booster.getType() + ".Multiplier", Booster.getMultiplier());
            ActiveData.saveConfig();

            //Initializing Booster Timer.
            Timer time = new Timer(plugin);
            time.setInstanceType(Booster.getType());
            time.StartTimer(Booster.getTime());

            //Handling Start Message
            plugin.getLogger().log(Level.INFO, Booster.getType() + " Booster has been Activated.");
            String Message = BoosterData.getConfig().getString("StartMessage");
            Message = Message.replace("%Booster%", Booster.getType());
            Message = ChatColor.translateAlternateColorCodes('&', Message);
            SendMessage(Message);

        }
    }

    private boolean CheckBoosterStatus(BoosterObject Booster){
        if(ActiveData.getConfig().contains("Active." + Booster.getType())){
            return true;
        }
        return false;
    }
    private void SendMessage(String Message){
        for(Player player : Bukkit.getOnlinePlayers()){
            player.sendMessage(Message);
        }
    }
}
