package org.launchcode.Piri.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CityData {

    public static ArrayList<City> findByValue(String value, Iterable<City> allCities){

        String lower_val = value.toLowerCase();

        ArrayList<City> results = new ArrayList<>();

        for (City city : allCities) {

            if(lower_val == ""){
                results.add(city);
            }
            if (city.getCityName().toLowerCase().equals(lower_val)) {
                results.add(city);
            } else if(city.getStateName().toLowerCase().equals(lower_val)) {
                results.add(city);
            }else if(city.getCounty().toLowerCase().equals(lower_val)) {
                results.add(city);
            }else if(city.getStateID().toLowerCase().equals(lower_val)) {
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

