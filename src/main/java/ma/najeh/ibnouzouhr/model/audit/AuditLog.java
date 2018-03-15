package ma.najeh.ibnouzouhr.model.audit;

/**
 * Created by youssef on 12/26/17.
 */
import javax.persistence.*;
import java.util.Date;

@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String oldValue;
    @Lob
    private String newValue;
    private Date createdAt;
    private String username;

    public AuditLog() {
    }

    public AuditLog(String oldValue, String newValue, String username) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.username = username;
        this.createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public AuditLog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOldValue() {
        return oldValue;
    }

    public AuditLog setOldValue(String oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public String getNewValue() {
        return newValue;
    }

    public AuditLog setNewValue(String newValue) {
        this.newValue = newValue;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public AuditLog setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AuditLog setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                ", createdAt=" + createdAt +
                ", username='" + username + '\'' +
                '}';
    }
}
