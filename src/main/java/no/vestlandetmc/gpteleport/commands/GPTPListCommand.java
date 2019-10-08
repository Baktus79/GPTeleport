package no.vestlandetmc.gpteleport.commands;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import no.vestlandetmc.gpteleport.handlers.MessageHandler;
import no.vestlandetmc.gpteleport.handlers.StorageHandler;

public class GPTPListCommand implements CommandExecutor {

	int countTo = 5;
	int countFrom = 0;
	int number = 1;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			MessageHandler.sendConsole("&cThis command can only be used in-game.");
			return true;
		}

		final Player player = (Player) sender;
		final UUID uuid = player.getUniqueId();
		final int maxClaims = GriefPrevention.instance.dataStore.getPlayerData(uuid).getClaims().size();
		final StorageHandler storage = new StorageHandler();

		if(args.length != 0) {
			if(isInt(args[0])) {
				this.number = Integer.parseInt(args[0]);
				this.countTo = 5 * number;
				this.countFrom = (5 * number) - 5;
			} else {
				MessageHandler.sendMessage(player, "&c" + args[0] + " is not a valid number.");
				return true;
			}
		}

		MessageHandler.sendMessage(player, "&e-- === [ &6Claimslist for " + player.getDisplayName() +  " &e] === --");

		if(maxClaims == 0) {
			MessageHandler.sendMessage(player, "&cYou do not have any claims yet.");
		}

		final int totalPage = (maxClaims / 5) + 1;

		for(int i = 0; i < GriefPrevention.instance.dataStore.getPlayerData(uuid).getClaims().toArray().length; i++) {
			if(this.number > totalPage || this.number == 0) {
				this.countTo = 5 * totalPage;
				this.countFrom = (5 * totalPage) - 5;
				this.number = totalPage;
			}

			if(i >= this.countFrom) {
				final Claim claims = (Claim) GriefPrevention.instance.dataStore.getPlayerData(uuid).getClaims().toArray()[i];

				final Location locMin = claims.getLesserBoundaryCorner();
				final Location locMax = claims.getGreaterBoundaryCorner();
				final int locX = (int) ((locMin.getX()+locMax.getX()) / 2);
				final int locZ = (int) ((locMin.getZ()+locMax.getZ()) / 2);

				if(storage.stored(claims.getID().toString())) {
					final String name = storage.getName(claims.getID().toString());
					MessageHandler.sendMessage(player, "&eClaimID: &6" + claims.getID() + " &eName: &6" + name + " &eCoordinates: &6X:" + locX + " Z:" + locZ);
				} else {
					MessageHandler.sendMessage(player, "&eClaimID: &6" + claims.getID() + " &eName: &6" + "None" + " &eCoordinates: &6X:" + locX + " Z:" + locZ);
				}


				if(i == this.countTo - 1) {
					MessageHandler.sendMessage(player, "");
					MessageHandler.sendMessage(player, "&e<--- [&6" + this.number + "\\" + totalPage + "&e] --->");
					break;
				}

				continue;
			}
		}

		if(this.number == totalPage) {
			MessageHandler.sendMessage(player, "");
			MessageHandler.sendMessage(player, "&e<--- [&6" + totalPage + "\\" + totalPage + "&e] --->");
		}

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
