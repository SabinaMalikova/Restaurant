package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.enums.Role;
import peaksoft.validation.EmailValidation;
import peaksoft.validation.PasswordValidation;
import peaksoft.validation.PhoneNumberValidation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    @EmailValidation
    private String email;
    @NotNull
    @PasswordValidation
    private String password;
    @NotNull
    @PhoneNumberValidation
    private String phoneNumber;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotNull
    private int experience;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Restaurant restaurant;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<Cheque>cheques;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
