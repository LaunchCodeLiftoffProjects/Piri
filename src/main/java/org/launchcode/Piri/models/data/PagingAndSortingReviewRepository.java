package org.launchcode.Piri.models.data;

import org.launchcode.Piri.models.Review;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagingAndSortingReviewRepository extends PagingAndSortingRepository<Review, Integer> {
}
