package org.launchcode.Piri.models;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class CityData {

    private static String json;
    private static boolean isDataLoaded = false;
    private static ArrayList<City> allCities;

    public static ArrayList<City> findAll(){

        loadData();

        return new ArrayList<>(allCities);

    }


    public static ArrayList<City> findByValue(String value) {

        loadData();

        ArrayList<City> cities = new ArrayList<>();

        for (City city : allCities) {

            if (city.getCityName().toLowerCase().contains(value.toLowerCase())) {
                cities.add(city);
            } else if(city.getStateName().toLowerCase().contains(value.toLowerCase())) {
                cities.add(city);
            }else if(city.getCounty().toLowerCase().contains(value.toLowerCase())){
                cities.add(city);
            }else if (String.valueOf(city.getZipCode()).contains(value.toLowerCase())) {
                cities.add(city);
            }

        }

        return cities;
    }


    public static void loadData() {
        if (isDataLoaded) {
            return;
        }
        try {
            URL url = new URL("https://raw.githubusercontent.com/millbj92/US-Zip-Codes-JSON/master/USCities.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream); // input stream to string

            allCities = new ArrayList<>();
            JSONArray cities = new JSONArray(json);
            for (int i = 0; i < cities.length(); i++) {
                JSONObject city = cities.getJSONObject(i);
                int aZipcode = city.getInt("zip_code");
                Double aLatitude = city.getDouble("latitude");
                Double aLongitude = city.getDouble("longitude");
                String aCityName = city.getString("city");
                String aState = city.getString("state");
                String aCounty = city.getString("county");
              City newCity = new City(aCityName,aState,aZipcode,aCounty,aLatitude,aLongitude);

              allCities.add(newCity);
            }
            isDataLoaded = true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }


}





