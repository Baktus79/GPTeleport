package no.vestlandetmc.gpteleport.config;

public class Config extends ConfigHandler {

	private Config(String fileName) {
		super(fileName);
	}

	public static boolean
	COOLDOWN_ENABLE,
	WARMUP_ENABLE;

	public static int
	COOLDOWN_TIME,
	WARMUP_TIME;

	private void onLoad() {

		COOLDOWN_ENABLE = getBoolean("cooldown.enable");
		WARMUP_ENABLE = getBoolean("warmup.enable");
		COOLDOWN_TIME = getInt("cooldown.time");
		WARMUP_TIME = getInt("warmup.time");

	}

	public static void initialize() {
		new Config("config.yml").onLoad();
	}
}
