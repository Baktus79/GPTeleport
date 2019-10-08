package no.vestlandetmc.gpteleport.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import no.vestlandetmc.gpteleport.handlers.StorageHandler;
import no.vestlandetmc.gpteleport.storage.DataStorage;

public class Schedule extends BukkitRunnable {

	@Override
	public void run() {
		final DataStorage data = DataStorage.getConfig();
		final StorageHandler storage = new StorageHandler();

		data.save();

		if(data.contains("claim-data")) {
			if(!data.getConfigurationSection("claim-data").getKeys(false).isEmpty()) {
				for(final String claimId : data.getConfigurationSection("claim-data").getKeys(false)) {
					if(GriefPrevention.instance.dataStore.getClaim(Long.parseLong(claimId)) == null) {
						storage.delete(claimId);
					}
				}
			}
		}
	}
}
