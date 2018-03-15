package ma.najeh.ibnouzouhr.dto;

import java.io.Serializable;

/**
 * Created by youssef on 1/13/18.
 */
public class Password implements Serializable{
    private String password;
    private String newPassword;
    private String retyNewPassword;

    public String getPassword() {
        return password;
    }

    public Password setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public Password setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getRetyNewPassword() {
        return retyNewPassword;
    }

    public Password setRetyNewPassword(String retyNewPassword) {
        this.retyNewPassword = retyNewPassword;
        return this;
    }
}
