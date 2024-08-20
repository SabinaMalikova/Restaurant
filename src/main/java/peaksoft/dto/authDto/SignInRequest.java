package peaksoft.dto.authDto;

import lombok.Builder;

@Builder
public record SignInRequest(
        String email,
        String password
) {
}
