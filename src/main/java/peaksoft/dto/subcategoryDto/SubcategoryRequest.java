package peaksoft.dto.subcategoryDto;

import lombok.Builder;

@Builder
public record SubcategoryRequest(
        String name
) {
}
