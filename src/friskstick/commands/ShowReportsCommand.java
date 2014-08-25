package friskstick.commands;

import friskstick.main.FriskStick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ShowReportsCommand implements CommandExecutor {

    FriskStick plugin;

    public ShowReportsCommand(FriskStick plugin) {

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player && (sender.isOp() || sender.hasPermission("friskstick.report.show"))) {

            List<String> reports = plugin.reportFileInstance.readReportsFromFile();

            for(String report : reports) {

                sender.sendMessage(report);

            }

        }

        return false;

    }

}
