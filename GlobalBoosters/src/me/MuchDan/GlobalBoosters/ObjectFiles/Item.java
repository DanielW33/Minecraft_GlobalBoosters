package me.MuchDan.GlobalBoosters.ObjectFiles;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Item {
    private int Quantity;
    private Material ItemMaterial;
    private ItemMeta itemMeta;
    private List<String> Lore;
    private String DisplayName;
    private ItemStack Block;
    private boolean Enchant = false;
    private int InventoryPosition;
    ////////////////////////////
    //
    //  Not Null Item
    //
    public Item(){
        Quantity = 1;
        ItemMaterial = Material.BEDROCK;
        Block = new ItemStack(ItemMaterial,Quantity);
        itemMeta = Block.getItemMeta();
        DisplayName = "";
        itemMeta.setDisplayName(DisplayName);

        Block.setItemMeta(itemMeta);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Method : CreateBlock
    //  Returns : ItemStack
    //  Actions : takes private variables, and adds them to itemstack Block, then returns Block.
    //
    public ItemStack createBlock(){
        itemMeta.setDisplayName(DisplayName);
        itemMeta.setLore(Lore);
        Block = new ItemStack(ItemMaterial, Quantity);
        if(Enchant){
            itemMeta.addEnchant(Enchantment.MENDING, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        Block.setItemMeta(itemMeta);
        return Block;
    }
    /////////////////////////////////////////////////
    //
    //  Setters and getters for private Variables.
    //
    public int getQuantity(){
        return Quantity;
    }
    public void setQuantity(int Quantity){
        this.Quantity = Quantity;
    }
    public Material getItemMaterial(){
        return ItemMaterial;
    }
    public void setItemMaterial(Material ItemMaterial){
        this.ItemMaterial = ItemMaterial;
    }
    public ItemMeta getItemMeta(){
        return itemMeta;
    }
    public void setItemMeta(ItemMeta itemMeta){
        this.itemMeta = itemMeta;
    }
    public List<String> getLore(){
        return Lore;
    }
    public void setLore(List<String> Lore){
        this.Lore = Lore;
    }
    public String getDisplayName(){
        return DisplayName;
    }
    public void setDisplayName(String DisplayName){
        this.DisplayName = DisplayName;
    }
    public boolean getEnchant(){
        return Enchant;
    }
    public void setEnchant(boolean Enchant){
        this.Enchant = Enchant;
    }
    public int getInventoryPosition(){
        return InventoryPosition;
    }
    public void setInventoryPosition(int InventoryPosition){
        this.InventoryPosition = InventoryPosition;
    }
    public ItemStack getBlock(){
        return Block;
    }
}
