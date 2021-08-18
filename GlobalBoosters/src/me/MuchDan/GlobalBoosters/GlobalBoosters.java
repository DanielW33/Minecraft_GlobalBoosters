package me.MuchDan.GlobalBoosters;

import me.MuchDan.GlobalBoosters.Commands.onCommandActivateBooster;
import me.MuchDan.GlobalBoosters.Commands.onCommandBoosters;
import me.MuchDan.GlobalBoosters.Commands.onCommandGlobalBoosters;
import me.MuchDan.GlobalBoosters.ConfigManager.ActiveBoostDataManager;
import me.MuchDan.GlobalBoosters.ConfigManager.BoostersFileManager;
import me.MuchDan.GlobalBoosters.ConfigManager.InventoryGUIManager;
import me.MuchDan.GlobalBoosters.Events.*;
import me.MuchDan.GlobalBoosters.ObjectFiles.BoosterObject;
import me.MuchDan.GlobalBoosters.ObjectFiles.Item;

import me.MuchDan.GlobalBoosters.Util.GlobalBoostersTabCompleter;
import me.MuchDan.GlobalBoosters.Util.Timer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class GlobalBoosters extends JavaPlugin {
    private BoostersFileManager BoosterData;
    private InventoryGUIManager InventoryData;
    private ActiveBoostDataManager ActiveBoostData;
    public static List<Item> Inventory;
    public static List<BoosterObject> Boosters;
    public static List<BoosterObject> Queue;
    @Override
    public void onEnable(){
        Inventory = new ArrayList<>();
        Boosters = new ArrayList<>();
        Queue = new ArrayList<>();

        //Initializing plugin config files
        this.BoosterData = new BoostersFileManager(this);
        this.InventoryData = new InventoryGUIManager(this);
        this.ActiveBoostData = new ActiveBoostDataManager(this);
        this.BoosterData.getConfig().options().copyDefaults(false);
        this.BoosterData.saveDefaultConfig();
        this.InventoryData.getConfig().options().copyDefaults(false);
        this.InventoryData.saveDefaultConfig();
        this.ActiveBoostData.getConfig().options().copyDefaults(false);
        this.ActiveBoostData.saveDefaultConfig();
        //////////////////////////////////////////////////////////

        //Checking for softdepend plugins.
        if(this.getServer().getPluginManager().getPlugin("mcMMO") == null && this.BoosterData.getConfig().getBoolean("mcMMO")){
            this.getLogger().log(Level.SEVERE, "Mcmmo is enabled in config, but not found in your plugin list.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        else if(this.getServer().getPluginManager().getPlugin("mcMMO") != null && this.BoosterData.getConfig().getBoolean("mcMMO")){
            this.getLogger().log(Level.INFO, "Successfully Hooked into mcMMO!");
        }
        if(this.getServer().getPluginManager().getPlugin("Jobs") == null && this.BoosterData.getConfig().getBoolean("Jobs")){
            this.getLogger().log(Level.SEVERE, "Jobs is enabled in config, but not found in your plugin list.");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        else if(this.getServer().getPluginManager().getPlugin("Jobs") != null && this.BoosterData.getConfig().getBoolean("Jobs")){
            this.getLogger().log(Level.INFO, "Successfully Hooked into Jobs!");
        }

        if(ActiveBoostData.getConfig().contains("Active.Minecraft")){
            Timer timer1 = new Timer(this);
            timer1.setInstanceType("Minecraft");
            timer1.StartTimer(ActiveBoostData.getConfig().getInt("Active.Minecraft.Time"));
            this.getLogger().log(Level.INFO, "Minecraft Global Exp Booster is Active. Starting timer.");
        }
        if(ActiveBoostData.getConfig().contains("Active.mcMMO")){
            Timer timer2 = new Timer(this);
            timer2.setInstanceType("mcMMO");
            timer2.StartTimer(ActiveBoostData.getConfig().getInt("Active.mcMMO.Time"));
            this.getLogger().log(Level.INFO, "mcMMO Global Exp Booster is Active. Starting Timer.");
        }
        if(ActiveBoostData.getConfig().contains("Active.Jobs")){
            Timer timer3 = new Timer(this);
            timer3.setInstanceType("Jobs");
            timer3.StartTimer(ActiveBoostData.getConfig().getInt("Active.Jobs.Time"));
            this.getLogger().log(Level.INFO, "Jobs Global Exp Booster is Active. Starting Timer.");
        }

        BuildInventory();
        BuildBoosterList();
        BuildQueue();

        this.getCommand("GlobalBoosters").setExecutor(new onCommandGlobalBoosters(this));
        this.getCommand("Boosters").setExecutor(new onCommandBoosters(this));
        this.getCommand("ActivateBooster").setExecutor(new onCommandActivateBooster(this));
        GlobalBoostersTabCompleter TabCompleter = new GlobalBoostersTabCompleter();
        this.getCommand("GlobalBoosters").setTabCompleter(TabCompleter);
        this.getCommand("Boosters").setTabCompleter(TabCompleter);
        this.getCommand("ActivateBooster").setTabCompleter(TabCompleter);

        this.getServer().getPluginManager().registerEvents(new PlayerClickBoostersInventoryEvent(this),this);
        this.getServer().getPluginManager().registerEvents(new MinecraftExpListener(this), this);
        this.getServer().getPluginManager().registerEvents(new mcMMOExpGainListener(this),this);
        this.getServer().getPluginManager().registerEvents(new JobsexpGainListener(this),this);
        this.getServer().getPluginManager().registerEvents(new JobsMoneyGainListener(this), this);
    }

    @Override
    public void onDisable(){
        Inventory.clear();
        Boosters.clear();
        Queue.clear();
    }

    public void BuildBoosterList(){
        this.BoosterData.getConfig().getConfigurationSection("Boosters").getKeys(false).forEach(Booster ->{
            BoosterObject Boost = new BoosterObject();

            Boost.setSectionName(Booster);
            Boost.setDisplayName(getBoosterDisplayName(Booster));
            Boost.setType(getBoosterType(Booster));
            Boost.setMultiplier(getBoosterMultiplier(Booster));
            Boost.setPermission(getBoosterPermission(Booster));
            Boost.setTime(getBoosterTime(Booster));

            Boosters.add(Boost);
        });
    }
    public void BuildInventory(){
        this.InventoryData.getConfig().getConfigurationSection("Blocks").getKeys(false).forEach(Section ->{
            Item Setter = new Item();

            Setter.setInventoryPosition(Integer.parseInt(Section));
            Setter.setQuantity(getItemQuantity(Integer.parseInt(Section)));
            Setter.setItemMaterial(getItemMaterial(Integer.parseInt(Section)));
            Setter.setDisplayName(getDisplayName(Integer.parseInt(Section)));
            Setter.setEnchant(getEnchanted(Integer.parseInt(Section)));
            Setter.setLore(getLore(Integer.parseInt(Section)));
            Setter.createBlock();

            Inventory.add(Setter);
        });
    }
    public void BuildQueue(){
        if(ActiveBoostData.getConfig().contains("Queue")){
            ActiveBoostData.getConfig().getConfigurationSection("Queue").getKeys(false).forEach(Boost->{

                BoosterObject Booster = new BoosterObject();
                Booster.setSectionName(Boost);
                Booster.setTime(getBoosterTime(Boost));
                Booster.setMultiplier(getBoosterMultiplier(Boost));
                Booster.setType(getBoosterType(Boost));
                Booster.setDisplayName(getBoosterDisplayName(Boost));

                Queue.add(Booster);
            });
        }
    }
    public String getBoosterDisplayName(String Section){
        return ChatColor.translateAlternateColorCodes('&', this.BoosterData.getConfig().getString("Boosters." + Section + ".DisplayName"));
    }
    public String getBoosterType(String Section){
        return this.BoosterData.getConfig().getString("Boosters." + Section + ".Type");
    }
    public String getBoosterPermission(String Section){
        return this.BoosterData.getConfig().getString("Boosters." + Section + ".Permission");
    }
    public double getBoosterMultiplier(String Section){
        return this.BoosterData.getConfig().getDouble("Boosters." + Section + ".Multiplier");
    }
    public int getBoosterTime(String Section){
        return this.BoosterData.getConfig().getInt("Boosters." + Section + ".Time");
    }
    public ActiveBoostDataManager getActiveBoostData(){
        return ActiveBoostData;
    }

    public String getInventoryName(){
        return ChatColor.translateAlternateColorCodes('&', this.InventoryData.getConfig().getString("InventoryName"));
    }
    public int getInventorySize(){
        return this.InventoryData.getConfig().getInt("InventorySize");
    }
    public BoostersFileManager getBoosterData(){
        return BoosterData;
    }
    public InventoryGUIManager getInventoryData(){
        return InventoryData;
    }

    public int getItemQuantity(int Section){
        return this.InventoryData.getConfig().getInt("Blocks." + Section + ".Quantity");
    }
    public Material getItemMaterial(int Section){
        return Material.matchMaterial(this.InventoryData.getConfig().getString("Blocks." + Section + ".Material"));
    }
    public String getDisplayName(int Section){
        return ChatColor.translateAlternateColorCodes('&',this.InventoryData.getConfig().getString("Blocks." + Section + ".DisplayName"));
    }
    public boolean getEnchanted(int Section){
        return this.InventoryData.getConfig().getBoolean("Blocks." + Section + ".Enchanted");
    }
    public List<String> getLore(int Section){
        List<String> Lore = new ArrayList<>();

        for(String L : this.InventoryData.getConfig().getStringList("Blocks." + Section + ".Lore")){
            L = ChatColor.translateAlternateColorCodes('&', L);
            Lore.add(L);
        }
        return Lore;
    }
}
