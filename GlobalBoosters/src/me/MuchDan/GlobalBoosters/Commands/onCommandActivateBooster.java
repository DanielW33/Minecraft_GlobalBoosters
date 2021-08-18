package me.MuchDan.GlobalBoosters.Commands;

import me.MuchDan.GlobalBoosters.GlobalBoosters;
import me.MuchDan.GlobalBoosters.ObjectFiles.BoosterObject;
import me.MuchDan.GlobalBoosters.Util.ActivateBooster;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class onCommandActivateBooster implements CommandExecutor {
    public GlobalBoosters plugin;

    public onCommandActivateBooster(GlobalBoosters plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args){
        if(label.equalsIgnoreCase("ActivateBooster")){
            if(args.length < 1){
                Sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7To activate a booster. Use command &b/ActivateBooster [BoosterName]"));
                return false;
            }
            for(BoosterObject Boost : GlobalBoosters.Boosters){
                if(Boost.getSectionName().equalsIgnoreCase(args[0])){
                    if(Boost.getType().equalsIgnoreCase("Minecraft") || Boost.getType().equalsIgnoreCase("mcMMO") || Boost.getType().equalsIgnoreCase("Jobs")){
                        ActivateBooster Booster = new ActivateBooster(plugin);
                        Booster.StartBoost(Boost);
                        return true;
                    }
                    else {
                        Sender.sendMessage(ChatColor.RED + "The booster section name was found, but the booster type was not Minecraft, mcMMO, or Jobs");
                        Sender.sendMessage(ChatColor.RED + "Please correct this error in the Boosters.yml Config file.");
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
