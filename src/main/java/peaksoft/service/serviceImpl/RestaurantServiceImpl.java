package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.retaurantDto.RestaurantRequest;
import peaksoft.dto.retaurantDto.RestaurantResponse;
import peaksoft.entity.Restaurant;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepo;
import peaksoft.service.RestaurantService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepo restaurantRepo;

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setNumberOfEmployees(0);
        restaurant.setService(restaurantRequest.service());

        restaurantRepo.save(restaurant);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant successfully created")
                .build();
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        return restaurantRepo.getRestaurantById(id)
                .orElseThrow(()->new NotFoundException("restaurant with id: "+id+" not found"));
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepo.getAllRestaurants();
    }

    @Override
    public SimpleResponse updateRestaurant(Long id, RestaurantRequest newRestaurantRequest) {

        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("restaurant with id: " + id +" not found"));

        restaurant.setName(newRestaurantRequest.name());
        restaurant.setLocation(newRestaurantRequest.location());
        restaurant.setRestType(newRestaurantRequest.restType());
        restaurant.setService(newRestaurantRequest.service());
        restaurantRepo.save(restaurant);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteRestaurant(Long id) {

        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("restaurant with id: " + id + " not found"));

        restaurantRepo.delete(restaurant);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant successfully deleted")
                .build();
    }
}
