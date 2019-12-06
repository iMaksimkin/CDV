
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;



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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class EmployeeInfoParser {
    //   private static final Logger LOGGER = Logger.getLogger(EmployeeInfoParser.class);
    // TODO: method with adding basic auth in headers / class with response model / class with employee model / class with db adder methods
    private static final String endpoint = "https://confluence.sperasoft.com/display/~%s";   // add in properties
    private static String user = "";
    private static String pass = "";
    private static final String groupsOfUsersPath = "https://confluence.sperasoft.com/rest/api/group/lu.confluence.global.users/member?limit=200&expand=status&start=%s";



    public static void readCredsfromconsole() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your userName: ");

        user = reader.readLine();

        System.out.print("Enter your password: ");

        pass = reader.readLine();
    }

    public static String getUsersEmail() throws IOException {
        System.out.println(user);
        System.out.println(pass);
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user, pass);
        HttpGet httpGet = new HttpGet(String.format(groupsOfUsersPath, 200));


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

        return null;
    }

    public String getAccountInfoResponse(String email) throws IOException {


        System.out.println(user);
        System.out.println(pass);
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user, pass);
        HttpGet httpGet = new HttpGet(String.format(endpoint, email));


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
        System.out.println(body);
        return body;
    }

    public HashMap<String, String> getAccountInfo(String html)    // null pointer
    {
        Document doc = Jsoup.parse(html);
        String position = doc.select("#userparam-position").first().text();
        Element location = doc.select("#userparam-location").get(0);
        Element department = doc.select("#userparam-department").get(0);
        Element fullName = doc.select("#fullName").get(0);
        Element aboutMeContent = doc.select("#profile-about-me-content").get(0);
        HashMap accountInfo = new HashMap<String, String>();
        accountInfo.put("position", position);   //  etc.
        System.out.println(location.text() + " " + department.text() + " " + fullName.text() + " " + aboutMeContent.text());
        return accountInfo;
    }

    public void setDataIntoBase(HashMap data)  // need new class for it or it will bee arraylist of hashmaps or just model of employee
    {

    }
}
