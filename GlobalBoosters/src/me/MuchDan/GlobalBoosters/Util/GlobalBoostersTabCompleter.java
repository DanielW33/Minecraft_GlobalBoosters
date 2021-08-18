package me.MuchDan.GlobalBoosters.Util;

import me.MuchDan.GlobalBoosters.GlobalBoosters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class GlobalBoostersTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender Sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("GlobalBoosters")) {
            if (args.length == 1) {
                List<String> Commands = new ArrayList<>();
                Commands.add("Reload");
                Commands.add("List");
                Commands.add("Clear");
                Commands.add("Help");
                Commands.add("Active");
                return Commands;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("Clear")) {
                List<String> Commands = new ArrayList<>();
                Commands.add("Minecraft");
                Commands.add("mcMMO");
                Commands.add("Jobs");
                return Commands;
            }
            return null;
        } else if (label.equalsIgnoreCase("ActivateBooster")) {
            List<String> Commands = new ArrayList<>();
            GlobalBoosters.Boosters.forEach(Booster -> {
                Commands.add(Booster.getSectionName());
            });
            return Commands;
        }

        return null;

    }
}
