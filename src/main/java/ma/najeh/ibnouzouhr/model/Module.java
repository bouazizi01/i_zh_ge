package ma.najeh.ibnouzouhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table
public class Module implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private String name;
    private String nextModules;
    private String previousModules;
    @Cascade(CascadeType.ALL)
    @ManyToOne
    private Semestre semestre;
    @Cascade(CascadeType.ALL)
    @ManyToOne
    private Branch branch;
    @Cascade(CascadeType.ALL)
    @ManyToOne
    private Teacher teacher;
    @JsonIgnore
    @OneToMany(mappedBy = "module")
    private Set<Inscription> inscriptions;
    public Module() {
    }

    public Module(String name, Semestre semestre, Branch branch) {
        this.name = name;
        this.semestre = semestre;
        this.branch = branch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Module setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public String getNextModules() {
        if (nextModules==null)
            return "";
        return nextModules;
    }

    public Module setNextModules(String nextModules) {
        this.nextModules = nextModules;
        return this;
    }

    public String getPreviousModules() {
        if (previousModules==null)
            return "";
        return previousModules;
    }

    public Module setPreviousModules(String previousModules) {
        this.previousModules = previousModules;
        return this;
    }



    @Override
    public String toString() {
        return "ModuleDto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", semestre=" + semestre +
                ", branch=" + branch +
                '}';
    }


    public ArrayList<String> getNextModulesAsList() {
        String [] modulesArray=getNextModules().split(";");
        return new ArrayList<>(Arrays.asList(modulesArray));
    }

    public ArrayList<String> getPreviousModulesAsList() {
        String [] modulesArray=getPreviousModules().split(";");
        return new ArrayList<>(Arrays.asList(modulesArray));
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public Module setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }
}
