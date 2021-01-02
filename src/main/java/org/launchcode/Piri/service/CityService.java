package org.launchcode.Piri.service;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.CityData;
import org.launchcode.Piri.models.data.PagingAndSortingCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.StrictMath.toIntExact;

@Service
public class CityService {

    @Autowired
    private PagingAndSortingCityRepository pagingAndSortingCityRepository;

    public List<City> getAllCities(){
        List<City> cities = (List<City>) pagingAndSortingCityRepository.findAll();
        return cities;
    }

    public Page<City> findByValue(int pageNo, int cityCount, String value){

        if(value == null){
            value = "";
        }else{
            String lower_val = value.toLowerCase();
        }

        Iterable<City> cities = this.pagingAndSortingCityRepository.findAll();
        ArrayList<City> results = new ArrayList<>();


        for (City city : cities) {

//            if(value == "" ){
//                results.add(city);
//            }
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
        Pageable pageable = PageRequest.of(pageNo - 1, cityCount);
//        List<City> cities1 = new ArrayList<>();
//        Page<City> newCities = new PageImpl<>(results, pageable, results.size());
        int total = results.size();
        int start = toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), total);

        List<City> output = new ArrayList<>();

        if (start <= end) {
            output = results.subList(start, end);
        }

        return new PageImpl<>(
                output,
                pageable,
                total
        );

//        return new PageImpl<>(results, pageable, results.size());
//        return newCities;
//        return results;
    }

    public Page<City> findPaginated(int pageNo, int cityCount) {

        Pageable pageable = PageRequest.of(pageNo - 1, cityCount);
//        List<City> cities = CityData.findByValue(searchTerm, this.pagingAndSortingCityRepository.findAll(pageable).getContent());
//        Page<City> newPAge = new PageImpl<>(cities);
//        return newPAge;
//        Page<City> cityPage = new PageImpl<>(cities);
        return this.pagingAndSortingCityRepository.findAll(pageable);
    }
}
