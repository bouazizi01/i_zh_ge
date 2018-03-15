package ma.najeh.ibnouzouhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ma.najeh.ibnouzouhr.config.CustomDateDeserializer;
import ma.najeh.ibnouzouhr.config.CustomDateSerializer;
import ma.najeh.ibnouzouhr.dto.UserDetailsDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String username;
    @JsonIgnore
    protected char[] password;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date joinAt;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date lastLogin;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    protected Date lastLogout;
    protected String role;

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password.toCharArray();
        this.role = role;
        this.joinAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
    public void setPassword(String password) {
        this.password = password.toCharArray();
    }

    public Date getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Date joinAt) {
        this.joinAt = joinAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public User setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public Date getLastLogout() {
        return lastLogout;
    }

    public User setLastLogout(Date lastLogout) {
        this.lastLogout = lastLogout;
        return this;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    @JsonIgnore
    public UserDetailsDto getUserDetailsDto() {
        UserDetailsDto userDetailsDto= new UserDetailsDto(username, password, role);

        if (this instanceof Admin) {
            Admin admin = (Admin) this;
            userDetailsDto= new UserDetailsDto(username, password, role, admin.getType());
        } else if (this instanceof Teacher) {
            Teacher teacher = (Teacher) this;
            userDetailsDto=  new UserDetailsDto(username, password, role, teacher.getFirstName(), teacher.getLastName());
        } else if (this instanceof Student) {
            Student student = (Student) this;
            userDetailsDto=  new UserDetailsDto(username, password, role, student.getFirstName(), student.getLastName(), student.getCodeAPOGEE(), student.getCne());
        } else if (this instanceof UserConfig) {
            UserConfig userConfig = (UserConfig) this;
            userDetailsDto=  new UserDetailsDto(username, password, role, userConfig.getFirstName(), userConfig.getLastName());
        }

        userDetailsDto.setId(getId());
        return userDetailsDto ;
    }
}
