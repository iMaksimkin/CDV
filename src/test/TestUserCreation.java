import db.entities.CompanyInfo;
import db.entities.PersonalInfo;
import db.entities.User;
import db.services.CompanyInfoService;
import db.services.PersonalInfoService;
import db.services.UserService;

import java.sql.Date;

public class TestUserCreation {
    private static UserService userService = new UserService();
    private static PersonalInfoService personalInfoService = new PersonalInfoService();
    private static CompanyInfoService companyInfoService = new CompanyInfoService();

    public static void main(String[] args) {
        User user = new User("dmitry.makartsev@sperasoft.com");
        User userFromDb = userService.add(user);

        PersonalInfo personalInfo = new PersonalInfo()
                .setBirthday(Date.valueOf("1999-02-19"))
                .setName("Dmitry")
                .setSurname("Makartsev")
                .setStartDate(Date.valueOf("1999-02-19"))
                .setUser(user);
        PersonalInfo personalInfoFromDb = personalInfoService.add(personalInfo);

        CompanyInfo companyInfo = new CompanyInfo()
                .setDepartment("SIE")
                .setLocation("Volgograd")
                .setPosition("jun")
                .setUser(user);
        CompanyInfo companyInfoFromDb = companyInfoService.add(companyInfo);
        System.out.println(userFromDb);
        System.out.println(companyInfoFromDb);
        System.out.println(personalInfoFromDb);
    }

}
