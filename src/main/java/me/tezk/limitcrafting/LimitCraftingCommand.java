package me.tezk.limitcrafting;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Tom Micallef on 23/12/2015.
 * Edited by Fang_Shadow on 29/9/16.
 * 
 * Changes
 * Added other commands such as adding and removing an item from the block list without editing the config manually
 * Added list command to view blocked items in the list
 */

public class LimitCraftingCommand implements CommandExecutor {
	
	private final LimitCraftingPlugin plugin;
	private String task;
	
	public LimitCraftingCommand(final LimitCraftingPlugin pl) {
		this.plugin = pl;
	}
	
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
	final LimitCraftingUtil perm = new LimitCraftingUtil(plugin);
		if (perm.hasPermission(sender,"limitcrafting.use")){
			if ((args.length >= 1)) {
				if ((args[0].equalsIgnoreCase("reload"))){
					if(args.length == 2){
						if((args[1].equalsIgnoreCase("plugin"))){
							if (perm.hasPermission(sender,"limitcrafting.reload.plugin")){
							
								this.task = new LimitCraftingUtil(plugin).reloadPlugin();
								sender.sendMessage(task);
							}
							else {
								sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
							}
							return true;
						}
					}
					if (perm.hasPermission(sender,"limitcrafting.reload")){
					
						plugin.reloadConfig();
						plugin.load();
						sender.sendMessage(ChatColor.GREEN + "Configuration " + ChatColor.GRAY + "reloaded for " + ChatColor.GREEN +
							plugin.getName() + " " + plugin.getDescription().getVersion());
					}
					else {
					sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
					}
				}
				else if ((args[0].equalsIgnoreCase("block"))||(args[0].equalsIgnoreCase("add"))){
					if (perm.hasPermission(sender,"limitcrafting.block")){
					
						if(args.length == 2){
							String mat = args[1].toUpperCase().trim();
							this.task = new LimitCraftingUtil(plugin).addBlock(mat);
							sender.sendMessage(task);
						}
						else{
							sender.sendMessage(ChatColor.RED + "You don't have an item name!");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
					}
				}
				else if ((args[0].equalsIgnoreCase("unblock"))||(args[0].equalsIgnoreCase("remove"))||(args[0].equalsIgnoreCase("rm"))){
					if (perm.hasPermission(sender,"limitcrafting.unblock")){
					
						if(args.length == 2){
							String mat = args[1].toUpperCase().trim();
							this.task = new LimitCraftingUtil(this.plugin).remBlock(mat);
							sender.sendMessage(task);
						}
						else{
							sender.sendMessage(ChatColor.RED + "You don't have an item name!");
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
					}
				}
				else if ((args[0].equalsIgnoreCase("list"))){
					if(args.length == 2){
						if((args[1].equalsIgnoreCase("perm"))){
							if (perm.hasPermission(sender,"limitcrafting.list.perm")){
								
								new LimitCraftingUtil(plugin).itemPermList();
							}
							else {
								sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
							}
							return true;
						}
					}
					if (perm.hasPermission(sender,"limitcrafting.list")){
					
						new LimitCraftingUtil(plugin,sender).listBlock();
					}
					else{
						sender.sendMessage(ChatColor.RED+"You don't have permission to use this command!");
					}
				}
			}
			else{
				sender.sendMessage(ChatColor.GREEN + "/limitcrafting reload " + ChatColor.GRAY + "- reloads configuration");
				sender.sendMessage(ChatColor.GREEN + "/limitcrafting reload plugin " + ChatColor.GRAY + "- reloads plugin");
				sender.sendMessage(ChatColor.GREEN + "/limitcrafting block " + ChatColor.GRAY + "- blocks crafting recipe");
				sender.sendMessage(ChatColor.GREEN + "/limitcrafting unblock " + ChatColor.GRAY + "- unblocks crafting recipe");
				sender.sendMessage(ChatColor.GREEN + "/limitcrafting list " + ChatColor.GRAY + "- lists blocked recipies");
				sender.sendMessage(ChatColor.GREEN + "/limitcrafting list perm " + ChatColor.GRAY + "- lists permissions for blocked recipies");
			}
		}
		else{
			sender.sendMessage(ChatColor.RED+"You don't have permission to use this command!");
		}
		return true;
		
	}
}
