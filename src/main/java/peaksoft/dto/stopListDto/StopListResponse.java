package peaksoft.dto.stopListDto;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record StopListResponse(
        String menuItem,
        String reason,
        LocalDate date

) {
}
