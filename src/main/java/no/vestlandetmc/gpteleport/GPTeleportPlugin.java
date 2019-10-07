package no.vestlandetmc.gpteleport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import no.vestlandetmc.gpteleport.commands.GPTPListCommand;
import no.vestlandetmc.gpteleport.commands.GPTeleportCommand;
import no.vestlandetmc.gpteleport.handlers.MessageHandler;
import no.vestlandetmc.gpteleport.handlers.UpdateNotification;

public class GPTeleportPlugin extends JavaPlugin{

	private static GPTeleportPlugin instance;

	public static GPTeleportPlugin getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;

		this.getCommand("gpteleportlist").setExecutor(new GPTPListCommand());
		this.getCommand("gpteleport").setExecutor(new GPTeleportCommand());

		if(getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
			MessageHandler.sendConsole("[" + getDescription().getPrefix() + "] &7Successfully hooked into &2GriefPrevention");
			MessageHandler.sendConsole("");
		} else {
			MessageHandler.sendConsole("[" + getDescription().getPrefix() + "] &cGriefPrevention was not found! Please add GriefPrevention.");
			MessageHandler.sendConsole("");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		new UpdateNotification(71853) {

			@Override
			public void onUpdateAvailable() {
				MessageHandler.sendConsole("&c-----------------------");
				MessageHandler.sendConsole("&6[" + getDescription().getPrefix() + "] &7Version " + getLatestVersion() + " is now available!");
				MessageHandler.sendConsole("&6[" + getDescription().getPrefix() + "] &7Download the update at https://www.spigotmc.org/resources/" + getProjectId());
				MessageHandler.sendConsole("&c-----------------------");
			}
		}.runTaskAsynchronously(this);
	}
}
