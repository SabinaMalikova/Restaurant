package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "category_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_gen", sequenceName = "category_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE, CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Subcategory> subcategories;
}
