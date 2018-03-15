package ma.najeh.ibnouzouhr.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Objects;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table
public class Note  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double note;
    private Double rattNate;
    @ManyToOne
    private Module module;
    @ManyToOne
    private Student student;
    private int year;
    private String session;
    private Date createdAt;
    @OneToMany(mappedBy = "note")
    private List<Reclamation> reclamations;


    public Note() {
    }

    public Note(double note, Module module, Student student, int year, String session) {
        this.note = note;
        this.module = module;
        this.student = student;
        this.year = year;
        this.session = session;
    }

    public Long getId() {
        return id;
    }

    public Note setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getNote() {
        return note;
    }

    public Note setNote(Double note) {
        this.note = note;
        return this;
    }

    public Module getModule() {
        return module;
    }

    public Note setModule(Module module) {
        this.module = module;
        return this;
    }

    public Student getStudent() {
        return student;
    }

    public Note setStudent(Student student) {
        this.student = student;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Note setYear(int year) {
        this.year = year;
        return this;
    }

    public String getSession() {
        return session;
    }

    public Note setSession(String session) {
        this.session = session;
        return this;
    }



    public Date getCreatedAt() {
        return createdAt;
    }

    public Note setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public List<Reclamation> getReclamations() {
        return reclamations;
    }

    public Note setReclamations(List<Reclamation> reclamations) {
        this.reclamations = reclamations;
        return this;
    }

    public Double getRattNate() {
        return rattNate;
    }

    public Note setRattNate(Double rattNate) {
        this.rattNate = rattNate;
        return this;
    }

    public boolean isReclamable(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(createdAt);
        calendar.add(Calendar.DAY_OF_YEAR,2);
        if (calendar.getTime().after(new Date())){
            return (Objects.isNull(getReclamations()) || getReclamations().isEmpty()) && (session.equals("ABI") || note<10);
        }
        return false;
    }
    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", note=" + note +
                ", module=" + module +
                ", student=" + student +
                ", year=" + year +
                ", session='" + session + '\'' +
                '}';
    }
}
