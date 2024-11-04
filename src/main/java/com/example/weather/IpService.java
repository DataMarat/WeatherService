package com.example.weather;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpService {

    /**
     * Получает публичный IP-адрес пользователя.
     *
     * @return строка с публичным IP-адресом или null, если запрос не удался
     */
    public String getPublicIp() {
        try {
            URL url = new URL("https://api.ipify.org?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getString("ip");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
