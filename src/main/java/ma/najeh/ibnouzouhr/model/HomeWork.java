package ma.najeh.ibnouzouhr.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table
public class HomeWork  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String title;
    private String pathHomeWork;
    private Date createdAt;
    @ManyToOne
    private Seance seance;
    @ManyToOne
    private Planing planing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathHomeWork() {
        return pathHomeWork;
    }

    public void setPathHomeWork(String pathHomeWork) {
        this.pathHomeWork = pathHomeWork;
    }

    public Planing getPlaning() {
        return planing;
    }

    public HomeWork setPlaning(Planing planing) {
        this.planing = planing;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Seance getSeance() {
        return seance;
    }

    public HomeWork setSeance(Seance seance) {
        this.seance = seance;
        return this;
    }
}
