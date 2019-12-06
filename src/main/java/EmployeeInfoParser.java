
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.sun.org.apache.xerces.internal.xs.StringList;
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
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


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
        HttpGet httpGet = new HttpGet(String.format(groupsOfUsersPath, 0)); // for first 200


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
        List usersEmailList = new ArrayList();
        for (int i = 0; i < arr.length(); i++) {
            usersEmailList.add(arr.getJSONObject(i).get("username").toString());
        }
        return usersEmailList;
    }

    public List<String> getAccountInfoResponse(List<String> usersEmailList) throws IOException {


        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user, pass);
        List pagesList = new ArrayList<StringList>();
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

    public HashMap<String, String> getAccountInfo(List<String> pagesList)    // statement for null value of string & change this method with user model instead hashmap
    {
        HashMap accountInfo = new HashMap<String, String>();
        int i = 0;
        for (String html : pagesList) {
            i++;
            Document doc = Jsoup.parse(html);
            String position = doc.select("#userparam-position").first().text();
            String location = doc.select("#userparam-location").first().text();
            String department = doc.select("#userparam-department").first().text();
            String fullName = doc.select("#fullName").first().text();
            String aboutMeContent = "";
            if (doc.select("#profile-about-me-content").first() != null) {
                //    Element aboutMeElem = doc.select("#profile-about-me-content").first();
                //     if ( aboutMeElem.text().isEmpty()||aboutMeElem.text() == null){
                aboutMeContent = doc.select("#profile-about-me-content").first().text();
            }
            accountInfo.put("position", position);
            accountInfo.put("location", location);
            accountInfo.put("department", department);
            accountInfo.put("fullName", fullName);   //  need to split first name and last name
            accountInfo.put("aboutMeContent", aboutMeContent);   //  need to split start date and birthday

            System.out.println(i + ") " + fullName + " " + department + " " + location + " " + department + " " + aboutMeContent);
        }
        return accountInfo;
    }

    public void setDataIntoBase(HashMap data)  // need new class for it or it will bee arraylist of hashmaps or just model of employee
    {

    }
}
