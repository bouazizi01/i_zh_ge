package ma.najeh.ibnouzouhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ma.najeh.ibnouzouhr.config.CustomDateDeserializer;
import ma.najeh.ibnouzouhr.config.CustomDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by youssef on 1/27/18.
 */
@Entity
public class ExamPresent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Module module;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Salle salle;
    @ManyToOne
    private Teacher observer;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public ExamPresent setId(Long id) {
        this.id = id;
        return this;
    }
    public Module getModule() {
        return module;
    }

    public ExamPresent setModule(Module module) {
        this.module = module;
        return this;
    }
    public Student getStudent() {
        return student;
    }

    public ExamPresent setStudent(Student student) {
        this.student = student;
        return this;
    }
    public Salle getSalle() {
        return salle;
    }

    public ExamPresent setSalle(Salle salle) {
        this.salle = salle;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ExamPresent setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    public Teacher getObserver() {
        return observer;
    }

    public ExamPresent setObserver(Teacher observer) {
        this.observer = observer;
        return this;
    }

    @Override
    public String toString() {
        return "ExamPresent{" +
                "id=" + id +
                ", module=" + module +
                ", student=" + student +
                ", salle=" + salle +
                ", observer=" + observer +
                ", createdAt=" + createdAt +
                '}';
    }
}
