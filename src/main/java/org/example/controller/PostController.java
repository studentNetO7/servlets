package org.example.controller;

import com.google.gson.Gson;
import org.example.model.Post;
import org.example.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import org.example.exception.NotFoundException;
import java.util.List;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private final Gson gson;

    public PostController(PostService service) {
        this.service = service;
        this.gson = new Gson();  // Инициализация Gson один раз
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        List<Post> posts = service.all();
        writeJsonResponse(response, posts);
    }


    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            Post post = service.getById(id);
            writeJsonResponse(response, post);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Post not found with id: " + id);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        Post post = gson.fromJson(body, Post.class);
        Post savedPost = service.save(post);
        writeJsonResponse(response, savedPost);
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        try {
            service.removeById(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Post not found with id: " + id);
        }
    }

    // Вспомогательный метод для сериализации объекта в JSON и записи в ответ
    private void writeJsonResponse(HttpServletResponse response, Object data) throws IOException {
        String jsonResponse = gson.toJson(data);
        response.getWriter().write(jsonResponse);
    }
}

//public class PostController {
//    public static final String APPLICATION_JSON = "application/json";
//    private final PostService service;
//
//    public PostController(PostService service) {
//        this.service = service;
//    }
//
//    public void all(HttpServletResponse response) throws IOException {
//        response.setContentType(APPLICATION_JSON);
//        final var data = service.all();
//        final var gson = new Gson();
//        response.getWriter().print(gson.toJson(data));
//    }
//
//    public void getById(long id, HttpServletResponse response) {
//        // TODO: deserialize request & serialize response
//    }
//
//    public void save(Reader body, HttpServletResponse response) throws IOException {
//        response.setContentType(APPLICATION_JSON);
//        final var gson = new Gson();
//        final var post = gson.fromJson(body, Post.class);
//        final var data = service.save(post);
//        response.getWriter().print(gson.toJson(data));
//    }
//
//    public void removeById(long id, HttpServletResponse response) {
//        // TODO: deserialize request & serialize response
//    }
//}
