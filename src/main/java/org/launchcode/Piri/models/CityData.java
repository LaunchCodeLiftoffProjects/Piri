package org.launchcode.Piri.models;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import java.util.ArrayList;
import java.util.List;


public class CityData {

    private static final String DATA_FILE = "uscities.csv";
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
            Resource resource = new ClassPathResource(DATA_FILE);
            InputStream is = resource.getInputStream();
            Reader reader = new InputStreamReader(is);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allCities = new ArrayList<>();

            //Put records into a more friendly format
            for (CSVRecord record : records) {

                String aCity = record.get(0);
                String aStateID = record.get(2);
                String aStateName = record.get(3);
                String aCountyName = record.get(5);
                Double aLatitude = Double.parseDouble(record.get(6));
                Double aLongitude = Double.parseDouble(record.get(7));
                String[] zipCodesStrs = record.get(15).split(" ");
                int[] zipCodes = new int[zipCodesStrs.length];
                for(int i =0; i < zipCodes.length; i++){
                    zipCodes[i] = Integer.parseInt(zipCodesStrs[i]);
                }


                City newCity = new City(aCity, aStateName, aStateID, zipCodes, aCountyName, aLatitude, aLongitude);

                allCities.add(newCity);
            }
            isDataLoaded = true;
        }catch (IOException e){
            System.out.println("Failed to load city data");
            e.printStackTrace();
        }catch (NumberFormatException e){

        }
    }


}





