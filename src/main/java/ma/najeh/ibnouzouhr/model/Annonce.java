package ma.najeh.ibnouzouhr.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table
public class Annonce implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String content;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public Annonce setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Annonce setContent(String content) {
        this.content = content;
        return this;
    }
}
