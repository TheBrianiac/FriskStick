package friskstick.main;

import friskstick.commands.DeleteReportCommand;
import friskstick.commands.FriskStickCommand;
import friskstick.commands.ReportCommand;
import friskstick.commands.ShowReportsCommand;
import friskstick.data.UpdateCheck;
import friskstick.data.MetricsLite;
import friskstick.data.PlayerData;
import friskstick.events.DamageEvent;
import friskstick.events.StickRightClickEvent;
import friskstick.io.ReportFile;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 */
public class FriskStick extends JavaPlugin {

    /**
     * The {@link Logger} for the plugin
     */
    public Logger log = this.getLogger();

    // INSTANCES (Frisk class instance intentionally not included)

    public ReportFile reportFileInstance = new ReportFile(this);

    public PlayerData playerDataInstance = new PlayerData(this);

    public FriskStickCommand friskStickCommandInstance = new FriskStickCommand(this);
    public ReportCommand reportCommandInstance = new ReportCommand(this);
    public ShowReportsCommand showReportsCommandInstance = new ShowReportsCommand(this);
    public DeleteReportCommand deleteReportCommandInstance = new DeleteReportCommand(this);

    public StickRightClickEvent rightClickInstance = new StickRightClickEvent(this);
    public DamageEvent damageEventInstance = new DamageEvent(this);
    ////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onEnable() {

        try {

            MetricsLite metrics = new MetricsLite(this);
            metrics.start();

        } catch(IOException e) {

            e.printStackTrace();

        }

        addCommands();
        registerEvents();

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        startUpdateTimer();

        PluginDescriptionFile pdf = getDescription();

        log.info(pdf.getName() + " v. " + pdf.getVersion() + " is now enabled!");

    }

    @Override
    public void onDisable() {

        PluginDescriptionFile pdf = getDescription();

        log.info(pdf.getName() + " v. " + pdf.getVersion() + " is now disabled.");

    }

    public void addCommands() {

        getCommand("friskstick").setExecutor(friskStickCommandInstance);
        getCommand("report").setExecutor(reportCommandInstance);
        getCommand("showreports").setExecutor(showReportsCommandInstance);
        getCommand("deletereport").setExecutor(deleteReportCommandInstance);

    }

    public void registerEvents() {

        PluginManager manager = getServer().getPluginManager();

        manager.registerEvents(rightClickInstance, this);
        manager.registerEvents(damageEventInstance, this);

    }

    public void checkForUpdates() {

        PluginDescriptionFile pdf = getDescription();

        UpdateCheck updater = new UpdateCheck();

        if(!updater.getName().equalsIgnoreCase(pdf.getName() + " v" + pdf.getVersion())) {

            for (Player p : getServer().getOnlinePlayers()) {

                if(p.isOp() || p.hasPermission("friskstick.update.receive"))
                    p.sendMessage(getConfig().getString("plugin-update-msg").replaceAll("&", "§").replaceAll("%link%", "http://dev.bukkit.org/bukkit-plugins/friskstick"));

            }

            log.info("Plugin is out of date! Check the BukkitDev page for the latest version.");

        }

    }

    private void startUpdateTimer() {

        if(this.getConfig().getBoolean("enable-update-checking")) {

            new BukkitRunnable() {

                @Override
                public void run() {

                    checkForUpdates();

                }

            }.runTaskTimer(this, 0, getConfig().getInt("update-check-interval") * 60 * 20);

        }

    }

}
