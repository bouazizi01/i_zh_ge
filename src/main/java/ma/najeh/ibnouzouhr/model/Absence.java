package ma.najeh.ibnouzouhr.model;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table
public class Absence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Planing planing;
    private Date createdAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Planing getPlaning() {
        return planing;
    }

    public Absence setPlaning(Planing planing) {
        this.planing = planing;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Absence setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
