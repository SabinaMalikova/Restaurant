package peaksoft.dto.menuItemDto;

import lombok.Builder;

@Builder
public record MenuItemRequest(
        String name,
        String image,
        Double price,
        String description,
        boolean isVegetarian
) {
}
