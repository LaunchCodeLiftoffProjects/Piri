package org.launchcode.Piri.models.data;

import org.launchcode.Piri.models.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    Optional<Review> findById(Integer integer);
}
