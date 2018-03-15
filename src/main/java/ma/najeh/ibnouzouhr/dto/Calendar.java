package ma.najeh.ibnouzouhr.dto;

import ma.najeh.ibnouzouhr.model.Planing;

import java.io.Serializable;
import java.util.Date;

public class Calendar implements Serializable {

    private Long id;
    private String title;
    private Date start;
    private String description;
    private Date end;
    private Planing planing;
    private String className="";

    public Long getId() {
        return id;
    }

    public Calendar setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Calendar setTitle(String title) {
        this.title = title;
        return this;
    }

    public Date getStart() {
        return start;
    }

    public Calendar setStart(Date start) {
        this.start = start;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Calendar setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public Calendar setEnd(Date end) {
        this.end = end;
        return this;
    }

    public Planing getPlaning() {
        return planing;
    }

    public Calendar setPlaning(Planing planing) {
        this.planing = planing;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Calendar setClassName(String className) {
        this.className = className;
        return this;
    }

    public void addClassName(String newClassName) {
        this.className+=" "+newClassName+" ";
    }
}

