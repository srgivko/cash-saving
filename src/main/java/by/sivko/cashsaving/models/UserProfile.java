package by.sivko.cashsaving.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @Column
    private String address2;

    @Column
    @NotEmpty(message = "City cannot be empty")
    private String city;

    @Column
    @NotEmpty(message = "State cannot be empty")
    @Size(min = 2, message = "State must be more than 2 characters")
    private String state;

    @Column
    @NotEmpty(message = "Zip cannot be empty")
    @Size(min = 5, max = 10, message = "Zip must be between 5 to 10")
    private String zip;

    @Column
    @Pattern(regexp = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", message = "Phone examples [1234567890, 123-456-7890, (123)456-7890, (123)4567890]")
    private String phone;
}

