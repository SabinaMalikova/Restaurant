package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.userDto.UserRequest;
import peaksoft.dto.userDto.UserResponse;
import peaksoft.dto.userDto.UserUpdateRequest;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.ForbiddenException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepo;
import peaksoft.repository.UserRepo;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SimpleResponse requestUserToRestaurant(UserRequest userRequest) {

        int age = Period.between(userRequest.dateOfBirth(), LocalDate.now()).getYears();

        if (userRepo.existsByEmail(userRequest.email())){
            throw new AlreadyExistException("User with email: " + userRequest.email() + " already exists");
        }

        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setRole(userRequest.role());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setPhoneNumber(userRequest.phoneNumber());

        if (userRequest.role().equals(Role.CHEF)){
            if (age >= 25 && age <=45){
                user.setDateOfBirth(userRequest.dateOfBirth());
                if (userRequest.experience() >= 2){
                    user.setExperience(userRequest.experience());
                    userRepo.save(user);
                }else throw new BadCredentialException("Chef must have 2 years or more experience");
            }else throw new BadCredentialException("Chef's age must be between 25 and 45 years old");
        }

        else if (userRequest.role().equals(Role.WAITER)){
            if (age >= 18 && age <=30){
                user.setDateOfBirth(userRequest.dateOfBirth());
                if (userRequest.experience() >= 1){
                    user.setExperience(userRequest.experience());
                    userRepo.save(user);
                }else throw new BadCredentialException("Waiter must have 1 year or more experience");
            }else throw new BadCredentialException("Waiter's age must be between 18 and 30 years old");
        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("user successfully saved")
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepo.getUserById(id)
                .orElseThrow(()->new NotFoundException("user with id: "+id+ " not found"));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepo.getAllUsers();
    }

    @Override
    public List<UserResponse> getAllUsersByRestId(Long id) {
        return userRepo.getAllUsersByRestId(id);
    }

    @Override
    public SimpleResponse updateUser(Long id, UserUpdateRequest newUserUpdateRequest) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.getUserByEmail(email).orElseThrow(() -> new NotFoundException("user with email: " + email + " not found"));

        User updatedUser = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("user with id: " + id + " not found"));

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(updatedUser.getId())){

            updatedUser.setFirstName(newUserUpdateRequest.firstName());
            updatedUser.setLastName(newUserUpdateRequest.lastName());
            updatedUser.setPassword(passwordEncoder.encode(newUserUpdateRequest.password()));
            updatedUser.setPhoneNumber(newUserUpdateRequest.phoneNumber());

            if (updatedUser.getRole().equals(Role.CHEF)){
                if (newUserUpdateRequest.experience() >= 2){
                    updatedUser.setExperience(newUserUpdateRequest.experience());
                    userRepo.save(updatedUser);
                }else throw new BadCredentialException("Chef must have 2 years or more experience");
            }

            else if (updatedUser.getRole().equals(Role.WAITER)) {
                if (updatedUser.getExperience() >= 1){
                    updatedUser.setExperience(newUserUpdateRequest.experience());
                    userRepo.save(updatedUser);
                }else throw new BadCredentialException("Waiter must have 1 year or more experience");
            }
        } else throw new ForbiddenException("forbidden request");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("user successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteUser(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("user with id: " + id + " not found"));

        Restaurant restaurant = user.getRestaurant();
        restaurant.setNumberOfEmployees(restaurant.getNumberOfEmployees()-1);

        restaurant.getUsers().remove(user);
        userRepo.delete(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("user successfully deleted")
                .build();
    }

    @Override
    public SimpleResponse assignUserToRestaurant(Long userId, Long restId, String word) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("user with id: " + userId + " not found"));
        Restaurant restaurant = restaurantRepo.findById(restId)
                .orElseThrow(() -> new NotFoundException("restaurant with id: " + restId + " not found"));

        if (word.equalsIgnoreCase("accept")){
            if (user.getRestaurant() == null){
                if (restaurant.getUsers().size() <= 15){

                    user.setRestaurant(restaurant);
                    restaurant.getUsers().add(user);
                    restaurant.setNumberOfEmployees(restaurant.getNumberOfEmployees()+1);

                    userRepo.save(user);
                    restaurantRepo.save(restaurant);

                }
                else throw new BadCredentialException("no vacancies");
            }
            else throw new AlreadyExistException("user cannot be in 2 restaurants at the same time");
        } else if (word.equalsIgnoreCase("reject")) {
            userRepo.delete(user);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("user successfully deleted")
                    .build();
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("user successfully assigned to restaurant")
                .build();
    }
}
