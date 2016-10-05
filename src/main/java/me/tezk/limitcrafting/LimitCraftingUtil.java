package me.tezk.limitcrafting;

import me.tezk.limitcrafting.*;
import java.util.Iterator;
import java.util.List;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * Created by Fang_Shadow
 * basic utilities to allow the adding and removing of items to block as well as listing - 9/29/16
 */

public class LimitCraftingUtil {
	private String output;
	private List<String> items;
	private int ID;
	private Material MAT;
	
	private LimitCraftingPlugin plugin;
	private CommandSender sender;
	
	public LimitCraftingUtil(LimitCraftingPlugin pl, CommandSender sender) {
		this.plugin = pl;
		this.sender = sender;
		this.items = plugin.getItems();
	}
	public LimitCraftingUtil(LimitCraftingPlugin pl) {
		this.plugin = pl;
		this.items = plugin.getItems();
		//this.sender = sender;
	}
	public LimitCraftingUtil(CommandSender sender) {
		//this.plugin = pl;
		this.sender = sender;
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public String addBlock(String val){

		this.MAT = null;
		
		try{
			ID = Integer.parseInt(val);
			MAT = Material.getMaterial(ID);
		}
		catch(NumberFormatException e){
			MAT = Material.matchMaterial(val);
		}
		if (MAT == null){
			this.output = ChatColor.RED.BOLD +val+" is not a vaild item id/name!";
		}
		else{
			items.add(val);
			plugin.getConfig().set("items", items);
			saveConfig();
			this.output = ChatColor.BOLD.GREEN +val+" was added to the list & the config has been saved & reloaded!";
		}
		return output;
	}
	
	@SuppressWarnings("static-access")
	public String remBlock(String val){
		try{	
		for (String item : items) {
				if (item.equalsIgnoreCase(val)) {
					items.remove(val);
					plugin.getConfig().set("items", items);
					saveConfig();
					this.output = ChatColor.BOLD.GREEN+"Item "+val+" was removed from the blocked list!";
					break;
				}
			}
		}
		catch(NullPointerException e){
			this.output = ChatColor.RED + "There is no item blocked by that id! "+val;
		}
		return output;
	}
	@SuppressWarnings("deprecation")
	public void listBlock(){
		sender.sendMessage(ChatColor.DARK_RED+"----Blocked Items---");
		for (String item : items) {
			//Testing this code below
			try{
				ID = Integer.parseInt(item);
				MAT = Material.getMaterial(ID);
				sender.sendMessage(ChatColor.RED+item+"("+MAT+")");
			}
			catch(NumberFormatException e){
				MAT = Material.matchMaterial(item);
				sender.sendMessage(ChatColor.RED + MAT.toString());
			}
			//end of testing code
			//sender.sendMessage(ChatColor.RED + item);
		}
		sender.sendMessage(ChatColor.DARK_RED+"--------------------");
	}
	public void saveConfig(){
		plugin.saveConfig();
		plugin.reloadConfig();
		plugin.load();
	}
	public String reloadPlugin(){
		plugin.getConfig();
		plugin.saveConfig();
		plugin.getServer().getPluginManager().disablePlugin(plugin);
		plugin.getServer().getPluginManager().enablePlugin(plugin);
		plugin.load();
		this.output = "Reloaded plugin " +ChatColor.GREEN+plugin.getName() + " " + plugin.getDescription().getVersion();
		return output;
	}
	public boolean hasPermission(CommandSender sender, String perm){
		if((sender == null)||(sender.isOp())){
			return true;
		}
		if(sender.hasPermission(perm)){
				
			return true;
		}
		final String[] nodes = perm.split("\\.");
        final StringBuilder n = new StringBuilder();
        for (int i = 0; i < (nodes.length - 1); i++) {
            n.append(nodes[i]).append(".");
            if (sender.hasPermission(n + "*")) {
                return true;
            }
        }
		
		return false;
	}
	@SuppressWarnings("deprecation")
	public void itemPermList(){
		sender.sendMessage(ChatColor.BLUE+"---Permission Nodes---");
		for (String item : items) {
			//Testing this code below
			try{
				ID = Integer.parseInt(item);
				MAT = Material.getMaterial(ID);
				sender.sendMessage(ChatColor.BLUE+"limitcrafting."+MAT.name().toLowerCase());
			}
			catch(NumberFormatException e){
				MAT = Material.matchMaterial(item);
				sender.sendMessage(ChatColor.BLUE +"limitcrafting."+MAT.name().toLowerCase());
			}
			//end of testing code
			//sender.sendMessage(ChatColor.RED + item);
		}
		sender.sendMessage(ChatColor.BLUE+"----------------------");
	}
}
