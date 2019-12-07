package db.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "PERSONAL_INFO")
public class PersonalInfo implements Serializable {

    @Id
    @Column(name = "personal_info_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int personalInfoId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "birthday")
    private Date birthday;



    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public PersonalInfo(String name, String surname, Date startDate, Date birthday, User user) {
        this.name = name;
        this.surname = surname;
        this.startDate = startDate;
        this.birthday = birthday;
        this.user = user;
    }

    public PersonalInfo() {
    }

    public int getPersonalInfoId() {
        return personalInfoId;
    }

    public PersonalInfo setPersonalInfoId(int personalInfoId) {
        this.personalInfoId = personalInfoId;
        return this;
    }

    public String getName() {
        return name;
    }

    public PersonalInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public PersonalInfo setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public PersonalInfo setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public PersonalInfo setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public User getUser() {
        return user;
    }

    public PersonalInfo setUser(User user) {
        this.user = user;
        return this;
    }
}
