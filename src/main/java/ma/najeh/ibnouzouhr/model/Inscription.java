package ma.najeh.ibnouzouhr.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by youssef on 12/15/17.
 */
@Entity
public class Inscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Module module;

    public Inscription() {
    }

    public Inscription(Student student, Module module) {
        this.student = student;
        this.module = module;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "id=" + id +
                ", student=" + student +
                ", module=" + module +
                '}';
    }
}
