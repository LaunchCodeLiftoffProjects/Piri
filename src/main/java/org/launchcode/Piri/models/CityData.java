package org.launchcode.Piri.models;

import java.util.ArrayList;



public class CityData {

    public static ArrayList<City> findByValue(String value, Iterable<City> allCities){

        String lower_val = value.toLowerCase();

        ArrayList<City> results = new ArrayList<>();

        for(City city : allCities){
            if(city.getCityName().toLowerCase().contains(lower_val)){
                results.add(city);
            }else if(city.getStateName().contains(lower_val)){
                results.add(city);
            }
        }
        return results;
    }

}





