package ma.najeh.ibnouzouhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table(name = "seance")
public class Seance   implements Serializable,Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String scholarYear;
    @Cascade({CascadeType.PERSIST,CascadeType.SAVE_UPDATE})
    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Group group;
    @ManyToOne
    private Teacher teacher;
    @JsonIgnore
    @OneToMany(mappedBy = "seance")
    private List<Planing> planings;


    public Long getId() {
        return id;
    }

    public Seance setId(Long id) {
        this.id = id;
        return this;
    }

    public String getScholarYear() {
        return scholarYear;
    }

    public Seance setScholarYear(String scholarYear) {
        this.scholarYear = scholarYear;
        return this;
    }



    public Salle getSalle() {
        return salle;
    }

    public Seance setSalle(Salle salle) {
        this.salle = salle;
        return this;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Seance setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", scholarYear='" + scholarYear + '\'' +
                ", salle=" + salle +
                ", group=" + group +
                ", teacher=" + teacher +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Group getGroup() {
        return group;
    }

    public Seance setGroup(Group group) {
        this.group = group;
        return this;
    }

    public List<Planing> getPlanings() {
        return planings;
    }

    public Seance setPlanings(List<Planing> planings) {
        this.planings = planings;
        return this;
    }
}
