package me.tezk.limitcrafting;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Tom Micallef on 23/12/2015.
 */
public final class LimitCraftingPlugin extends JavaPlugin {
	
	private boolean enabled;
	private String message;
	private boolean blockAll;
	private List<String> items;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		load();
		
		final PluginCommand plot = getCommand("limitcrafting");
		plot.setExecutor(new LimitCraftingCommand(this));
		plot.setAliases(Arrays.asList("lc","restrict"));
		getServer().getPluginManager().registerEvents(new PlayerCraftListener(this), this);
	}

	public void load() {
		this.enabled = getConfig().getBoolean("enabled");
		this.message = getConfig().getString("message");
		this.blockAll = getConfig().getBoolean("block-all-items");
		this.items = getConfig().getStringList("items");
	}

	public boolean isPlugEnabled() {
		return this.enabled;
	}

	public boolean isBlockAll() {
		return blockAll;
	}

	public List<String> getItems() {
		return this.items;
	}

	public String getMessage() {
		return this.message;
	}
}