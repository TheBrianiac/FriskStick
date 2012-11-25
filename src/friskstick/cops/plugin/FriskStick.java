package friskstick.cops.plugin;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import friskstick.cops.commands.FriskCommand;
import friskstick.cops.commands.FriskStickHelpCommand;
import friskstick.cops.commands.ReportCommand;
import friskstick.cops.commands.ShowReportsCommand;
import friskstick.cops.data.AutoUpdate;
import friskstick.cops.data.MetricsLite;
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
		//pm.registerEvents(new Drugs(this), this); // Drug Register
		getCommand("frisk").setExecutor(new FriskCommand(this));
		getCommand("report").setExecutor(new ReportCommand(this));
		getCommand("showreports").setExecutor(new ShowReportsCommand(this));
		getCommand("FriskStick").setExecutor(new FriskStickHelpCommand(this));
		getConfig().options().copyDefaults(true);

		try {

			new AutoUpdate(this);

		} catch (Exception e) {

			e.printStackTrace();

		}

		saveConfig();

	}

	//This method already has a javadoc
	public void onDisable() {

		PluginDescriptionFile pdffile = this.getDescription();
		logger.info(pdffile.getName() + " has been disabled.");

	}

}
