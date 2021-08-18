package me.MuchDan.GlobalBoosters.Commands;

import me.MuchDan.GlobalBoosters.GlobalBoosters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class onCommandBoosters implements CommandExecutor {
    public GlobalBoosters plugin;

    public onCommandBoosters(GlobalBoosters plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args){
        if(!label.equalsIgnoreCase("Boosters")){
            return false;
        }
        if(!(Sender instanceof Player)){
            Sender.sendMessage(ChatColor.RED + "You must be ingame to open the Booster GUI");
            return false;
        }
        Player player = (Player)Sender;
        Inventory inv = Bukkit.createInventory(player, plugin.getInventorySize(), plugin.getInventoryName());

        GlobalBoosters.Inventory.forEach(Item->{
            inv.setItem(Item.getInventoryPosition(),Item.getBlock());
        });

        player.openInventory(inv);

        return false;
    }
}
