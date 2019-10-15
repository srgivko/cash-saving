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
    @NotNull(message = "Type cannot be empty")
    private Type type;

    @Column
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @Column
    @NotNull(message = "Amount cannot be empty")
    private BigDecimal amount;

    @Column
    @NotNull(message = "Create date cannot be empty")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date createAt = new Date();

    @ManyToOne
    @NotNull(message = "Category data cannot be empty")
    private Category category;

    @Column
    private String imgFilename;

    public enum Type {
        INCOME, OUTCOME
    }
}
