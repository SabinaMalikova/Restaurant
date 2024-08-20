package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(generator = "cheque_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque_gen", sequenceName = "cheque_seq", allocationSize = 1)
    private Long id;
    private double priceAverage;
    private LocalDate createdAt;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<MenuItem> menuItems;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private User user;

}
