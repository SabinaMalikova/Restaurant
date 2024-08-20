package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.retaurantDto.RestaurantResponse;
import peaksoft.entity.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

    Optional<RestaurantResponse> getRestaurantById(Long id);

    @Query("select new peaksoft.dto.retaurantDto.RestaurantResponse(" +
            "r.name," +
            "r.location," +
            "r.restType," +
            "r.numberOfEmployees," +
            "r.service) from Restaurant r")
    List<RestaurantResponse> getAllRestaurants();


}
