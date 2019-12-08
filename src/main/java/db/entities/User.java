package db.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "C_USERS")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "position")
    private String position;

    @Column(name = "department")
    private String department;

    @Column(name = "location")
    private String location;

    public User(String email) {
        this.email = email;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public User setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public User setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public User setDepartment(String department) {
        this.department = department;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public User setLocation(String location) {
        this.location = location;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public User setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
