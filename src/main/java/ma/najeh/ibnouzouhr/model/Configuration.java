package ma.najeh.ibnouzouhr.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by youssef on 1/25/18.
 */
@Entity
@Table(name = "configs")
public class Configuration implements Serializable {
    @Id
    private String name;
    private String val;

    public Configuration() {
    }

    public Configuration(String name, String val) {
        this.name = name;
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public Configuration setName(String name) {
        this.name = name;
        return this;
    }

    public String getVal() {
        return val;
    }

    public Configuration setVal(String val) {
        this.val = val;
        return this;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}
