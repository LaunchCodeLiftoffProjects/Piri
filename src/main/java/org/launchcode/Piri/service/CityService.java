package org.launchcode.Piri.service;

import org.launchcode.Piri.models.City;
import org.launchcode.Piri.models.Review;
import org.launchcode.Piri.models.ReviewData;
import org.launchcode.Piri.models.data.PagingAndSortingCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public Page<City> findPaginatedByValue(int pageNo, int cityCount, String searchTerm, String sortField, String sortDirection, Integer starRating){

        String lower_val = null;

        if(searchTerm == null){
            searchTerm = "";
        }else{
            lower_val = searchTerm.toLowerCase();
        }

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Iterable<City> cities = this.pagingAndSortingCityRepository.findAll(sort);
        ArrayList<City> results = new ArrayList<>();
        ArrayList<City> sortedResultsOfCities = new ArrayList<>();


        for (City city : cities) {

            if(searchTerm == "" ){
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
                    if(s.equals(searchTerm)){
                        results.add(city);
                    }
                }
            }
        }


        if(starRating == 0){
            sortedResultsOfCities.addAll(results);
        }else if(starRating != null) {

            for(int i = 0; i < results.size(); i++){
                City city = results.get(i);
                if(starRating == 4 && city.getOverallCityRating() >= 4){
                    sortedResultsOfCities.add(city);
                }else if(starRating == 3 && city.getOverallCityRating() >= 3){
                    sortedResultsOfCities.add(city);
                }else if(starRating == 2 && city.getOverallCityRating() >= 2){
                    sortedResultsOfCities.add(city);
                }else if(starRating == 1 && city.getOverallCityRating() >= 1){
                    sortedResultsOfCities.add(city);
                }
            }
        }


        Pageable pageable = PageRequest.of(pageNo - 1, cityCount);

        int total = sortedResultsOfCities.size();
        int start = toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), total);

        List<City> output = new ArrayList<>();

        if (start <= end) {
            output = sortedResultsOfCities.subList(start, end);
        }

        return new PageImpl<>(
                output,
                pageable,
                total
        );

    }


}