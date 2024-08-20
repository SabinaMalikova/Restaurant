package peaksoft.dto.retaurantDto;

import lombok.Builder;

@Builder
public record RestaurantResponse(
        String name,
        String location,
        String restType,
        int numberOfEmployees,
        int service
) {
}
