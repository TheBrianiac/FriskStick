package friskstick.main;

import friskstick.commands.DeleteReportCommand;
import friskstick.commands.FriskStickCommand;
import friskstick.commands.ReportCommand;
import friskstick.commands.ShowReportsCommand;
import friskstick.data.AutoUpdate;
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

        startUpdateTimer();

        PluginDescriptionFile pdf = this.getDescription();

        log.info(pdf.getName() + " v. " + pdf.getVersion() + " is now enabled!");

    }

    @Override
    public void onDisable() {

        PluginDescriptionFile pdf = this.getDescription();

        log.info(pdf.getName() + " v. " + pdf.getVersion() + " is now disabled.");

    }

    public void addCommands() {

        this.getCommand("friskstick").setExecutor(friskStickCommandInstance);
        this.getCommand("report").setExecutor(reportCommandInstance);
        this.getCommand("showreports").setExecutor(showReportsCommandInstance);
        this.getCommand("deletereport").setExecutor(deleteReportCommandInstance);

    }

    public void registerEvents() {

        PluginManager manager = this.getServer().getPluginManager();

        manager.registerEvents(rightClickInstance, this);
        manager.registerEvents(damageEventInstance, this);

    }

    public void checkForUpdates() {

        PluginDescriptionFile pdf = this.getDescription();

            AutoUpdate updater = new AutoUpdate();

            if(!updater.getName().equalsIgnoreCase(pdf.getName() + " v" + pdf.getVersion())) {

                for (Player p : this.getServer().getOnlinePlayers()) {

                    if(p.isOp() || p.hasPermission("friskstick.update.receive"))
                        p.sendMessage(this.getConfig().getString("plugin-update-msg").replaceAll("&", "§").replaceAll("%link%", "http://dev.bukkit.org/bukkit-plugins/friskstick"));

                }

                this.log.info("Plugin is out of date! Check the BukkitDev page for the latest version.");

            }

    }

    private void startUpdateTimer() {

        if(this.getConfig().getBoolean("enable-update-checking")) {

            new BukkitRunnable() {

                @Override
                public void run() {

                    checkForUpdates();

                }

            }.runTaskTimer(this, 0, this.getConfig().getInt("update-check-interval") * 60 * 20);

        }

    }

}
