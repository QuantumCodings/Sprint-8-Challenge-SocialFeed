package com.bloomtech.socialfeed.observerpattern;

import com.bloomtech.socialfeed.models.Post;
import com.bloomtech.socialfeed.models.User;
import com.bloomtech.socialfeed.repositories.PostRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TODO: Implement Observer Pattern
public class SourceFeed implements Source {
    private final PostRepository postRepository = new PostRepository();

    private List<Post> posts;
    private List<Observer> observers;

    public SourceFeed() {
        this.posts = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void getAllPosts() {
        posts = postRepository.getAllPosts();
        updateAll();
    }

    public Post addPost(User user, String body) {
        Post post = new Post(user.getUsername(),
                LocalDateTime.now(), // Use LocalDateTime directly
                body);
        posts = postRepository.addPost(post);
        updateAll();
        return post;
    }

    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void updateAll() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
