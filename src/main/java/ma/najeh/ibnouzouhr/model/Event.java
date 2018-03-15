package ma.najeh.ibnouzouhr.model;

import ma.najeh.ibnouzouhr.constant.Constant;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    private String content;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startAt;

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

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public String getContent() {
        return content;
    }

    public Event setContent(String content) {
        this.content = content;
        return this;
    }
}
