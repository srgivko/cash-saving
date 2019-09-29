package by.sivko.cashsaving.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8557478348905650819L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public boolean isNew() {
        return (this.id == null);
    }
}
