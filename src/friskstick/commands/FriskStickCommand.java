package friskstick.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FriskStickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(label.equalsIgnoreCase("friskstick") || label.equalsIgnoreCase("fs")) {

            // If the sender is a player and has full access to the help menu OR the command was sent from the console...
            if((sender instanceof Player && (sender.isOp() || sender.hasPermission("friskstick.help.advanced"))) || sender instanceof ConsoleCommandSender) {

                sender.sendMessage(ChatColor.GOLD + "----------FriskStick----------");
                sender.sendMessage(ChatColor.DARK_AQUA + "by " + ChatColor.YELLOW + "domingocool" + ChatColor.DARK_AQUA + " and " + ChatColor.YELLOW + "dchaosknight");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.AQUA + "/friskstick - " + ChatColor.YELLOW + "Display the help menu");
                sender.sendMessage(ChatColor.AQUA + "/report - " + ChatColor.YELLOW + "Report a player for having drugs");
                sender.sendMessage(ChatColor.AQUA + "/showreports - " + ChatColor.YELLOW + "Show the reports that have been submitted");

            // Otherwise, if the sender is a player and has limited access to the help menu...
            } else if (sender instanceof Player && sender.hasPermission("friskstick.help.basic")) {

                sender.sendMessage(ChatColor.GOLD + "----------FriskStick----------");
                sender.sendMessage(ChatColor.DARK_AQUA + "by " + ChatColor.YELLOW + "domingocool" + ChatColor.DARK_AQUA + " and " + ChatColor.YELLOW + "dchaosknight");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.AQUA + "/friskstick - " + ChatColor.YELLOW + "Display the help menu");
                sender.sendMessage(ChatColor.AQUA + "/report - " + ChatColor.YELLOW + "Report a player for having drugs");

            }

        }

        return true;

    }

}
