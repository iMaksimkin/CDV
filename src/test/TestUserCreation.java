import db.entities.User;
import db.services.UserService;

import java.sql.Date;

public class TestUserCreation {
    private static UserService userService = new UserService();

    public static void main(String[] args) {
        User user = new User("dmitry.makartsev@sperasoft.com")
                .setBirthday(Date.valueOf("1999-02-19"))
                .setName("Dmitry")
                .setSurname("Makartsev")
                .setStartDate(Date.valueOf("1999-02-19"))
                .setDepartment("SIE")
                .setLocation("Volgograd")
                .setPosition("jun");



        User userFromDb = userService.add(user);

        System.out.println(userFromDb);
    }

}
