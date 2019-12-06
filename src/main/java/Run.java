import java.io.IOException;

public class Run {
    public static void main(String[] args) throws IOException {

       EmployeeInfoParser employeeInfoParser = new EmployeeInfoParser();
       employeeInfoParser.readCredsfromconsole();
        String str =  employeeInfoParser.getAccountInfoResponse("brad.doan@sperasoft.com");
       employeeInfoParser.getAccountInfo(str);
}}
