package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.retaurantDto.RestaurantRequest;
import peaksoft.dto.retaurantDto.RestaurantResponse;
import peaksoft.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@Tag(name = "restaurant-api")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")

public class RestaurantApi {

    private final RestaurantService restaurantService;

    @PostMapping("/save")
    SimpleResponse saveRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest){
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @GetMapping("/get/{id}")
    RestaurantResponse getRestaurantById(@PathVariable Long id){
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/getAll")
    List<RestaurantResponse> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }

    @PutMapping("/update/{id}")
    SimpleResponse updateRestaurant(@PathVariable Long id, @RequestBody @Valid RestaurantRequest newRestaurantRequest){
        return restaurantService.updateRestaurant(id, newRestaurantRequest);
    }

    @DeleteMapping("/delete/{id}")
    SimpleResponse deleteRestaurant(@PathVariable Long id){
        return restaurantService.deleteRestaurant(id);
    }

}
