package ma.najeh.ibnouzouhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by youssef on 12/4/17.
 */
@Entity
public class Branch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String nameAr;
    private String abbrv;
    //master fonda,prof/licence fonda, prof
    private String type;
    @JsonIgnore
    @ManyToOne
    private Specialty specialty;

    public Branch() {
    }

    public Branch(String name,String abbrv, String type, Specialty specialty) {
        this.name = name;
        this.abbrv = abbrv;
        this.type = type;
        this.specialty = specialty;
    }
    public Branch(String name, String abbrv, String type) {
        this.name = name;
        this.abbrv = abbrv;
        this.type = type;
        this.specialty = new Specialty(name,abbrv);
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

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Branch branch = (Branch) o;

        return name != null ? name.equals(branch.name) : branch.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameAr='" + nameAr + '\'' +
                ", abbrv='" + abbrv + '\'' +
                ", type='" + type + '\'' +
                ", specialty=" + specialty +
                '}';
    }
}
