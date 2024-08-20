package peaksoft.dto.categoryDto;

import lombok.Builder;

@Builder
public record CategoryRequest(
        String name
) {
}
