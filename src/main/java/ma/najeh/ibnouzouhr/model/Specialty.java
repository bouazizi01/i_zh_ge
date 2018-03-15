package ma.najeh.ibnouzouhr.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by youssef on 12/4/17.
 */
@Entity
public class Specialty implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String nameAr;
    private String abbrv;
    @JsonIgnore
    @OneToMany(mappedBy = "specialty")
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Branch> branchs;
    @ManyToOne
    private Teacher teacher;

    public Specialty() {
    }


    public Specialty(String name, String abbrv) {

        this.name = name;
        this.abbrv = abbrv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getAbbrv() {
        return abbrv;
    }

    public void setAbbrv(String abbrv) {
        this.abbrv = abbrv;
    }

    public List<Branch> getBranchs() {
        return branchs;
    }

    public Specialty setBranchs(List<Branch> branchs) {
        this.branchs = branchs;
        return this;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Specialty setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameAr='" + nameAr + '\'' +
                ", abbrv='" + abbrv + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Specialty specialty = (Specialty) o;

        return name != null ? name.equals(specialty.name) : specialty.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
