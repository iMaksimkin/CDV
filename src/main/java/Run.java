import collect_data.DataCollector;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class Run {
    public static void main(String[] args) throws IOException, ParseException {

        EmployeeInfoParser employeeInfoParser = new EmployeeInfoParser();

        employeeInfoParser.readCredsfromconsole();
        List userEmailsList = employeeInfoParser.getUsersEmail();

        List userPagesList = employeeInfoParser.getAccountInfoResponse(userEmailsList);
        List peopleInfo = employeeInfoParser.getAccountInfo(userPagesList);

        DataCollector dataCollector = new DataCollector();
        dataCollector.setUsersToDb(peopleInfo);

    }
}
