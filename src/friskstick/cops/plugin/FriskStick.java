package friskstick.cops.plugin;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import friskstick.cops.commands.FriskCommand;
import friskstick.cops.commands.FriskStickHelpCommand;
import friskstick.cops.commands.ReportCommand;
import friskstick.cops.commands.ShowReportsCommand;
import friskstick.cops.data.MetricsLite;
import friskstick.cops.data.UpdateCheck;
//import friskstick.cops.drugs.Drugs;
//import friskstick.cops.data.PluginUpdateCheck;
import friskstick.cops.stick.Stick;

/**
 * Main class for the plugin. This is the class that should be referenced when using methods relating to the {@link JavaPlugin} class.
 */
public class FriskStick extends JavaPlugin {

	/**
	 * The {@link Logger} object
	 */
	public final Logger logger = Logger.getLogger("Minecraft");

	//This method already has a javadoc
	public void onEnable() {

		/*
		 * Metrics
		 */
		try {

			MetricsLite metrics = new MetricsLite(this);
			metrics.start();

		} catch (IOException e) {

		}

		PluginDescriptionFile pdffile = this.getDescription();
		logger.info(pdffile.getName() + " v" + pdffile.getVersion() + " has been enabled!");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Stick(this), this);
		pm.registerEvents(new UpdateCheck(this), this);
		//pm.registerEvents(new Drugs(this), this); // Drug Register
		getCommand("frisk").setExecutor(new FriskCommand(this));
		getCommand("report").setExecutor(new ReportCommand(this));
		getCommand("showreports").setExecutor(new ShowReportsCommand(this));
		getCommand("FriskStick").setExecutor(new FriskStickHelpCommand(this));
		getConfig().options().copyDefaults(true);

		try {

			updateCheck();

		} catch (IOException e) {

			e.printStackTrace();
		}

		saveConfig();

	}

	//This method already has a javadoc
	public void onDisable() {

		PluginDescriptionFile pdffile = this.getDescription();
		logger.info(pdffile.getName() + " has been disabled.");

	}



	/**
	 * Check For Updates
	 */
	public void updateCheck() throws IOException {

		String currentVersion = "1.4";
		String latestVersion;

		URL u = new URL("https://dl.dropbox.com/u/73820799/latestVersion.txt");
		Scanner stream = new Scanner(u.openStream());
		latestVersion = stream.nextLine();
		PluginDescriptionFile pdfFile = this.getDescription();

		logger.info("[FriskStick] Checking for updates!");

		if(!latestVersion.equalsIgnoreCase(currentVersion)) {

			logger.info(pdfFile.getName() + " Needs To be Updated to version " + latestVersion + "!");
			logger.info("Get it here: http://dev.bukkit.org/server-mods/friskstick/ to download");
			UpdateCheck.updateNeeded = true;

		} else {

			logger.info("[FriskStick] Plugin is up to date!");
			UpdateCheck.updateNeeded = false;

		}

		stream.close();

	}

}
