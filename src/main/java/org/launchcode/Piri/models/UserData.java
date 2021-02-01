package org.launchcode.Piri.models;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class UserData {

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
}
