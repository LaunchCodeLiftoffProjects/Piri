package org.launchcode.Piri.models.data;

import org.launchcode.Piri.models.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
public interface PagingAndSortingCityRepository extends PagingAndSortingRepository<City, Integer> {


}
