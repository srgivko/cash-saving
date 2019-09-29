package by.sivko.cashsaving.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "authorities")
@NamedQueries({
        @NamedQuery(name = "Authority.getAuthorityByType", query = "select a from Authority a where a.type=:type")
})
public class Authority extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = -4834236478291600291L;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AuthorityType type;

    public Authority(AuthorityType type) {
        this.type = type;
    }

    @Override
    public String getAuthority() {
        return this.type.name();
    }
}
