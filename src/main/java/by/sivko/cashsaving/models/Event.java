package by.sivko.cashsaving.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "category")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    private static final long serialVersionUID = -6470119171778441523L;

    @Enumerated(EnumType.STRING)
    @Column
    @NotNull
    private Type type;

    @Column
    @NotEmpty
    private String name;

    @Column
    @NotEmpty
    private String description;

    @Column
    @NotNull
    private BigDecimal amount;

    @Column
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date createAt = new Date();

    @ManyToOne
    private Category category;

    public enum Type {
        INCOME, OUTCOME
    }
}
