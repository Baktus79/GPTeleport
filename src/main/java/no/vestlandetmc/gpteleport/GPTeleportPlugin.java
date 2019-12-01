package no.vestlandetmc.gpteleport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import no.vestlandetmc.gpteleport.commands.GPTPListCommand;
import no.vestlandetmc.gpteleport.commands.GPTeleportCommand;
import no.vestlandetmc.gpteleport.commands.SetClaimName;
import no.vestlandetmc.gpteleport.commands.SetClaimSpawn;
import no.vestlandetmc.gpteleport.config.Config;
import no.vestlandetmc.gpteleport.config.Messages;
import no.vestlandetmc.gpteleport.handlers.MessageHandler;
import no.vestlandetmc.gpteleport.handlers.UpdateNotification;
import no.vestlandetmc.gpteleport.listener.PlayerListener;
import no.vestlandetmc.gpteleport.runnable.Schedule;
import no.vestlandetmc.gpteleport.storage.DataStorage;

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
		this.getCommand("setclaimspawn").setExecutor(new SetClaimSpawn());
		this.getCommand("setclaimname").setExecutor(new SetClaimName());
		this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		new DataStorage();
		Messages.initialize();
		Config.initialize();
		new Schedule().runTaskTimer(this, 0L, (3600 * 20L));

		if(getServer().getPluginManager().getPlugin("GriefPrevention") != null) {
			MessageHandler.sendConsole("[" + getDescription().getPrefix() + "] &7Successfully hooked into GriefPrevention");
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

	@Override
	public void onDisable() {
		final DataStorage data = DataStorage.getConfig();
		data.save();
	}
}
