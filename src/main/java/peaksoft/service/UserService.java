package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.userDto.UserRequest;
import peaksoft.dto.userDto.UserResponse;
import peaksoft.dto.userDto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    SimpleResponse requestUserToRestaurant(UserRequest userRequest);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    List<UserResponse> getAllUsersByRestId(Long id);
    SimpleResponse updateUser(Long id, UserUpdateRequest newUserUpdateRequest);
    SimpleResponse deleteUser(Long id);
    SimpleResponse assignUserToRestaurant(Long userId, Long restId, String word);
}
