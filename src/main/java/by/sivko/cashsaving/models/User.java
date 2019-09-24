package by.sivko.cashsaving.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "select u from User u where u.username=:username"),
        @NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email=:email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @Length(min = 6, message = "*Name must have at least 6 characters")
    private String username;

    @Column(nullable = false)
//    ^                 # start-of-string
//    (?=.*[0-9])       # a digit must occur at least once
//    (?=.*[a-z])       # a lower case letter must occur at least once
//    (?=.*[A-Z])       # an upper case letter must occur at least once
//    (?=.*[@#$%^&+=])  # a special character must occur at least once
//    (?=\S+$)          # no whitespace allowed in the entire string
//    .{8,}             # anything, at least eight places though
//    $                 # end-of-string
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            targetEntity = Category.class, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Category> categoryList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<Authority> authorities = new HashSet<>();

    public void addCategory(Category category) {
        this.categoryList.add(category);
        category.setUser(this);
    }

    public void removeCategory(Category category) {
        category.setUser(null);
        this.categoryList.remove(category);
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }
}
