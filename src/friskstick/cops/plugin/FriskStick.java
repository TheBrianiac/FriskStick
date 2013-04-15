package friskstick.cops.plugin;

/*TODO
 * Add a general FriskStick command(in progress)
 * Add a show reports command(in progress)
 * Fix Frisk Command Bug/Auto-Jailing Bug
 * Implement Beatdown Mode
 * Implement Frisk allowing(with beatdown)
 * Think of more things to do :)
 */

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import friskstick.cops.commands.FriskCommand;
import friskstick.cops.commands.FriskStickHelpCommand;
import friskstick.cops.commands.ReportCommand;
import friskstick.cops.commands.ShowReportsCommand;
import friskstick.cops.data.MetricsLite;
import friskstick.cops.eventListeners.PlayerJoinEventUpdateCheck;
import friskstick.cops.eventListeners.StickRightClickEvent;

/**
 * Main class for the plugin. This is the class that should be referenced when using methods relating to the {@link JavaPlugin} class.
 */
public class FriskStick extends JavaPlugin {

	/**
	 * The {@link Logger} object
	 */
	public final Logger logger = Logger.getLogger("Minecraft");

	/**
	 * Already has a javadoc(Bukkit)
	 */
	public void onEnable() {

		/*
		 * Metrics
		 */
		try {

			MetricsLite metrics = new MetricsLite(this);
			metrics.start();

		} catch (IOException e) {

			e.printStackTrace();

		}

		PluginDescriptionFile pdffile = this.getDescription();
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new StickRightClickEvent(this), this);
		pm.registerEvents(new PlayerJoinEventUpdateCheck(this), this);

		getCommand("frisk").setExecutor(new FriskCommand(this));
		getCommand("report").setExecutor(new ReportCommand(this));
		getCommand("showreports").setExecutor(new ShowReportsCommand(this));
		getCommand("FriskStick").setExecutor(new FriskStickHelpCommand(this));

		logger.info(pdffile.getName() + " v" + pdffile.getVersion() + " has been enabled!");

		getConfig().options().copyDefaults(true);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			public void run() {

				try {

					updateCheck();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		}, 0, this.getConfig().getInt("check-time") * 60 * 20);

		saveConfig();

	}

	/**
	 * Already has a javadoc(Bukkit)
	 */
	public void onDisable() {

		PluginDescriptionFile pdffile = this.getDescription();
		logger.info(pdffile.getName() + " has been disabled.");

	}

	/**
	 * Check For Updates
	 */
	public void updateCheck() throws IOException {

		PluginDescriptionFile pdfFile = this.getDescription();

		String currentVersion = pdfFile.getVersion();
		String latestVersion;

		URL u = new URL("https://dl.dropbox.com/u/73820799/latestVersion.txt");
		Scanner stream = new Scanner(u.openStream());
		latestVersion = stream.nextLine();

		logger.info("[FriskStick] Checking for updates!");

		if(!latestVersion.equalsIgnoreCase(currentVersion)) {

			logger.info("[" + pdfFile.getName() + "]" + " Needs To be Updated to version " + latestVersion + "!");
			logger.info("Get it here: http://dev.bukkit.org/server-mods/friskstick/ to download");
			PlayerJoinEventUpdateCheck.updateNeeded = true;

		} else {

			logger.info("[FriskStick] Plugin is up to date!");
			PlayerJoinEventUpdateCheck.updateNeeded = false;

		}

		stream.close();

	}

}
