//package db.entities;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.sql.Date;
//
//@Entity
//@Table(name = "PERSONAL_INFO")
//public class PersonalInfo implements Serializable {
//
//   /* @Id
//    @Column(name = "personal_info_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int personalInfoId;*/
//
//
//
//    @Id
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(referencedColumnName = "user_id", name = "user_id")
//    private User user;
//
//    public User getUser() {
//        return user;
//    }
//
//    public PersonalInfo(String name, String surname, Date startDate, Date birthday) {
//        this.name = name;
//        this.surname = surname;
//        this.startDate = startDate;
//        this.birthday = birthday;
//    }
//
//    public PersonalInfo() {
//    }
//
//    public PersonalInfo setUser(User user) {
//        this.user = user;
//        return this;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public PersonalInfo setName(String name) {
//        this.name = name;
//        return this;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public PersonalInfo setSurname(String surname) {
//        this.surname = surname;
//        return this;
//    }
//
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public PersonalInfo setStartDate(Date startDate) {
//        this.startDate = startDate;
//        return this;
//    }
//
//    public Date getBirthday() {
//        return birthday;
//    }
//
//    public PersonalInfo setBirthday(Date birthday) {
//        this.birthday = birthday;
//        return this;
//    }
//
//}
