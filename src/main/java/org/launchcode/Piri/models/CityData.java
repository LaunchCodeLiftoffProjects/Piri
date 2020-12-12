package org.launchcode.Piri.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CityData {

//    public static void httpRequest() {
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://raw.githubusercontent.com/millbj92/US-Zip-Codes-JSON/master/USCities.json")).build();
//        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenApply(CityData::parse)
//                .join();
//
//
//    }
//
//
//    public static String parse(String responseBody) throws JSONException {
//
//            JSONArray cities = new JSONArray(responseBody);
//            for (int i = 0; i < cities.length(); i++) {
//                JSONObject city = cities.getJSONObject(i);
//                int zipcode = city.getInt("zip_code");
//                int latitude = city.getInt("latitude");
//                int longitude = city.getInt("longitude");
//                String cityName = city.getString("city");
//                String state = city.getString("state");
//                String county = city.getString("county");
//
//            }
//            return null;
//    }
}
