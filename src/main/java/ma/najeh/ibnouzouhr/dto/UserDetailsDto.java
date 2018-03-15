package ma.najeh.ibnouzouhr.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Created by youssef on 12/9/17.
 */
public class UserDetailsDto implements UserDetails {

    private Long id;
    private String username;
    private char[] password;
    private String role;

    //only student
    private String codeAPOGEE;
    private String cne;
    // teacher and student
    private String firstName;
    private String lastName;
    //admin
    private String type;
    // super admin




    // student construct
    public UserDetailsDto(String username, char[] password, String role, String firstName, String lastName, String codeAPOGEE, String cne) {
        this(username,password,role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.codeAPOGEE = codeAPOGEE;
        this.cne = cne;

    }

    // teacher constuct
    public UserDetailsDto(String username, char[] password, String role, String firstName, String lastName) {
        this(username,password,role);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // admin constuct
    public UserDetailsDto(String username, char[] password, String role, String type) {
        this(username,password,role);
        this.type = type;
    }

    // normal constuct and for super admin
    public UserDetailsDto(String username, char[] password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(role));
        return authList;
    }

    @Override
    public String getPassword() {
        if (password==null)
            return "";
        return String.valueOf(password);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCodeAPOGEE() {
        return codeAPOGEE;
    }

    public void setCodeAPOGEE(String codeAPOGEE) {
        this.codeAPOGEE = codeAPOGEE;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @JsonIgnore
    public String getFullName() {
        if (role.equals("student") || role.equals("teacher")){
            return firstName + " "+ lastName;
        }else{
            return username;
        }
    }

    @Override
    public String toString() {
        return "UserDetailsDto{" +
                "username='" + username + '\'' +
                ", password=" + Arrays.toString(password) +
                ", role='" + role + '\'' +
                ", codeAPOGEE='" + codeAPOGEE + '\'' +
                ", cne='" + cne + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


}
