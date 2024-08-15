package com.bloomtech.socialfeed.repositories;

import com.bloomtech.socialfeed.models.Post;
import com.bloomtech.socialfeed.helpers.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostRepository {
    private static final String POST_DATA_PATH = "src/resources/PostData.json";
    private Gson gson;

    public PostRepository() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
    }

    public List<Post> getAllPosts() {
        List<Post> allPosts = new ArrayList<>();
        try (FileReader reader = new FileReader(POST_DATA_PATH)) {
            Type postListType = new TypeToken<ArrayList<Post>>(){}.getType();
            allPosts = gson.fromJson(reader, postListType);
        } catch (IOException e) {
            // If the file doesn't exist or is empty, return an empty list
            return new ArrayList<>();
        }
        return allPosts != null ? allPosts : new ArrayList<>();
    }

    public List<Post> findByUsername(String username) {
        return getAllPosts()
                .stream()
                .filter(p -> p.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public List<Post> addPost(Post post) {
        List<Post> allPosts = getAllPosts();
        allPosts.add(post);

        // Write the new Post data to the PostData.json file
        String json = gson.toJson(allPosts);

        try (FileWriter writer = new FileWriter(POST_DATA_PATH)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return an updated list of all posts
        return allPosts;
    }
}
