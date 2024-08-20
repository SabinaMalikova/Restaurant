package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(generator = "rest_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "rest_gen", sequenceName = "rest_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String location;
    @NotNull
    private String restType;
    @NotNull
    private int numberOfEmployees;
    @NotNull
    private int service;

    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<User> users;

    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<MenuItem> menuItems;
}
