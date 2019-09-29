package by.sivko.cashsaving.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "user")
@Entity
@Table(name="user_profile")
public class UserProfile extends BaseEntity {

    private static final long serialVersionUID = 2433269327690182846L;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Column
    private String address;

    @Column
    private String address2;

    @Column
    private String city;

    @Column
    @Size(min = 2)
    private String state;

    @Column
    @Size(min = 5, max = 10)
    private String zip;

    @Column
    private String phone;
}

