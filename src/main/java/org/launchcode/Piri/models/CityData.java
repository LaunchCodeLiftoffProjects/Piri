package org.launchcode.Piri.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CityData {

    public static ArrayList<City> findByValue(String value, Iterable<City> allCities){

        String lower_val = value.toLowerCase();

        ArrayList<City> results = new ArrayList<>();

        for (City city : allCities) {

            if (city.getCityName().toLowerCase().equals(value.toLowerCase())) {
                results.add(city);
            } else if(city.getStateName().toLowerCase().equals(value.toLowerCase())) {
                results.add(city);
            }else if(city.getCounty().toLowerCase().equals(value.toLowerCase())) {
                results.add(city);
            }else if(city.getStateID().toLowerCase().equals(value.toLowerCase())) {
                results.add(city);
            }
            else{
                String str[] = city.getZipCodes().split(" ");
                List<String> al;
                al = Arrays.asList(str);
                for(String s: al){
                    if(s.equals(value)){
                        results.add(city);
                    }
                }
            }


        }
        return results;
    }

}

