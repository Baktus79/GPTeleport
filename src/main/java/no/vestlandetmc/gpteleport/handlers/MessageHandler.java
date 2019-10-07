package no.vestlandetmc.gpteleport.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import no.vestlandetmc.gpteleport.GPTeleportPlugin;

public class MessageHandler {

	public static void sendAction(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(colorize(message)));
	}

	public static void sendTitle(Player player, String title, String subtitle) {
		player.sendTitle(colorize(title), colorize(subtitle), 20, 3 * 20, 10);
	}

	public static void sendTitle(Player player, String title, String subtitle, int stay) {
		player.sendTitle(colorize(title), colorize(subtitle), 20, (stay * 20), 10);
	}

	public static void sendMessage(Player player, String... messages) {
		for(final String message : messages) {
			player.sendMessage(colorize(message));
		}
	}

	public static void sendAnnounce(String... messages) {
		for(final Player player : Bukkit.getOnlinePlayers()) {
			for(final String message : messages) {
				player.sendMessage(colorize(message));
			}
		}
	}

	public static void sendConsole(String... messages) {
		for(final String message : messages) {
			GPTeleportPlugin.getInstance().getServer().getConsoleSender().sendMessage(colorize(message));
		}
	}

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
