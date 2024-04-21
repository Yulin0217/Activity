package com.example1.activity;

import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;


public class HuaweiIOT {
    public static int[] return_properties = new int[4];
    @Nullable
    public static String gettoken() throws Exception {
        String strurl = "";
        strurl = "https://iam.cn-north-4.myhuaweicloud.com" + "/v3/auth/tokens?nocatalog=false";
        String tokenstr = "{" + "\"" + "auth" + "\"" + ": {" + "\"" + "identity" + "\"" + ": {" + "\"" + "methods" + "\"" + ": [" + "\"" + "password" + "\"" + "]," + "\"" + "password" + "\"" + ": {" + "\"" + "user" + "\"" + ":{" + "\"" + "domain\": {\"name\": \"hid_s_zkeq_uo125o-v\"},\"name\": \"apple\",\"password\": \"zzy63886451\"}}},\"scope\": {\"project\": {\"name\": \"cn-north-4\"}}}}";
        URL url = new URL(strurl);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.addRequestProperty("Content-Type", "application/json;charset=utf8");
        urlCon.setDoOutput(true);
        urlCon.setRequestMethod("POST");
        urlCon.setUseCaches(false);
        urlCon.setInstanceFollowRedirects(true);
        urlCon.connect();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlCon.getOutputStream(), "UTF-8"));
        writer.write(tokenstr);
        writer.flush();
        writer.close();
        Map headers = urlCon.getHeaderFields();
        Set<String> keys = headers.keySet();
        /*for( String key : keys ){
            String val = urlCon.getHeaderField(key);
            System.out.println(key+"    "+val);
        }*/
        String token = urlCon.getHeaderField("X-Subject-Token");
        System.out.println("X-Subject-Token" + " : " + token);
        return token;
    }

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


        /*ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode=objectMapper.readTree(jsonResponse);
        JsonNode tempNode=jsonNode.get("shadow").get(0).get("reported").get("properties");
        String temp = tempNode.get("temp").asText()+"℃";
        String air_humidity = tempNode.get("air_humidity").asText()+"%";
        String soil_humidity = tempNode.get("soil_humidity").asText()+"%";
        String light = tempNode.get("light").asText()+"lx";

        return new String[]{temp,air_humidity,soil_humidity,light};*/


        /*if(mode=="shadow")
        {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(result, JsonNode.class);
            JsonNode tempNode = jsonNode.get("shadow").get(0).get("reported").get("properties").get(att);
            String attvaluestr = tempNode.asText();
            System.out.println(att+"=" + attvaluestr);
            return attvaluestr;
        }
        if(mode=="status")
        {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(result, JsonNode.class);
            JsonNode statusNode = jsonNode.get("status");
            String statusstr = statusNode.asText();
            System.out.println("status = " + statusstr);
            return statusstr;
        }
        return "error ";*/
    /*}*/
    /*public String setCom(String com,String value) throws Exception{
        String strurl="";
        strurl="https://d1dd65c306.st1.iotda-app.cn-north-4.myhuaweicloud.com:443/v5/iot/dd4ba8efe5c94a48b7f00e8ccf1c2a66/devices/661e2d252ccc1a5838818871_0001/commands";
        strurl = String.format(strurl, project_id,device_id);
        URL url = new URL(strurl);
        HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();
        urlCon.addRequestProperty("Content-Type", "application/json");
        urlCon.addRequestProperty("X-Auth-Token",token);
        urlCon.setRequestMethod("POST");
        urlCon.setDoOutput(true);
        urlCon.setUseCaches(false);
        urlCon.setInstanceFollowRedirects(true);
        urlCon.connect();

        String body = "{\"paras\":{\""+com+"\""+":"+value+"},\"service_id\":\"BasicData\",\"command_name\":\"Control\"}";

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlCon.getOutputStream(),"UTF-8"));
        writer.write(body);
        writer.flush();
        writer.close();
        InputStreamReader is = new InputStreamReader(urlCon.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(is);
        StringBuffer strBuffer = new StringBuffer();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            strBuffer.append(line);
        }
        is.close();
        urlCon.disconnect();
        String result = strBuffer.toString();
        System.out.println(result);
        return result;
    }*/

    public static int[] returnProperties(){
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
}
