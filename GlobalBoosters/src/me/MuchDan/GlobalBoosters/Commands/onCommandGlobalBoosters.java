package me.MuchDan.GlobalBoosters.Commands;

import me.MuchDan.GlobalBoosters.ConfigManager.ActiveBoostDataManager;
import me.MuchDan.GlobalBoosters.ConfigManager.BoostersFileManager;
import me.MuchDan.GlobalBoosters.ConfigManager.InventoryGUIManager;
import me.MuchDan.GlobalBoosters.GlobalBoosters;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class onCommandGlobalBoosters implements CommandExecutor {
    private BoostersFileManager BoosterData;
    private InventoryGUIManager InventoryData;
    private ActiveBoostDataManager ActiveData;
    private GlobalBoosters plugin;

    public onCommandGlobalBoosters(GlobalBoosters plugin){
        this.plugin = plugin;
        BoosterData = this.plugin.getBoosterData();
        InventoryData = this.plugin.getInventoryData();
        ActiveData = this.plugin.getActiveBoostData();
    }
    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args){
        if(label.equalsIgnoreCase("Globalboosters")){
            if(args.length < 1){
                HelpMessages(Sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("reload")){
                //Refreshing Config files.
                this.InventoryData.reloadConfig();
                this.BoosterData.reloadConfig();
                //Clearing Static arraylists
                GlobalBoosters.Boosters.clear();
                GlobalBoosters.Inventory.clear();
                //Rebuilding static array lists
                this.plugin.BuildBoosterList();
                this.plugin.BuildInventory();
                Sender.sendMessage(ChatColor.GREEN + "Successfully reload GlobalBoosters config files.");
                return true;
            }
            else if(args[0].equalsIgnoreCase("List")){
                GlobalBoosters.Boosters.forEach(Booster -> {
                    Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7" + Integer.toString(GlobalBoosters.Boosters.indexOf(Booster))));
                    Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Booster: " + Booster.getSectionName()));
                    Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Duration: " + Booster.getTime()));
                });
            }
            else if(args[0].equalsIgnoreCase("Clear")){
                if(args.length < 2){
                    Sender.sendMessage(ChatColor.RED + "Clearing all boosters.");
                    MakeNull("Minecraft");
                    MakeNull("mcMMO");
                    MakeNull("Jobs");
                }
                else if(args[1].equalsIgnoreCase("Minecraft")){
                    MakeNull("Minecraft");
                }
                else if(args[1].equalsIgnoreCase("mcMMO")){
                    MakeNull("mcMMO");
                }
                else if(args[1].equalsIgnoreCase("Jobs")){
                    MakeNull("Jobs");
                }
                else{
                    Sender.sendMessage(ChatColor.RED + "Invalid Type.");
                }
                return true;
            }
            else if(args[0].equalsIgnoreCase("Help")){
                HelpMessages(Sender);
                return true;
            }
            else if(args[0].equalsIgnoreCase("Active")){
                if(ActiveData.getConfig().contains("Active.Minecraft") || ActiveData.getConfig().contains("Active.mcMMO") || ActiveData.getConfig().contains("Active.Jobs")) {
                    Sender.sendMessage("Currently active: ");
                    ActiveData.getConfig().getConfigurationSection("Active").getKeys(false).forEach(Type -> {
                        Sender.sendMessage(Type);
                        Sender.sendMessage("Time remaining: " + ActiveData.getConfig().getInt("Active." + Type + ".Time"));
                    });
                }
                else{
                    Sender.sendMessage("There are no boosters active at this time.");
                }
            }
        }
        return false;
    }
    public void MakeNull(String type){
        if(ActiveData.getConfig().contains("Active." + type)) {
            ActiveData.getConfig().set("Active." + type + ".Time", null);
            ActiveData.getConfig().set("Active." + type + ".Multiplier", null);
            ActiveData.getConfig().set("Active." + type, null);
            ActiveData.saveConfig();
        }
    }
    private void HelpMessages(CommandSender Sender){
        Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7======== &bGlobal Boosters &7========"));
        Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Use command &b/GlobalBoosters reload &7to reload config files."));
        Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Use command &b/GlobalBoosters list &7to list available boosters"));
        Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Use command &b/GlobalBoosters clear &7to clear boosters from config."));
        Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Use command &b/ActivateBooster [BoosterName] &7to start a global booster"));
        Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Use command &b/boosters &7to view the booster GUI."));
        Sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7=============================="));
    }
}
