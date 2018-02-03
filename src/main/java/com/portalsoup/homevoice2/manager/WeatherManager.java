package com.portalsoup.homevoice2.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherManager {

    public static String API_KEY = "b46c8a1475eda4ff19319e504c57a4d9";

    public static void main(String[] args) throws IOException {
        WeatherManager weatherManager = new WeatherManager();

        System.out.println(weatherManager.getTodayForecast());
    }

    private String sendGET(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        return "error";
    }

    public String getTodayForecast() throws IOException {
        String formattedURL = String.format("https://api.openweathermap.org/data/2.5/weather?q=Eugene&APPID=%s", API_KEY);

        String response = sendGET(formattedURL);

        return response;
    }

}
