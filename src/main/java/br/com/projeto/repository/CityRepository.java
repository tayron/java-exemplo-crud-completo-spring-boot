package br.com.projeto.repository;

import br.com.projeto.model.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CityRepository extends JpaRepository<City, Integer> {
    public List<City> findByFederalUnit(String federalUnit);

    public City findByName(String citySelected);
}
