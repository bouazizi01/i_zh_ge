package ma.najeh.ibnouzouhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    private String url;
    private Date createdAt;
    @ManyToMany(cascade = {CascadeType.MERGE })
    @JoinTable(
            name = "notifications_users",
            joinColumns = { @JoinColumn(name = "notification_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    @JsonIgnore
    private List<User> users;

    public Notification() {
    }

    public Notification(String message, String url, List<? extends User> users) {
        this.message = message;
        this.url = url;
        this.createdAt = new Date();
        this.users = new ArrayList<>(users);
    }


    public Notification(StringBuilder message, String url, List<? extends User> users) {
        this(message.toString(),url,users);
    }

    public Notification(StringBuilder message, String url, User user) {
        this.message=message.toString();
        this.url=url;
        ArrayList<User> users=new ArrayList();
        users.add(user);
        this.users=users;
    }


    public Long getId() {
        return id;
    }

    public Notification setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Notification setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Notification setUrl(String url) {
        this.url = url;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Notification setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    public Notification setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;

        Notification that = (Notification) o;

        if (!getId().equals(that.getId())) return false;
        if (!getMessage().equals(that.getMessage())) return false;
        if (!getUrl().equals(that.getUrl())) return false;
        if (!getCreatedAt().equals(that.getCreatedAt())) return false;
        return getUsers().equals(that.getUsers());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getMessage().hashCode();
        result = 31 * result + getUrl().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        result = 31 * result + getUsers().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                ", users=" + users +
                '}';
    }
}
