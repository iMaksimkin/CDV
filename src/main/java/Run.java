import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Run {
    public static void main(String[] args) throws IOException {

        EmployeeInfoParser employeeInfoParser = new EmployeeInfoParser();

        employeeInfoParser.readCredsfromconsole();
        List userEmailsList = employeeInfoParser.getUsersEmail();
       List userPagesList = employeeInfoParser.getAccountInfoResponse(userEmailsList);
        employeeInfoParser.getAccountInfo(userPagesList);

    }
}
