package friskstick.io;

import friskstick.main.FriskStick;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportFile {

    private FriskStick plugin;
    private File reports;

    private Calendar cal = Calendar.getInstance();

    public ReportFile(FriskStick plugin) {

        this.plugin = plugin;

        try {

            reports = new File(plugin.getDataFolder(), "reports.txt");

            if(!reports.exists())
                reports.createNewFile();

        } catch(IOException e) {

            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Unable to create reports file! Disabling FriskStick...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);

        }

    }

    public void writeReportToFile(Player reporter, Player reported) {

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String monthName = (new DateFormatSymbols()).getMonths()[month];
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        String time = String.format("%d:%d:%d", hour, minute, second);
        String date = String.format("%s %d, %d", monthName, day, year);

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(reports, true));

            writer.write(reporter.getName() + " reported " + reported.getName() + " at " + time + " on " + date + "\n");

            writer.close();

        } catch(IOException e) {

            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Unable to access reports file! Disabling FriskStick...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);

        }

    }

    public List<String> readReportsFromFile() {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(reports));

            List<String> reportList = new ArrayList<String>();

            String report;

            while((report = reader.readLine()) != null){

                reportList.add(report);

            }

            reader.close();

            return reportList;

        } catch (IOException e) {

            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Unable to access reports file! Disabling FriskStick...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);

        }

        return null;

    }

    public void deleteReport(String reporterName) {

        try {

            File tmp = new File(reports.getAbsolutePath() + ".tmp");
            tmp.createNewFile();

            BufferedReader reader = new BufferedReader(new FileReader(reports));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmp, true));

            String report;

            while((report = reader.readLine()) != null) {

                if(!report.substring(0, reporterName.length()).equalsIgnoreCase(reporterName)) {

                    writer.write(report + "\n");
                    writer.flush();

                }

            }

            writer.close();
            reader.close();

            reports.delete();
            tmp.renameTo(reports);

        } catch (IOException e) {

            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Unable to access report file! Disabling FriskStick...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);

        }

    }

}
