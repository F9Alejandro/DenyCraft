package me.tezk.limitcrafting;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Tom Micallef on 23/12/2015.
 */
public class PlayerCraftListener implements Listener {
	
	private final LimitCraftingPlugin plugin;
	public PlayerCraftListener(final LimitCraftingPlugin pl) {
		this.plugin = pl;
	}
	
	@SuppressWarnings("deprecation") 
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerCraft(final CraftItemEvent e) {
		final boolean enabled = plugin.isPlugEnabled();
		final String message = plugin.getMessage();
		final boolean blockAll = plugin.isBlockAll();
		final List<String> items = plugin.getItems();
		final LimitCraftingUtil perm = new LimitCraftingUtil(plugin);
		Player player = (Player) e.getWhoClicked();
		Material MAT = null;
		int ID;
		
		if (!(enabled)) return;
		//System.out.println("Before Permissions test"); //debug code
		if (perm.hasPermission(player,"limitcrafting.bypass")) {
			//debug code
			//e.getWhoClicked().sendMessage(ChatColor.BLUE+"You Created: "+e.getRecipe().getResult()+" and you can bypass");
			return;
		}
		//System.out.println("Before Blockall test"); //debug code
		if (blockAll) {
			e.setCancelled(true);
			final ItemStack is = e.getCurrentItem();
			//Player player = (Player) e.getWhoClicked();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%item%", is.getData().getItemType().name().toLowerCase()));
			return;
		}
		//System.out.println("Before Try Catch"); //debug code
		try{
			for (String s : items) {
				final String mat = s.split(":")[0];
				final ItemStack is;
				try{
					ID = Integer.parseInt(mat);
					MAT = Material.getMaterial(ID);
				}
				catch(NumberFormatException z){
					MAT = Material.matchMaterial(mat);
				}
				if (MAT != null){
					String val = MAT.toString();
					if (!(s.contains(":"))) {
						is = new ItemStack(Material.valueOf(val));
						//debug code
						//e.getWhoClicked().sendMessage(ChatColor.BLUE+"Testing: Material doesn't have metadata");
					} else {
						final byte type = Byte.valueOf(s.split(":")[1]);
						is = new ItemStack(Material.valueOf(val), 0, (short)0, type);
						//debug code
						//e.getWhoClicked().sendMessage(ChatColor.BLUE+"Testing: Material has metadata");
					}
					if (perm.hasPermission(player,"limicrafting."+is.getData().getItemType().name().toLowerCase())){
						//player is allowed to build this specified item
						break;
					}
					if (e.getRecipe().getResult().equals(is)) {
						e.setCancelled(true);
						//Player player = (Player) e.getWhoClicked();
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%item%", is.getData().getItemType().name().toLowerCase()));
						break;
					}
				}
				//System.out.println("End of For loop"); //debug code
			}
			//System.out.println("End of Try"); //debug code
		}
		catch (NullPointerException x){
			//Player player = (Player) e.getWhoClicked();
			player.sendMessage(ChatColor.RED+"Unable to find items in item list!");
		}
		//System.out.println("Skipped Catch"); //debug code
		//debug code
		//e.getWhoClicked().sendMessage(ChatColor.BLUE+"You Created: "+e.getResult());
		return;
	}
}
