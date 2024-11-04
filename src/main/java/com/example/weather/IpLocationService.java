package com.example.weather;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpLocationService implements LocationService {

    private final IpService ipService;

    public IpLocationService(IpService ipService) {
        this.ipService = ipService;
    }

    @Override
    public double[] getCoordinates() {
        try {
            // Получение публичного IP-адреса через IpService
            String publicIp = ipService.getPublicIp();
            if (publicIp == null) {
                System.err.println("Не удалось получить публичный IP-адрес.");
                return null;
            }

            URL url = new URL("http://ip-api.com/json/" + publicIp);
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

            if (!jsonResponse.getString("status").equals("success")) {
                System.err.println("Не удалось определить координаты по IP.");
                return null;
            }

            double latitude = jsonResponse.getDouble("lat");
            double longitude = jsonResponse.getDouble("lon");

            String currentCoordinatesMessage = "Координаты для текущего IP " + publicIp +
                    ": latitude=" + latitude + ", longitude=" + longitude;
            System.out.println(currentCoordinatesMessage);

            return new double[]{latitude, longitude};

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
