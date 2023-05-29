package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.IOException;

public class UsersOpenTask {
    public static void main(String[] args) throws IOException {
        getUserOpenTusk(11);
    }
    public static void getUserOpenTusk(int id) throws IOException {
        String url = "https://jsonplaceholder.typicode.com/users/" + id + "/todos";
        Connection.Response response = Jsoup.connect(url)
                .header("Content-Type", "application/json")
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();
        String rez = response.body();
        if (rez.replace("[]", "").isEmpty()) {
            System.out.println("User not found :(");
        } else {
            System.out.println("Open task for User " + id + " :");
            JSONArray tasksArray = new JSONArray(rez);
            for (int i = 0; i < tasksArray.length(); i++) {
                JSONObject taskObj = tasksArray.getJSONObject(i);
                boolean completed = taskObj.getBoolean("completed");
                if (!completed) {
                    System.out.println(taskObj.toString());
                }
            }
        }
    }
}