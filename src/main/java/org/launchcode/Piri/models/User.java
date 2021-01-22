package org.launchcode.Piri.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    //    @NotNull
    private String username;

    //    @NotNull
    private String pwHash;

    @OneToMany (mappedBy = "user")
    //@JoinColumn (name="city_id")
    private final List<Review> reviews = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.pwHash = encoder.encode(password);
    }

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean isMatchingPassword(String password) {

        return encoder.matches(password, pwHash);
    }

    public String getUsername() {
        return username;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}

