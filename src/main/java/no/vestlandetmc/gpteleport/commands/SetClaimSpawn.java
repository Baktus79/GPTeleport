package no.vestlandetmc.gpteleport.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import no.vestlandetmc.gpteleport.handlers.MessageHandler;
import no.vestlandetmc.gpteleport.handlers.StorageHandler;

public class SetClaimSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			MessageHandler.sendConsole("&cThis command can only be used in-game.");
			return true;
		}

		final Player player = (Player) sender;
		final Location loc = player.getLocation();
		final StorageHandler storage = new StorageHandler();
		final Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, true, null);

		if(claim == null) {
			MessageHandler.sendMessage(player, "&cPlease stand inside your claim to set a new spawn location.");
			return true;
		} else {
			if(claim.getOwnerName().equals(player.getName())) {
				MessageHandler.sendMessage(player, "&cYou are not the owner of this claim.");

				return true;
			}

			final int locX = (int) loc.getX();
			final int locY = (int) loc.getY();
			final int locZ = (int) loc.getZ();
			final String world = loc.getWorld().getName();

			storage.setLocation(claim.getID().toString(), loc);

			MessageHandler.sendMessage(player, "&eNew spawn location has been set for claimid &6" + claim.getID().toString() + " &eat the coordinates &6X:" + locX + " Y:" + locY + " Z:" + locZ + " &ein world &6" + world + "&e.");

			return true;
		}
	}
}
