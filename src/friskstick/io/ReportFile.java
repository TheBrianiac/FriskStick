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

    public ReportFile(FriskStick plugin) {

        this.plugin = plugin;

        reports = new File(plugin.getDataFolder(), "reports.txt");

    }

    public void writeReportToFile(Player reporter, Player reported) {

        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String monthName = (new DateFormatSymbols()).getMonths()[month];
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        String time = String.format("%02d:%02d:%02d", hour, minute, second);
        String date = String.format("%s %d, %d", monthName, day, year);

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(reports, true));

            writer.write(reporter.getName() + " reported " + reported.getName() + " at " + time + " on " + date);
            writer.newLine();

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

    public void deleteReport(int reportNum) {

        try {

            // Create a temporary file
            File tmp = new File(reports.getAbsolutePath() + ".tmp");

            BufferedReader reader = new BufferedReader(new FileReader(reports));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmp, true));

            String report;

            int counter = 1;

            while((report = reader.readLine()) != null) {

                // If the report is not the report being deleted, write it to the temp. file.
                if(!(reportNum == counter)) {

                    writer.write(report + "\n");
                    writer.flush();

                }

                counter++;

            }

            writer.close();
            reader.close();

            // Delete the original reports.txt and rename the temp. file to reports.txt
            reports.delete();
            tmp.renameTo(reports);

        } catch (IOException e) {

            plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Unable to access report file! Disabling FriskStick...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);

        }

    }

}
