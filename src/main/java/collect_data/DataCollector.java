package collect_data;

import db.entities.CompanyInfo;
import db.entities.PersonalInfo;
import db.entities.User;
import db.services.CompanyInfoService;
import db.services.PersonalInfoService;
import db.services.UserService;
import util.DateUtil;

import java.util.HashMap;
import java.util.List;

public class DataCollector {

    public void setUsersToDb(List<HashMap<DataFields, String>> users){

    }

    private void setUserToDb(HashMap<DataFields, String> userInfoFromConfluence){
        User user = new User();
        PersonalInfo personalInfo = new PersonalInfo();
        CompanyInfo companyInfo = new CompanyInfo();

        fillUser(user, userInfoFromConfluence);
        fillPersonalInfo(personalInfo, userInfoFromConfluence, user);
        fillCompanyInfo(companyInfo, userInfoFromConfluence, user);

        sentDataToDb(user, personalInfo, companyInfo);
    }

    private void fillUser(User user, HashMap<DataFields, String> userInfoFromConfluence){
        user
                .setEmail(userInfoFromConfluence.get(DataFields.EMAIL));
    }

    private void fillPersonalInfo(PersonalInfo personalInfo, HashMap<DataFields, String> userInfoFromConfluence, User user){
        personalInfo
                .setBirthday(DateUtil.convertStringToSqlDate(userInfoFromConfluence.get(DataFields.BIRTHDAY)))
                .setName(userInfoFromConfluence.get(DataFields.NAME))
                .setSurname(userInfoFromConfluence.get(DataFields.SURNAME))
                .setStartDate(DateUtil.convertStringToSqlDate(userInfoFromConfluence.get(DataFields.START_DATE)))
                .setUser(user);
    }

    private void fillCompanyInfo(CompanyInfo companyInfo, HashMap<DataFields, String> userInfoFromConfluence, User user){
        companyInfo
                .setDepartment(userInfoFromConfluence.get(DataFields.DEPARTMENT))
                .setLocation(userInfoFromConfluence.get(DataFields.LOCATION))
                .setPosition(userInfoFromConfluence.get(DataFields.POSITION))
                .setUser(user);
    }

    private void sentDataToDb(User user, PersonalInfo personalInfo, CompanyInfo companyInfo){
        sentUserToDb(user);
        sentPersonalInfoToDb(personalInfo);
        sentCompanyInfoToDb(companyInfo);
    }

    private void sentUserToDb(User user){
        UserService userService = new UserService();
        userService.add(user);
    }

    private void sentPersonalInfoToDb(PersonalInfo personalInfo){
        PersonalInfoService personalInfoService = new PersonalInfoService();
        personalInfoService.add(personalInfo);
    }

    private void sentCompanyInfoToDb(CompanyInfo companyInfo){
        CompanyInfoService companyInfoService = new CompanyInfoService();
        companyInfoService.add(companyInfo);
    }

}
