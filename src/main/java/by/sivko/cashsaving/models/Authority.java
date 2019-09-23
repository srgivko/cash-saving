package by.sivko.cashsaving.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "authorities")
@NamedQueries({
        @NamedQuery(name = "Authority.getAuthorityByType", query = "select a from Authority a where a.name=:name")
})
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AuthorityType name;

    public Authority(AuthorityType name) {
        this.name = name;
    }
}
