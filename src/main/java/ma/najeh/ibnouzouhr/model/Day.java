package ma.najeh.ibnouzouhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Day implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "day")
    private List<Planing> planings;
    @Transient
    private static List<Day> days;

    public Day(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Day() {
    }
    public static List<Day> daysOfWeek(){
        days =new ArrayList<>();
        days.add(new Day(2L,"Lundi"));
        days.add(new Day(3L,"Mardi"));
        days.add(new Day(4L,"Mercredi"));
        days.add(new Day(5L,"Jeudi"));
        days.add(new Day(6L,"Vendredi"));
        days.add(new Day(7L,"Samedi"));
        return days;
    }

    public Long getId() {
        return id;
    }

    public Day setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Day setName(String name) {
        this.name = name;
        return this;
    }

    public List<Planing> getPlanings() {
        return planings;
    }

    public Day setPlanings(List<Planing> planings) {
        this.planings = planings;
        return this;
    }
}
