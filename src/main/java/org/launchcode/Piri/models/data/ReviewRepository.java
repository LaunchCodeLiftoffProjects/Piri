package org.launchcode.Piri.models.data;

import org.launchcode.Piri.models.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    
}
