package org.launchcode.Piri.models.data;

import org.launchcode.Piri.models.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CityRepository extends CrudRepository<City, Integer> {


}
