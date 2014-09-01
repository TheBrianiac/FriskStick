package friskstick.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateCheck {

    private URL url;

    private String fileName;

    public UpdateCheck() {

        query();

    }

    public String getName() {

        return fileName;

    }

    private void query() {

        try {

            url = new URL("https://api.curseforge.com/servermods/files?projectIDs=42526");

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        try {

            URLConnection conn = url.openConnection();

            conn.addRequestProperty("User-Agent", "FriskStick");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = reader.readLine();

            JSONArray array = (JSONArray) JSONValue.parse(response);

            if (array.size() > 0) {

                JSONObject latest = (JSONObject) array.get(array.size() - 1);

                fileName = (String) latest.get("name");

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
