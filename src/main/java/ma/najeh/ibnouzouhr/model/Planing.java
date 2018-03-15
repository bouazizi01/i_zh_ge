package ma.najeh.ibnouzouhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ma.najeh.ibnouzouhr.config.CustomDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by youssef on 11/28/17.
 */
@Entity
@Table
public class Planing implements Serializable,Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Day day;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date planingDate;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "h", column = @Column(name = "start_h")),
            @AttributeOverride(name = "m", column = @Column(name = "start_m"))
    })
    private Hour startHour;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "h", column = @Column(name = "end_h")),
            @AttributeOverride(name = "m", column = @Column(name = "end_m"))
    })
    private Hour endHour;
    @ManyToOne
    private Seance seance;
    @JsonIgnore
    @OneToMany(mappedBy = "planing")
    private Set<Absence> absences;
    @JsonIgnore
    @OneToMany(mappedBy = "planing")
    private Set<HomeWork> homeWorks;
    public Planing() {
    }

    public Planing(String startH, String endH) {
        String[] start=startH.split(":");
        String[] end=endH.split(":");
        startHour=new Hour();
        endHour=new Hour();
        startHour.setH(Integer.parseInt(start[0]));
        startHour.setM(Integer.parseInt(start[1]));
        endHour.setH(Integer.parseInt(end[0]));
        endHour.setM(Integer.parseInt(end[1]));
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPlaningDate() {
        return planingDate;
    }

    public Planing setPlaningDate(Date planingDate) {
        this.planingDate = planingDate;
        return this;
    }

    public Hour getStartHour() {
        return startHour;
    }

    public Planing setStartHour(Hour startHour) {
        this.startHour = startHour;
        return this;
    }

    public Hour getEndHour() {
        return endHour;
    }

    public Planing setEndHour(Hour endHour) {
        this.endHour = endHour;
        return this;
    }

    public Seance getSeance() {
        return seance;
    }

    public Planing setSeance(Seance seance) {
        this.seance = seance;
        return this;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Day getDay() {
        return day;
    }

    public Planing setDay(Day day) {
        this.day = day;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Planing)) return false;

        Planing planing = (Planing) o;

        if (getDay() != null ? !getDay().equals(planing.getDay()) : planing.getDay() != null) return false;
        if (getStartHour() != null ? !getStartHour().equals(planing.getStartHour()) : planing.getStartHour() != null)
            return false;
        return getEndHour() != null ? getEndHour().equals(planing.getEndHour()) : planing.getEndHour() == null;
    }

    @Override
    public int hashCode() {
        int result = getDay() != null ? getDay().hashCode() : 0;
        result = 31 * result + (getStartHour() != null ? getStartHour().hashCode() : 0);
        result = 31 * result + (getEndHour() != null ? getEndHour().hashCode() : 0);
        return result;
    }

    public Set<Absence> getAbsences() {
        return absences;
    }

    public Planing setAbsences(Set<Absence> absences) {
        this.absences = absences;
        return this;
    }

    public Set<HomeWork> getHomeWorks() {
        return homeWorks;
    }

    public Planing setHomeWorks(Set<HomeWork> homeWorks) {
        this.homeWorks = homeWorks;
        return this;
    }
}
