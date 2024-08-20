package peaksoft.dto.subcategoryDto;


import lombok.Builder;

@Builder
public record SubcategoryResponse(
        String category,
        String name
) {
}
