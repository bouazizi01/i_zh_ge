package ma.najeh.ibnouzouhr.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table(name="super_admins")
@PrimaryKeyJoinColumn(name="id")
public class SuperAdmin extends User  implements Serializable {
    public SuperAdmin() {
        this.role="super_admin";
    }

    public SuperAdmin(String username, String password) {
        super(username, password, "super_admin");
    }

    public SuperAdmin(String username, String password,  String fullName) {
        super(username, password, "super_admin");
        this.fullName = fullName;
    }

    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @Override
    public String toString() {
        return "SuperAdmin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
