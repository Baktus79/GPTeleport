package no.vestlandetmc.gpteleport.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import no.vestlandetmc.gpteleport.config.Messages;
import no.vestlandetmc.gpteleport.handlers.Cooldown;
import no.vestlandetmc.gpteleport.handlers.MessageHandler;
import no.vestlandetmc.gpteleport.handlers.Warmup;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
			final Warmup warmup = new Warmup();
			final Cooldown cooldown = new Cooldown();
			if (warmup.isWarmup(e.getPlayer())) {
				warmup.moved(e.getPlayer());
				cooldown.remove(e.getPlayer());
				MessageHandler.sendTitle(e.getPlayer(), Messages.WARMUP_CANCELLED, "", 0, 2, 1);
			}
		}
	}

}
