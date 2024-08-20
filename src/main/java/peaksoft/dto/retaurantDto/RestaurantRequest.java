package peaksoft.dto.retaurantDto;


import lombok.Builder;

@Builder
public record RestaurantRequest(
        String name,
        String location,
        String restType,
        Integer service
) {
}
