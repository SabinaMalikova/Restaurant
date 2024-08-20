package peaksoft.dto.chequeDto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ChequeResponse(
        Long userID,
        String waitersFullName,
//        List<MenuItem> items,
        double priceAverage,
        int service,
        double grandTotal
) implements Serializable {
}
