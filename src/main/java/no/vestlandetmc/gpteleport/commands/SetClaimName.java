package no.vestlandetmc.gpteleport.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import no.vestlandetmc.gpteleport.handlers.MessageHandler;
import no.vestlandetmc.gpteleport.handlers.StorageHandler;

public class SetClaimName implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			MessageHandler.sendConsole("&cThis command can only be used in-game.");
			return true;
		}

		final Player player = (Player) sender;
		final UUID uuid = player.getUniqueId();
		final StorageHandler storage = new StorageHandler();

		if(args.length == 0) {
			MessageHandler.sendMessage(player, "&cYou must define claimID and a name.");
			return true;
		}

		if(!isLong(args[0])) {
			MessageHandler.sendMessage(player, "&cYou must enter a valid number.");
			return true;
		}

		for(final Claim claims : GriefPrevention.instance.dataStore.getPlayerData(uuid).getClaims()) {
			if(claims.getID().toString().equals(args[0])) {
				if(args.length < 2) {
					MessageHandler.sendMessage(player, "&cYou must enter a name for your claim.");
					return true;
				} else {
					if(claims.getOwnerName().equals(player.getName())) {
						MessageHandler.sendMessage(player, "&cYou are not the owner of this claim.");

						return true;
					}

					storage.setName(claims.getID().toString(), args[1]);
					MessageHandler.sendMessage(player, "&eName for claimid &6" + claims.getID().toString() + " &ehas been set to &6" + args[1]);
					return true;
				}
			}
		}

		return false;
	}

	private boolean isLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

}
