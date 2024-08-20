package peaksoft.dto.menuItemDto;

import lombok.Builder;

@Builder
public record MenuItemResponse(
        String name,
        String image,
        double price,
        String description,
        boolean isVegetarian
) {
}
