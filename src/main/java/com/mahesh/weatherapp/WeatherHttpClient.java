package com.mahesh.weatherapp;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class WeatherHttpClient {


    String APP_ID = "771ad0611f6fab1538c257d97846d55c";
    String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    String APPEND_UNITS = "&units=";
    String APPEND_APP_ID = "&APPID=";
    String FAHRENHEIT = "imperial";
    String CELSIUS = "metric";

    private String constructOpenWeatherMapURL(String location, String unit) {
        return BASE_URL + location + APPEND_UNITS + unit + APPEND_APP_ID + APP_ID;
    }

    public String getWeatherData(String location, String unit) {
        HttpURLConnection con = null;
        InputStream is = null;
        String line;

        try {
            unit = (unit != null && "C".equals(unit)) ? CELSIUS : FAHRENHEIT;
            location = URLEncoder.encode(location, "UTF-8").replace("+", "%20");
            String weatherApiUrl = constructOpenWeatherMapURL(location, unit);
            con = (HttpURLConnection) (new URL(weatherApiUrl)).openConnection();
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuilder buffer = new StringBuilder();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                buffer.append(line);
                buffer.append("\r\n");
            }
            is.close();
            con.disconnect();

            return buffer.toString();

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                if(is != null)
                  is.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                if(con != null)
                  con.disconnect();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        return null;

    }
}