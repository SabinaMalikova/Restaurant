package peaksoft.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.userDto.UserResponse;
import peaksoft.dto.userDto.UserUpdateRequest;
import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "user-api")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @GetMapping("/get/{id}")
    @PermitAll
    UserResponse getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/getAll")
    @PermitAll
    List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getAllUsersByRestId/{id}")
    @PermitAll
    List<UserResponse> getAllUsersByRestId(@PathVariable Long id){
        return userService.getAllUsersByRestId(id);
    }

    @PutMapping("/update/{id}")
    @PermitAll
    SimpleResponse updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest newUserUpdateRequest){
        return userService.updateUser(id, newUserUpdateRequest);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @PostMapping("/assignUserToRestaurant/{userId}/{restId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse assignUserToRestaurant(@PathVariable Long userId, @PathVariable Long restId, @RequestParam String word){
        return userService.assignUserToRestaurant(userId, restId, word);
    }

}
