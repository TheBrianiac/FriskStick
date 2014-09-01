package friskstick.commands;

import friskstick.main.FriskStick;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ShowReportsCommand implements CommandExecutor {

    FriskStick plugin;

    public ShowReportsCommand(FriskStick plugin) {

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if ((sender instanceof Player && (sender.isOp() || sender.hasPermission("friskstick.report.show"))) || sender instanceof ConsoleCommandSender) {

            List<String> reports = plugin.reportFileInstance.readReportsFromFile();

            if (reports.size() == 0) {

                sender.sendMessage(ChatColor.RED + "There are no reports available.");

            } else {

                int count = 1;

                for (String report : reports) {

                    sender.sendMessage(ChatColor.GOLD + Integer.toString(count) + ". " + report);

                    count++;

                }

            }

        } else if (!sender.hasPermission("friskstick.report.show")) {

            sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");

        }

        return true;

    }

}
