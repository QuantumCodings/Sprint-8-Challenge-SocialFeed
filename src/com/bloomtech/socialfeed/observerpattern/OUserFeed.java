package com.bloomtech.socialfeed.observerpattern;

import com.bloomtech.socialfeed.App;
import com.bloomtech.socialfeed.models.Post;
import com.bloomtech.socialfeed.models.User;

import java.util.ArrayList;
import java.util.List;

//TODO: Implement Observer Pattern
public class OUserFeed implements Observer {
    private User user;
    private List<Post> feed;

    public OUserFeed(User user) {
        this.user = user;
        this.feed = new ArrayList<>();
        App.sourceFeed.attach(this);
    }

    @Override
    public void update() {
        List<Post> allPosts = App.sourceFeed.getPosts();
        feed.clear();
        for (Post post : allPosts) {
            if (user.getFollowing().contains(post.getUsername())) {
                feed.add(post);
            }
        }
    }

    public User getUser() {
        return user;
    }

    public List<Post> getFeed() {
        return feed;
    }
}