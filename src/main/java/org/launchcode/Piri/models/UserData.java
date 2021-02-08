package org.launchcode.Piri.models;

import org.launchcode.Piri.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.toIntExact;

@Transactional
@Service
public class UserData {

    @Autowired
    static
    UserRepository userRepository;

    public static void uploadProfilePicture(MultipartFile file, User user){
        try{
            if (file != null) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                if (fileName.contains("..")) {
                    System.out.println("not a a valid file");
                }
                String byteTostring = Base64.getEncoder().encodeToString(file.getBytes());
                user.setProfilePicture(byteTostring);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveCityToFavoritesList(City city, User user){
        ArrayList<City> savedCities = new ArrayList<>();
        savedCities.addAll(user.getSavedCities());
        if(!savedCities.contains(city)){
            savedCities.add(city);
        }
        user.setSavedCities(savedCities);

    }

    @Transactional
    public static void unSaveCityFromFavoritesList(City city, User user){
        ArrayList<City> savedCities = new ArrayList<>();
        savedCities.addAll(user.getSavedCities());

        if(savedCities.contains(city)) {
            savedCities.remove(city);
        }

        user.setSavedCities(savedCities);
        city.setUser(null);
    }

    public Page<City> findPaginatedSavedCities(User user, int pageNo, int cityCount, String searchTerm){
        String lower_val = null;
        if(searchTerm == null){
            searchTerm = "";
        }else{
            lower_val = searchTerm.toLowerCase();
        }
        ArrayList<City> results = new ArrayList<>();
        ArrayList<City> savedCities = new ArrayList<>();
        savedCities.addAll(user.getSavedCities());
        for(City city : savedCities){
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
        Pageable pageable = PageRequest.of(pageNo - 1, cityCount);
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
    }
}
