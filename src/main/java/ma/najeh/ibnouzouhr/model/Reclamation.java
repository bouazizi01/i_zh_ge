package ma.najeh.ibnouzouhr.model;



import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static ma.najeh.ibnouzouhr.constant.Constant.DEMAND_REC_STATE.ACCEPTED_ADMIN;
import static ma.najeh.ibnouzouhr.constant.Constant.DEMAND_REC_STATE.ACCEPTED_UPDATED;
import static ma.najeh.ibnouzouhr.constant.Constant.DEMAND_REC_STATE.INITIALIZED;
import static org.hibernate.annotations.CascadeType.PERSIST;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table
public class Reclamation  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double newNote;
    private String replayMessage;
    private String state;
    private Date createdAt;
    @Cascade({PERSIST,SAVE_UPDATE})
    @ManyToOne
    private Student student;
    @ManyToOne
    private Note note;

    public Long getId() {
        return id;
    }

    public Reclamation setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getNewNote() {
        return newNote;
    }

    public Reclamation setNewNote(Double newNote) {
        this.newNote = newNote;
        return this;
    }

    public String getReplayMessage() {
        return replayMessage;
    }

    public Reclamation setReplayMessage(String replayMessage) {
        this.replayMessage = replayMessage;
        return this;
    }

    public String getState() {
        return state;
    }

    public Reclamation setState(String state) {
        this.state = state;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Reclamation setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Student getStudent() {
        return student;
    }

    public Reclamation setStudent(Student student) {
        this.student = student;
        return this;
    }

    public Note getNote() {
        return note;
    }

    public Reclamation setNote(Note note) {
        this.note = note;
        return this;
    }


    public boolean isAcceptable(){

        return state.equals(ACCEPTED_ADMIN) || state.equals(INITIALIZED);
    }

    public boolean isRefusable(){

        return state.equals(ACCEPTED_ADMIN) || state.equals(INITIALIZED);
    }
}
