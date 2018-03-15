package ma.najeh.ibnouzouhr.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.CONFIG;

/**
 * Created by youssef on 1/25/18.
 */
@Entity
@Table(name="config_users")
@PrimaryKeyJoinColumn(name="id")
public class UserConfig extends User {

    private String firstName;
    private String lastName;

    public UserConfig() {
    }

    public UserConfig(String username, String password, String firstName, String lastName) {
        super(username, password, CONFIG);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserConfig setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserConfig setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName() {
        return this.firstName+" "+this.lastName;
    }

    @Override
    public String toString() {
        return "UserConfig{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
