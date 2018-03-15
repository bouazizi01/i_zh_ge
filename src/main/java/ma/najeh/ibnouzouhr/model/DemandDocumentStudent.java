package ma.najeh.ibnouzouhr.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table
public class DemandDocumentStudent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date demandAt;
    private Date processedAt;
    private String state;
    private String reason;
    private String typeDocument;
    @ManyToOne
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDemandAt() {
        return demandAt;
    }

    public void setDemandAt(Date demandAt) {
        this.demandAt = demandAt;
    }

    public Date getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Date processedAt) {
        this.processedAt = processedAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    @Override
    public String toString() {
        return "DemandDocumentStudent{" +
                "id=" + id +
                ", demandAt=" + demandAt +
                ", processedAt=" + processedAt +
                ", state='" + state + '\'' +
                ", reason='" + reason + '\'' +
                ", typeDocument='" + typeDocument + '\'' +
                ", student=" + student +
                '}';
    }
}
