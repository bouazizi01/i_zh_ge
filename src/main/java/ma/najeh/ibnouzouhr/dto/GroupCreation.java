package ma.najeh.ibnouzouhr.dto;


import lombok.Data;

import java.io.Serializable;
@Data
public class GroupCreation implements Serializable{
    private String name;
    private String salle;
    private Long module;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public Long getModule() {
        return module;
    }

    public GroupCreation setModule(Long module) {
        this.module = module;
        return this;
    }
}
