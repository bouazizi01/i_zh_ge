package ma.najeh.ibnouzouhr.model;

import javax.persistence.*;
import java.io.Serializable;

import static ma.najeh.ibnouzouhr.constant.Constant.ROLE.SCOLARITY;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table(name="admins")
@PrimaryKeyJoinColumn(name="id")
public class Admin extends User implements Serializable {
    public Admin() {
    }

    public Admin(String username, String password) {
        super(username, password, SCOLARITY );
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
