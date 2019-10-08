package no.vestlandetmc.gpteleport.commands;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import no.vestlandetmc.gpteleport.handlers.MessageHandler;
import no.vestlandetmc.gpteleport.handlers.StorageHandler;

public class GPTeleportCommand implements CommandExecutor {

	private int claimId = 0;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			MessageHandler.sendConsole("&cThis command can only be used in-game.");
			return true;
		}

		final Player player = (Player) sender;
		final UUID uuid = player.getUniqueId();
		Claim claim = null;
		final StorageHandler storage = new StorageHandler();

		if(args.length != 0) {
			if(isInt(args[0])) {
				this.claimId = Integer.parseInt(args[0]);
			} else {
				MessageHandler.sendMessage(player, "&c" + args[0] + " is not a valid number.");
				return true;
			}
		} else {
			MessageHandler.sendMessage(player, "&cYou must enter the claimID for teleport.");
			return true;
		}

		for(int i = 0; i < GriefPrevention.instance.dataStore.getPlayerData(uuid).getClaims().toArray().length; i++) {
			final Claim claims = (Claim) GriefPrevention.instance.dataStore.getPlayerData(uuid).getClaims().toArray()[i];

			if(claims.getID() == this.claimId) { claim = claims; }
		}

		if(claim == null) {
			MessageHandler.sendMessage(player, "&cThis claim does not exist.");
			return true;
		} else if(claim.getOwnerName().equals(player.getName())) {
			MessageHandler.sendMessage(player, "&cYou are not the owner of this claim.");

			return true;
		}

		final Location locMin = claim.getLesserBoundaryCorner();
		final Location locMax = claim.getGreaterBoundaryCorner();

		final double locX = (locMax.getX() + locMin.getX()) / 2;
		final double locZ = locMax.getZ();
		final double locY = locMax.getWorld().getHighestBlockAt((int) locX, (int) locZ).getY();
		final World world = locMax.getWorld();

		if(storage.storedLocation(claim.getID().toString())) {
			final Location loc = storage.getLocation(claim.getID().toString());
			player.teleport(loc);
		} else {
			final Location loc = new Location(world, locX, locY, locZ);
			player.teleport(loc);
		}

		MessageHandler.sendMessage(player, "&eYou have successfully teleported to your claim with the id &6" + this.claimId);

		return true;
	}

	private boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

}
