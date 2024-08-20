package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subcategories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subcategory {
    @Id
    @GeneratedValue(generator = "sub_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sub_gen", sequenceName = "sub_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "subcategory",cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<MenuItem>menuItems;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH,CascadeType.DETACH})
    private Category category;


}
