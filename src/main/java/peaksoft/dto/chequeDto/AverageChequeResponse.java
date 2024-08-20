package peaksoft.dto.chequeDto;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record AverageChequeResponse(
        String restName,
        double averageTotal,
        LocalDate localDate
) {
}
