package by.sivko.cashsaving.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"user", "eventList"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@NamedQueries({
        @NamedQuery(name = "Category.getAllCategoriesByUserId", query = "select c from Category c where c.user.id = :id"),
        @NamedQuery(name = "Category.getAllCategoriesByUserName", query = "select c from Category c where c.user.username = :username")
})
public class Category extends BaseEntity {


    private static final long serialVersionUID = -1088780004560221054L;

    @Column
    @NotEmpty
    private String name;

    @Column
    @NotEmpty
    private String description;

    @Column
    @NotNull
    private BigDecimal capacity;

    @Column
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt = new Date();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, targetEntity = Event.class,
            fetch = FetchType.EAGER)
    private List<Event> eventList = new ArrayList<>();

    public void addEvent(Event event) {
        this.eventList.add(event);
        event.setCategory(this);
    }

    public void removeEvent(Event event) {
        this.eventList.remove(event);
        event.setCategory(null);
    }

    public BigDecimal calculateCashEvents() {
        Optional<BigDecimal> sum = this.eventList.stream()
                .map(event -> event.getType() == Event.Type.INCOME ? event.getAmount() : event.getAmount().negate())
                .reduce(BigDecimal::add);
        return sum.orElse(BigDecimal.ZERO);
    }
}
