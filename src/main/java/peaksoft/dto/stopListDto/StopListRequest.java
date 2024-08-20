package peaksoft.dto.stopListDto;

import lombok.Builder;

@Builder
public record StopListRequest(
        String reason
) {
}
