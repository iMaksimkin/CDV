package db.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "COMPANY_INFO")
public class CompanyInfo implements Serializable {

    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int companyId;

    @Column(name = "position")
    private String position;

    @Column(name = "department")
    private String department;

    @Column(name = "location")
    private String location;



    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public CompanyInfo(String position, String department, String location, User user) {
        this.position = position;
        this.department = department;
        this.location = location;
        this.user = user;
    }

    public CompanyInfo() {
    }

    public int getCompanyId() {
        return companyId;
    }

    public CompanyInfo setCompanyId(int companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public CompanyInfo setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public CompanyInfo setDepartment(String department) {
        this.department = department;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public CompanyInfo setLocation(String location) {
        this.location = location;
        return this;
    }

    public User getUser() {
        return user;
    }

    public CompanyInfo setUser(User user) {
        this.user = user;
        return this;
    }
}
