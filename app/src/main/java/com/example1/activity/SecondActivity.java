package com.example1.activity;


import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import static com.example1.activity.HuaweiIOT.*;

import android.os.AsyncTask;


public class SecondActivity extends AppCompatActivity {
    private TextView tvTemperature;
    private TextView tvAir;
    private TextView tvSoil;
    private TextView tvLight;

    public static String getProperties() {
        String endpoint = "https://d1dd65c306.st1.iotda-app.cn-north-4.myhuaweicloud.com:443/v5/iot/%s/devices/%s/shadow";
        String projectId = "dd4ba8efe5c94a48b7f00e8ccf1c2a66";
        String deviceId = "661e2d252ccc1a5838818871_0001";
        endpoint = String.format(endpoint, projectId, deviceId);

        try {
            String token = gettoken();
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("X-Auth-Token", token);
            connection.connect();

            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            connection.disconnect();

            String result = response.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            // 返回空字符串以表示获取属性失败
            return "";
        }
    }

    public static int[] returnProperties() {
        String jsonStr;
        jsonStr = getProperties();

        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(jsonStr).getAsJsonObject();

        if (obj.has("shadow")) {
            JsonObject shadow = obj.getAsJsonArray("shadow").get(0).getAsJsonObject();
            JsonObject reported = shadow.getAsJsonObject("reported");
            JsonObject properties = reported.getAsJsonObject("properties");

            int temp = properties.getAsJsonPrimitive("temp").getAsInt();
            int soilHumidity = properties.getAsJsonPrimitive("soil_humidity").getAsInt();
            int airHumidity = properties.getAsJsonPrimitive("air_humidity").getAsInt();
            int light = properties.getAsJsonPrimitive("light").getAsInt();

            return_properties = new int[]{temp, soilHumidity, airHumidity, light};


        } else {
            System.out.println("No shadow data available.");
        }
        return return_properties;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        tvTemperature = findViewById(R.id.tvTemperature);
        tvAir = findViewById(R.id.tvAir);
        tvSoil = findViewById(R.id.tvSoil);
        tvLight = findViewById(R.id.tvLight);


        new FetchPropertiesTask().execute();
    }

    private class FetchPropertiesTask extends AsyncTask<Void, Void, int[]> {
        @Override
        protected int[] doInBackground(Void... voids) {
            return returnProperties();
        }

        @Override
        protected void onPostExecute(int[] properties) {
            super.onPostExecute(properties);


            int temp = properties[0];
            int soilHumidity = properties[1];
            int airHumidity = properties[2];
            int light = properties[3];

            tvTemperature.setText(getString(R.string.temperature_default) + " " + temp + " ℃");
            tvAir.setText(getString(R.string.air_default) + " " + airHumidity + " %");
            tvSoil.setText(getString(R.string.soil_default) + " " + soilHumidity + " %");
            tvLight.setText(getString(R.string.light_default) + " " + light + " lx");
        }
    }
}

