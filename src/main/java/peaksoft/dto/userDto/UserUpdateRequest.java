package peaksoft.dto.userDto;

import lombok.Builder;

@Builder
public record UserUpdateRequest(
        String firstName,
        String lastName,
        String password,
        String phoneNumber,
        int experience
) {
}
