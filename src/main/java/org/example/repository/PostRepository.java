package org.example.repository;

import org.example.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Post> all() {
        return new ArrayList<>(posts.values()); // Теперь это будет работать
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = idCounter.incrementAndGet();
            post.setId(newId);
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}

//// Stub
//public class PostRepository {
//    public List<Post> all() {
//        return Collections.emptyList();
//    }
//
//    public Optional<Post> getById(long id) {
//        return Optional.empty();
//    }
//
//    public Post save(Post post) {
//        return post;
//    }
//
//    public void removeById(long id) {
//    }
//}