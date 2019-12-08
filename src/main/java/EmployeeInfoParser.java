import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import enums.DetailsAboutPeople;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class EmployeeInfoParser {
    //   private static final Logger LOGGER = Logger.getLogger(EmployeeInfoParser.class);
    // TODO: method with adding basic auth in headers / class with response model / class with employee model / class with db adder methods
    // TODO add assertions to responses
    // add gitignore
    private static final String endpoint = "https://confluence.sperasoft.com/display/~%s";   // add in properties
    private static String user = "";
    private static String pass = "";
    private static final String groupsOfUsersPath = "https://confluence.sperasoft.com/rest/api/group/lu.confluence.global.users/member?limit=200&expand=status&start=%s";


    public static void readCredsfromconsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your userName: ");

        user = reader.readLine();

        System.out.print("Enter your password: ");

        pass = reader.readLine(); // TODO: hide input from console
    }

    public static List getUsersEmail() throws IOException {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user, pass);
        List usersEmailList = new ArrayList();

        for (int i = 0; i < 800; i += 200) {
            HttpGet httpGet = new HttpGet(String.format(groupsOfUsersPath, i));

            HttpHost targetHost = new HttpHost("confluence.sperasoft.com", 443, "https");
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                    creds);
            credsProvider.setCredentials(AuthScope.ANY, creds);

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(targetHost, basicAuth);

            // Add AuthCache to the execution context
            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);


            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(targetHost, httpGet, context);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "UTF-8");
            JSONObject obj = new JSONObject(body);
            JSONArray arr = obj.getJSONArray("results");

            for (int j = 0; j < arr.length(); j++) {
                usersEmailList.add(arr.getJSONObject(j).get("username").toString());
            }
        }


        return usersEmailList;
    }

    public List<String> getAccountInfoResponse(List<String> usersEmailList) throws IOException {


        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user, pass);
        List pagesList = new ArrayList<String>();
        int i = 0;
        for (String email : usersEmailList) {
            HttpGet httpGet = new HttpGet(String.format(endpoint, email));
            i++;
            System.out.println(i + ") " + email);
            HttpHost targetHost = new HttpHost("confluence.sperasoft.com", 443, "https");
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                    creds);
            credsProvider.setCredentials(AuthScope.ANY, creds);

            // Create AuthCache instance
            AuthCache authCache = new BasicAuthCache();
            // Generate BASIC scheme object and add it to the local auth cache
            BasicScheme basicAuth = new BasicScheme();
            authCache.put(targetHost, basicAuth);

            // Add AuthCache to the execution context
            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);


            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(targetHost, httpGet, context);
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "UTF-8");
            //  System.out.println(body);
            pagesList.add(body);
        }
        return pagesList;
    }

    public List<HashMap<DetailsAboutPeople, String>> getAccountInfo(List<String> pagesList) throws ParseException    // statement for null value of string & change this method with user model instead hashmap
    {
        List<HashMap<DetailsAboutPeople, String>> accountInfo = new ArrayList<>();
        int i = 0;
        for (String html : pagesList) {
            i++;
            Document doc = Jsoup.parse(html);
            String email = doc.select("#email").first().text();
            String position = doc.select("#userparam-position").first().text();
            String location = doc.select("#userparam-location").first().text();
            String department = doc.select("#userparam-department").first().text();
            String fullName = doc.select("#fullName").first().text();
            String firstName = fullName.split(" ")[0];
            String secondName = fullName.split(" ")[1];

            String aboutMeContent = "";
            String startDate = "";
            String birthday = "";
            if (doc.select("#profile-about-me-content").first() != null) {
                //    Element aboutMeElem = doc.select("#profile-about-me-content").first();
                //     if ( aboutMeElem.text().isEmpty()||aboutMeElem.text() == null){
                aboutMeContent = doc.select("#profile-about-me-content").first().text();
            }

            try {
                startDate = formatDateForSQL(aboutMeContent.split("Start Date: ")[1].split(" ")[0]);

                birthday = formatDateForSQL(aboutMeContent.split("Birthday: ")[1]);
            } catch (Exception e) {
                continue;
            }
            HashMap<DetailsAboutPeople, String> peopleInfo = new HashMap<>();
            peopleInfo.put(DetailsAboutPeople.EMAIL, email);
            peopleInfo.put(DetailsAboutPeople.POSITION, position);
            peopleInfo.put(DetailsAboutPeople.LOCATION, location);
            peopleInfo.put(DetailsAboutPeople.DEPARTMENT, department);
            peopleInfo.put(DetailsAboutPeople.FIRST_NAME, firstName);
            peopleInfo.put(DetailsAboutPeople.SECOND_NAME, secondName);
            peopleInfo.put(DetailsAboutPeople.START_DATE_AT_COMPANY, startDate);
            peopleInfo.put(DetailsAboutPeople.BIRTHDAY, birthday);
            accountInfo.add(peopleInfo);
            System.out.println(i + ") " + firstName + " " + secondName + " " + department + " " + location + " " + position + " Start Date: " + startDate + " Birthday: " + birthday);
        }
        return accountInfo;
    }

    public String formatDateForSQL( String oldDateString ) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
        Date d = sdf.parse( oldDateString );
        sdf.applyPattern( "yyyy-MM-dd" );
        String newDateString = sdf.format( d );
        return newDateString;
    }

    public void setDataIntoBase(HashMap data)  // need new class for it or it will bee arraylist of hashmaps or just model of employee
    {

    }
}
