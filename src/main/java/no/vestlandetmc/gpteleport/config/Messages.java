package no.vestlandetmc.gpteleport.config;

public class Messages extends ConfigHandler {

	private Messages(String fileName) {
		super(fileName);
	}

	public static String
	TELEPORT_TP,
	LIST_HEADER,
	LIST_NOCLAIM,
	LIST_CLAIMID,
	LIST_NAME,
	LIST_COORDS,
	LIST_CLAIMID_COLOR,
	LIST_NAME_COLOR,
	LIST_COORDS_COLOR,
	SETNAME_NOARGS,
	SETNAME_NONAME,
	SETNAME_SETNAME,
	SETSPAWN_OUTSIDE,
	SETSPAWN_NOOWNER,
	SETSPAWN_SETSPAWN,
	COOLDOWN_COOLDOWN,
	WARMUP_TITLE,
	WARMUP_SUBTITLE,
	WARMUP_CANCELLED,
	UNVALID_NUMBER,
	UNVALID_CLAIMID;

	private void onLoad() {

		TELEPORT_TP = getString("teleport.teleport");
		LIST_HEADER = getString("list.header");
		LIST_NOCLAIM = getString("list.no-claims");
		LIST_CLAIMID = getString("list.claimid");
		LIST_NAME = getString("list.claim-name");
		LIST_COORDS = getString("list.coordinate");
		LIST_CLAIMID_COLOR = getString("list.claimid-color");
		LIST_NAME_COLOR = getString("list.claim-name-color");
		LIST_COORDS_COLOR = getString("list.coordinate-color");
		SETNAME_NOARGS = getString("setname.no-arguments");
		SETNAME_NONAME = getString("setname.no-name");
		SETNAME_SETNAME = getString("setname.set-name");
		SETSPAWN_OUTSIDE = getString("setspawn.outside-claim");
		SETSPAWN_NOOWNER = getString("setspawn.no-owner");
		SETSPAWN_SETSPAWN = getString("setspawn.set-spawn");
		COOLDOWN_COOLDOWN = getString("cooldown.cooldown");
		WARMUP_TITLE = getString("warmup.title");
		WARMUP_SUBTITLE = getString("warmup.subtitle");
		WARMUP_CANCELLED = getString("warmup.cancelled");
		UNVALID_NUMBER = getString("miscellaneous.unvalid-number");
		UNVALID_CLAIMID = getString("miscellaneous.unvalid-claimid");

	}

	public static void initialize() {
		new Messages("messages.yml").onLoad();
	}
}
