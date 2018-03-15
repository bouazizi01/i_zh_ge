package ma.najeh.ibnouzouhr.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ma.najeh.ibnouzouhr.config.CustomDateDeserializer;
import ma.najeh.ibnouzouhr.config.CustomDateSerializer;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User implements Comparable<Student>, Serializable {
    private String codeAPOGEE;
    private String firstInscription;
    private String cne;
    private String cin;
    private String firstName;
    private String firstNameAr;
    private String lastName;
    private String lastNameAr;
    private String gender;
    private String hometown;
    private String hometownAr;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date birthday;
    private String birthProvince;
    private String diplome;
    private String etablissement;
    private String adress;
    private String step;
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.PERSIST})
    @OneToMany(mappedBy = "student")
    @JsonIgnore
    public Set<Inscription> inscriptions;

    public Student() {
        this.role = "student";
    }

    public Student(String username, String password, String firstName, String lastName, String codeAPOGEE, String cne,
                   String cin) {
        super(username, password, "student");
        this.codeAPOGEE = codeAPOGEE;
        this.cne = cne;
        this.cin = cin;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private List<Group> groups;
    @ManyToOne
    private Branch branch;

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String getCodeAPOGEE() {
        return codeAPOGEE;
    }

    public void setCodeAPOGEE(String codeAPOGEE) {
        this.codeAPOGEE = codeAPOGEE;
        this.username = codeAPOGEE;
    }

    public String getFirstInscription() {
        return firstInscription;
    }

    public void setFirstInscription(String firstInscription) {
        this.firstInscription = firstInscription;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin.replace(" ", "");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstNameAr() {
        return firstNameAr;
    }

    public void setFirstNameAr(String firstNameAr) {
        this.firstNameAr = firstNameAr;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastNameAr() {
        return lastNameAr;
    }

    public void setLastNameAr(String lastNameAr) {
        this.lastNameAr = lastNameAr;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public String getStep() {
        return step;
    }

    public Student setStep(String step) {
        this.step = step;
        return this;
    }

    public String getBirthProvince() {
        return birthProvince;
    }

    public void setBirthProvince(String birthProvince) {
        this.birthProvince = birthProvince;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getHometownAr() {
        return hometownAr;
    }

    public void setHometownAr(String hometownAr) {
        this.hometownAr = hometownAr;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public Student setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (codeAPOGEE != null ? !codeAPOGEE.equals(student.codeAPOGEE) : student.codeAPOGEE != null) return false;
        if (cne != null ? !cne.equals(student.cne) : student.cne != null) return false;
        if (cin != null ? !cin.equals(student.cin) : student.cin != null) return false;
        if (firstName != null ? !firstName.equals(student.firstName) : student.firstName != null) return false;
        return firstNameAr != null ? firstNameAr.equals(student.firstNameAr) : student.firstNameAr == null;
    }

    @Override
    public int hashCode() {
        int result = codeAPOGEE != null ? codeAPOGEE.hashCode() : 0;
        result = 31 * result + (cne != null ? cne.hashCode() : 0);
        result = 31 * result + (cin != null ? cin.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (firstNameAr != null ? firstNameAr.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "codeAPOGEE='" + codeAPOGEE + '\'' +
                ", firstInscription='" + firstInscription + '\'' +
                ", cne='" + cne + '\'' +
                ", cin='" + cin + '\'' +
                ", firstName='" + firstName + '\'' +
                ", firstNameAr='" + firstNameAr + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastNameAr='" + lastNameAr + '\'' +
                ", gender='" + gender + '\'' +
                ", hometown='" + hometown + '\'' +
                ", hometownAr='" + hometownAr + '\'' +
                ", birthday=" + birthday +
                ", birthProvince='" + birthProvince + '\'' +
                ", diplome='" + diplome + '\'' +
                ", etablissement='" + etablissement + '\'' +
                ", adress='" + adress + '\'' +
                ", groups=" + groups +
                ", branch=" + branch +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        if (o.getFullName().equals(getFullName())) {
            return o.getCodeAPOGEE().compareTo(getCodeAPOGEE());
        }
        return o.getFullName().compareTo(getFullName());
    }
}
