package friskstick.main;

import friskstick.commands.FriskStickCommand;
import friskstick.commands.ReportCommand;
import friskstick.commands.ShowReportsCommand;
import friskstick.data.MetricsLite;
import friskstick.data.PlayerData;
import friskstick.events.StickRightClickEvent;
import friskstick.io.ReportFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 */
public class FriskStick extends JavaPlugin {

    /**
     * The {@link Logger} for the plugin
     */
    public Logger log = Logger.getLogger("Minecraft");

    // INSTANCES

    public ReportFile reportFileInstance = new ReportFile(this);

    public PlayerData playerDataInstance = new PlayerData();

    public FriskStickCommand friskStickCommandInstance = new FriskStickCommand();
    public ReportCommand reportCommandInstance = new ReportCommand(this);
    public ShowReportsCommand showReportsCommandInstance = new ShowReportsCommand(this);

    public StickRightClickEvent rightClickInstance = new StickRightClickEvent(this);
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

        PluginDescriptionFile pdf = this.getDescription();

        log.info("[" + pdf.getName() + "] v. " + pdf.getVersion() + " is now enabled!");

    }

    @Override
    public void onDisable() {

        PluginDescriptionFile pdf = this.getDescription();

        log.info("[" + pdf.getName() + "] v. " + pdf.getVersion() + " is now enabled!");

    }

    public void addCommands() {

        this.getCommand("friskstick").setExecutor(friskStickCommandInstance);
        //this.getCommand("comply").setExecutor(complyCommandInstance);
        this.getCommand("report").setExecutor(reportCommandInstance);
        this.getCommand("showreports").setExecutor(showReportsCommandInstance);

    }

    public void registerEvents() {

        PluginManager manager = this.getServer().getPluginManager();

        manager.registerEvents(rightClickInstance, this);

    }

}
