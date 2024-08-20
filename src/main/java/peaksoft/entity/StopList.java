package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "stopLists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StopList {
    @Id
    @GeneratedValue(generator = "list_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "list_gen", sequenceName = "list_seq", allocationSize = 1)
    private Long id;
    @NotNull
    private String reason;
    @NotNull
    private LocalDate date;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private MenuItem menuItem;

}
