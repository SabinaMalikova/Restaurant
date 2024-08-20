package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.retaurantDto.RestaurantRequest;
import peaksoft.dto.retaurantDto.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    RestaurantResponse getRestaurantById(Long id);
    List<RestaurantResponse> getAllRestaurants();
    SimpleResponse updateRestaurant(Long id, RestaurantRequest newRestaurantRequest);
    SimpleResponse deleteRestaurant(Long id);

}
