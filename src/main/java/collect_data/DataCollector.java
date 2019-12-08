package collect_data;

import db.entities.User;
import db.services.UserService;
import enums.DetailsAboutPeople;
import util.DateUtil;

import java.util.HashMap;
import java.util.List;

public class DataCollector {

    public void setUsersToDb(List<HashMap<DetailsAboutPeople, String>> users){
        users.forEach(this::setUserToDb);
    }

    private void setUserToDb(HashMap<DetailsAboutPeople, String> userInfoFromConfluence){
        User user = new User();


        fillUser(user, userInfoFromConfluence);

        sentDataToDb(user);
    }

    private void fillUser(User user, HashMap<DetailsAboutPeople, String> userInfoFromConfluence){
        user
                .setEmail(userInfoFromConfluence.get(DetailsAboutPeople.EMAIL))
                .setBirthday(DateUtil.convertStringToSqlDate(userInfoFromConfluence.get(DetailsAboutPeople.BIRTHDAY)))
                .setName(userInfoFromConfluence.get(DetailsAboutPeople.FIRST_NAME))
                .setSurname(userInfoFromConfluence.get(DetailsAboutPeople.SECOND_NAME))
                .setStartDate(DateUtil.convertStringToSqlDate(userInfoFromConfluence.get(DetailsAboutPeople.START_DATE_AT_COMPANY)))
                .setDepartment(userInfoFromConfluence.get(DetailsAboutPeople.DEPARTMENT))
                .setLocation(userInfoFromConfluence.get(DetailsAboutPeople.LOCATION))
                .setPosition(userInfoFromConfluence.get(DetailsAboutPeople.POSITION));
    }

    /*private void fillPersonalInfo(PersonalInfo personalInfo, HashMap<DetailsAboutPeople, String> userInfoFromConfluence){
        personalInfo
                ;
    }

    private void fillCompanyInfo(CompanyInfo companyInfo, HashMap<DetailsAboutPeople, String> userInfoFromConfluence){
        companyInfo
                ;
    }*/

    private void sentDataToDb(User user){
        UserService userService = new UserService();
        userService.add(user);

    }

}
