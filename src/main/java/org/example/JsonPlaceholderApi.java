package org.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.dto.Address;
import org.example.dto.Company;
import org.example.dto.Geo;
import org.example.dto.User;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.io.IOException;
public class JsonPlaceholderApi {
    private final static String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    public static void main(String[] args) throws Exception {
        Geo geo=new Geo(10.144,-141.56);
       Address address=new Address
               ("Exemple street","Exemple suite","Exemple city","Exemple zip",geo);
       Company company=new Company("Exemple company","Exemple catchPhrase","Exemple bs");
       User user=new User("Vlad","vldMlvn","vldMlvn@exemple.com",address,"12345678"
       ,"exemplewebsite.com",company);
        updateUser(1, user);
        deleteUser(4);
        getAllUsers();
       getUserById(10);
       getUserByUsername("12");
        createUser(user);
    }
    public static void createUser(User user) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(BASE_URL);
        StringEntity requestEntity = new StringEntity(user.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 201) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            System.out.println("Done!User created: " + responseBody+"\n----------------------------------");
        } else {
            System.out.println
                    ("Failed to create user. Status code: " + statusCode+"\n----------------------------------");
        }
    }
    public static void updateUser(int id, User user) throws IOException {
        String url = BASE_URL + "/" + id;
        try {
            Connection.Response response = Jsoup.connect(url)
                    .header("Content-Type", "application/json")
                    .ignoreContentType(true)
                    .requestBody(user.toString())
                    .method(Connection.Method.PUT)
                    .execute();
            String rez = response.body();
            int status = response.statusCode();
            System.out.println("Done! Status code: " + status);
            System.out.println(rez+"\n----------------------------------");
        } catch (HttpStatusException e) {
            int status = e.getStatusCode();
            System.out.println("User not found :(+\n----------------------------------");
        }
    }
    public static void deleteUser(int id) throws IOException {
        String url = BASE_URL + "/" + id;
        try {
            Connection.Response response = Jsoup.connect(url)
                    .header("Content-Type", "application/json")
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();
            String body = response.body();
            if (body.replace("[]", "").isEmpty()) {
                System.out.println("User not found :(\n----------------------------------");
            } else {
                response = Jsoup.connect(url)
                        .header("Content-Type", "application/json")
                        .ignoreContentType(true)
                        .method(Connection.Method.DELETE)
                        .execute();
                int status = response.statusCode();
                System.out.println("User deleted! Status code: " + status+"\n----------------------------------");
            }
        } catch (HttpStatusException e) {
            int status = e.getStatusCode();
            System.out.println("User not found :("+"\n----------------------------------");
        }
    }
    public static void getAllUsers() throws IOException {
        Connection.Response response = Jsoup.connect(BASE_URL)
                .header("Content-Type", "application/json")
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
        String rez = response.body();
        System.out.println(rez);
    }
    public static void getUserById(int id) throws IOException {
        String url = BASE_URL + "/" + id;
        try {
            Connection.Response response = Jsoup.connect(url)
                    .header("Content-Type", "application/json")
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();
            int status = response.statusCode();
            String rez = response.body();
          isExist(rez);
        } catch (HttpStatusException e) {
            int status = e.getStatusCode();
            System.out.println("User not found :("+"\n----------------------------------");
        }
    }
    public static void getUserByUsername(String username) throws IOException {
        String url = BASE_URL + "?username=" + username;
        try {
            Connection.Response response = Jsoup.connect(url)
                    .header("Content-Type", "application/json")
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();
            String rez = response.body();
            int status=response.statusCode();
            isExist(rez);
        } catch (HttpStatusException e) {
            int status = e.getStatusCode();
            System.out.println("User not found :("+"\n----------------------------------");
        }
    }
    public static void isExist(String rez){
        if (!rez.replace("[]", "").isEmpty()) {
            System.out.println("Done!");
            System.out.println(rez+"\n----------------------------------");
        } else {
            System.out.println("User not found :("+"\n----------------------------------");
        }
    }
}