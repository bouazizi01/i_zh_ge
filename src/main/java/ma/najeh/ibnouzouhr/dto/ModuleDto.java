package ma.najeh.ibnouzouhr.dto;

import java.io.Serializable;

/**
 * Created by youssef on 1/26/18.
 */
public class ModuleDto implements Serializable ,Comparable<ModuleDto>{
    private String code;
    private String name;
    private String semestre;

    public ModuleDto(String code, String name, String semestre) {
        this.code = code;
        this.name = name;
        this.semestre = semestre;
    }

    public String getCode() {
        return code;
    }

    public ModuleDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public ModuleDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getSemestre() {
        return semestre;
    }

    public ModuleDto setSemestre(String semestre) {
        this.semestre = semestre;
        return this;
    }

    @Override
    public int compareTo(ModuleDto o) {
        if (o.getSemestre().equals(getSemestre())) {
            return getCode().compareTo(o.getCode());
        }
        return getSemestre().compareTo(o.getSemestre());
    }
}
