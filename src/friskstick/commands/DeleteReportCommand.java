package friskstick.commands;

import friskstick.main.FriskStick;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeleteReportCommand implements CommandExecutor {

    private FriskStick plugin;

    public DeleteReportCommand(FriskStick plugin) {

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("friskstick.report.delete") && args.length == 1) {

            try {

                plugin.reportFileInstance.deleteReport(Integer.parseInt(args[0]));

                sender.sendMessage(plugin.getConfig().getString("report-delete-msg").replaceAll("&", "§"));

            } catch(NumberFormatException e) {

                sender.sendMessage(ChatColor.RED + "Please ensure the number entered is an integer.");

            }

        } else if(args.length != 0)
            sender.sendMessage(ChatColor.GOLD + "Usage: /deletereport <reportnumber>");

        else if(!sender.hasPermission("friskstick.report.delete"))
            sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");

        return true;

    }

}
