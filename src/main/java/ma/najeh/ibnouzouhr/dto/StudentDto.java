package ma.najeh.ibnouzouhr.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jaoua on 1/30/2018.
 */
public class StudentDto implements Serializable {
    private String codeAPOGEE;
    private String cne;
    private String cin;
    private String firstName;
    private String firstNameAr;
    private String lastName;
    private String lastNameAr;
    private String gender;
    private String hometown;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String birthProvince;
    private String diplome;
    private String adress;

    public String getCodeAPOGEE() {
        return codeAPOGEE;
    }

    public StudentDto setCodeAPOGEE(String codeAPOGEE) {
        this.codeAPOGEE = codeAPOGEE;
        return this;
    }

    public String getCne() {
        return cne;
    }

    public StudentDto setCne(String cne) {
        this.cne = cne;
        return this;
    }

    public String getCin() {
        return cin;
    }

    public StudentDto setCin(String cin) {
        this.cin = cin;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public StudentDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getFirstNameAr() {
        return firstNameAr;
    }

    public StudentDto setFirstNameAr(String firstNameAr) {
        this.firstNameAr = firstNameAr;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public StudentDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getLastNameAr() {
        return lastNameAr;
    }

    public StudentDto setLastNameAr(String lastNameAr) {
        this.lastNameAr = lastNameAr;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public StudentDto setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getHometown() {
        return hometown;
    }

    public StudentDto setHometown(String hometown) {
        this.hometown = hometown;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public StudentDto setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getBirthProvince() {
        return birthProvince;
    }

    public StudentDto setBirthProvince(String birthProvince) {
        this.birthProvince = birthProvince;
        return this;
    }

    public String getDiplome() {
        return diplome;
    }

    public StudentDto setDiplome(String diplome) {
        this.diplome = diplome;
        return this;
    }

    public String getAdress() {
        return adress;
    }

    public StudentDto setAdress(String adress) {
        this.adress = adress;
        return this;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "codeAPOGEE='" + codeAPOGEE + '\'' +
                ", cne='" + cne + '\'' +
                ", cin='" + cin + '\'' +
                ", firstName='" + firstName + '\'' +
                ", firstNameAr='" + firstNameAr + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastNameAr='" + lastNameAr + '\'' +
                ", gender='" + gender + '\'' +
                ", hometown='" + hometown + '\'' +
                ", birthday=" + birthday +
                ", birthProvince='" + birthProvince + '\'' +
                ", diplome='" + diplome + '\'' +
                ", adress='" + adress + '\'' +
                '}';
    }
}
