package ma.najeh.ibnouzouhr.model;

import ma.najeh.ibnouzouhr.constant.Constant;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by youssef on 11/15/17.
 */
@Entity
@Table(name="teachers")
@PrimaryKeyJoinColumn(name="id")
public class Teacher extends User   implements Serializable {
    private String firstName;
    private String lastName;
    private String firstNameAr;
    private String lastNameAr;
    private String cin;
    private String ppr;
    private String gender;
    @DateTimeFormat (pattern = Constant.Date.DATE_FORMAT)
    private Date birthday;
    private String hometown;
    private String adress;
    private String email;
    private String emailPro;
    private String phone;
    @Column(name = "date_affectation_MESRSFC")
    @DateTimeFormat(pattern = Constant.Date.DATE_FORMAT)
    private Date affectationMESRSFC;
    @Column(name = "date_affectation_enseignement")
    @DateTimeFormat (pattern = Constant.Date.DATE_FORMAT)
    private Date affectationEnseignement;
    private String departement;
    private String specialite;
    private String universiteDiplomante;
    private String structureRecherche;
    private String fonctionExercee;
    private String grade;
    private String ancienneteGrade;
    private String categorie;
    private Integer echelon;
    private Integer indice;
    private String ancienneteEchelon;


    public Teacher() {
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

    public String getFirstNameAr() {
        return firstNameAr;
    }

    public void setFirstNameAr(String firstNameAr) {
        this.firstNameAr = firstNameAr;
    }

    public String getLastNameAr() {
        return lastNameAr;
    }

    public void setLastNameAr(String lastNameAr) {
        this.lastNameAr = lastNameAr;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPpr() {
        return ppr;
    }

    public void setPpr(String ppr) {
        this.ppr = ppr;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.replace(",",".");
    }

    public String getEmailPro() {
        return emailPro;
    }

    public void setEmailPro(String emailPro) {
        this.emailPro = emailPro.replace(",",".");
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getAffectationMESRSFC() {
        return affectationMESRSFC;
    }

    public void setAffectationMESRSFC(Date affectationMESRSFC) {
        this.affectationMESRSFC = affectationMESRSFC;
    }

    public Date getAffectationEnseignement() {
        return affectationEnseignement;
    }

    public void setAffectationEnseignement(Date affectationEnseignement) {
        this.affectationEnseignement = affectationEnseignement;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getUniversiteDiplomante() {
        return universiteDiplomante;
    }

    public void setUniversiteDiplomante(String universiteDiplomante) {
        this.universiteDiplomante = universiteDiplomante;
    }

    public String getStructureRecherche() {
        return structureRecherche;
    }

    public void setStructureRecherche(String structureRecherche) {
        this.structureRecherche = structureRecherche;
    }

    public String getFonctionExercee() {
        return fonctionExercee;
    }

    public void setFonctionExercee(String fonctionExercee) {
        this.fonctionExercee = fonctionExercee;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAncienneteGrade() {
        return ancienneteGrade;
    }

    public void setAncienneteGrade(String ancienneteGrade) {
        this.ancienneteGrade = ancienneteGrade;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Integer getEchelon() {
        return echelon;
    }

    public void setEchelon(Integer echelon) {
        this.echelon = echelon;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getAncienneteEchelon() {
        return ancienneteEchelon;
    }

    public void setAncienneteEchelon(String ancienneteEchelon) {
        this.ancienneteEchelon = ancienneteEchelon;
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }
}
