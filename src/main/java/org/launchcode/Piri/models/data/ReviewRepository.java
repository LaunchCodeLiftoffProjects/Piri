package org.launchcode.Piri.models.data;

import org.launchcode.Piri.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
