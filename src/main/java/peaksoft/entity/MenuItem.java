package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.validation.PriceValidation;

import java.util.List;

@Entity
@Table(name = "menuItems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(generator = "menu_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "menu_gen", sequenceName = "menu_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String image;
    @NotNull
    @PriceValidation
    private Double price;
    @NotNull
    private String description;
    @NotNull
    private boolean isVegetarian;

    @ManyToMany(mappedBy = "menuItems", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Cheque> cheques;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Restaurant restaurant;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Subcategory subcategory;

    @OneToOne(mappedBy = "menuItem", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private StopList stopList;
}
