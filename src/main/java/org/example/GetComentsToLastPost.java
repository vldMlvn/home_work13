package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GetComentsToLastPost {
    public static void main(String[] args) throws IOException {
        readAndWriteToFileLastPostComents(4);
    }
  public static void readAndWriteToFileLastPostComents(int id) throws IOException {
      String urlUsersPost = "https://jsonplaceholder.typicode.com/users/" + id + "/posts";
      Connection.Response response = Jsoup.connect(urlUsersPost)
              .header("Content-Type", "application/json")
              .ignoreContentType(true)
              .method(Connection.Method.GET)
              .execute();
      if (response.body().replace("[]", "").isEmpty()) {
          System.out.println("User not found :(");
      } else {
          JsonArray postsArray = new Gson().fromJson(response.body(), JsonArray.class);
          int maxId = 0;
          for (JsonElement element : postsArray) {
              JsonObject post = element.getAsJsonObject();
              int postId = post.get("id").getAsInt();
              if (postId > maxId) {
                  maxId = postId;
              }
          }
          String urlComents = "https://jsonplaceholder.typicode.com/posts/" + maxId + "/comments";
          Connection.Response postComents = Jsoup.connect(urlComents)
                  .header("Content-Type", "application/json")
                  .ignoreContentType(true)
                  .method(Connection.Method.GET)
                  .execute();
          String rez = postComents.body();
          System.out.println(rez);
          String fileName = "user-" + id + "-post-" + maxId + "-comments.json";
          File file = new File(fileName);
          FileWriter fw = new FileWriter(file);
          fw.write(rez);
          fw.close();
      }
  }
}