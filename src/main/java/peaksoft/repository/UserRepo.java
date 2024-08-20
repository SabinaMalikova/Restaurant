package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.userDto.UserResponse;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);

    @Query("select new peaksoft.dto.userDto.UserResponse(" +
            "u.id," +
            "u.firstName," +
            "u.lastName," +
            "u.dateOfBirth," +
            "u.email," +
            "u.password," +
            "u.phoneNumber," +
            "u.role," +
            "u.experience) from User u where u.id=:id")
    Optional<UserResponse> getUserById(Long id);

    @Query("select new peaksoft.dto.userDto.UserResponse(" +
            "u.id," +
            "u.firstName," +
            "u.lastName," +
            "u.dateOfBirth," +
            "u.email," +
            "u.password," +
            "u.phoneNumber," +
            "u.role," +
            "u.experience) from User u")
    List<UserResponse> getAllUsers();

    @Query("select new peaksoft.dto.userDto.UserResponse(" +
            "u.id," +
            "u.firstName," +
            "u.lastName," +
            "u.dateOfBirth," +
            "u.email," +
            "u.password," +
            "u.phoneNumber," +
            "u.role," +
            "u.experience) from User u where u.restaurant.id=:id")
    List<UserResponse> getAllUsersByRestId(Long id);


}
