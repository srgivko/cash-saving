package by.sivko.cashsaving.models;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString(exclude = "categories")
@EqualsAndHashCode(callSuper = true, exclude = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "select u from User u where u.username=:username"),
        @NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email=:email")
})
@Slf4j
public class User extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = -5517738947999508011L;

    @Column(unique = true)
    @NotEmpty(message = "Username cannot be empty")
    @Size(max = 50, min = 6, message = "Username 6-50 characters")
    private String username;

    @Transient
    private String verifyPassword;

    @Column
    @NotEmpty
    @Size(min = 5)
    private String password;

    @Column(unique = true)
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is incorrect")
    private String email;

    @Column(name = "account_expired")
    private boolean accountExpired = false;

    @Column(name = "account_locked")
    private boolean accountLocked = false;

    @Column(name = "credentials_expired")
    private boolean credentialsExpired = false;

    @Column
    private boolean enabled;

    private String activationCode;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<Authority> authorities;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Category> categories;

    public void addAuthority(Authority authority) {
        if (authority == null) return;
        if (this.authorities == null) {
            log.warn(String.format("authorities is null for user [%s]", this));
            this.authorities = new HashSet<>();
        }
        log.warn(String.format("authority[%s] add to user [%s]", authority, this));
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        log.warn(String.format("authority[%s] remove from user [%s]", authority, this));
        this.authorities.remove(authority);
    }

    public void addCategory(Category category) {
        if (category == null) return;
        if (this.categories == null) {
            log.warn(String.format("category[%s] add to user [%s]", category, this));
            this.categories = new HashSet<>();
        }
        this.categories.add(category);
        category.setUser(this);
    }

    public void removeCategory(Category category) {
        log.warn(String.format("category[%s] remove from user [%s]", category, this));
        this.categories.remove(category);
        category.setUser(null);
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
