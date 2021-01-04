package org.launchcode.Piri.models.data;

import org.launchcode.Piri.models.City;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;


@Transactional
public interface PagingAndSortingCityRepository extends PagingAndSortingRepository<City, Integer> {


}
