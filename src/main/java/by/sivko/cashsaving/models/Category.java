package by.sivko.cashsaving.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@NamedQueries({
        @NamedQuery(name = "Category.getAllCategoriesByUserId", query = "select c from Category c where c.user.id = :userId")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Length(min = 3, message = "*Name must have at least 3 characters")
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal capacity;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt = new Date();

    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, targetEntity = Event.class,
            fetch = FetchType.LAZY)
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
