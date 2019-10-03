package by.sivko.cashsaving.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true, exclude = {"user", "categoryStat"})
@ToString(exclude = {"user", "eventList", "categoryStat"})
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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date createAt = new Date();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, targetEntity = Event.class,
            fetch = FetchType.EAGER)
    private List<Event> eventList = new ArrayList<>();

    @Transient
    private CategoryStat categoryStat;

    public void addEvent(Event event) {
        this.eventList.add(event);
        event.setCategory(this);
    }

    public void removeEvent(Event event) {
        this.eventList.remove(event);
        event.setCategory(null);
    }

    public CategoryStat getCategoryStat() {
        if (this.categoryStat == null) {
            CategoryStat categoryStat = new CategoryStat();
            this.eventList.forEach(event -> {
                    if (event.getType() == Event.Type.INCOME) {
                    categoryStat.addToIncome(event.getAmount());
                } else {
                    categoryStat.addToOutcome(event.getAmount());
                }
            });
            this.categoryStat = categoryStat;
        }
        return this.categoryStat;
    }

}
