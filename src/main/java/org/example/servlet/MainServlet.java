package org.example.servlet;

import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;

   @Override
public void init() {
    var context = new AnnotationConfigApplicationContext(PostController.class, PostService.class, PostRepository.class);
    controller = context.getBean(PostController.class);  // Получаем бин PostController
}

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            // Убедись, что правильный путь
            if (method.equals("GET") && path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }

            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
                return;
            }

            if (method.equals("POST") && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                return;
            }

            if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // Возвращаем 404, если ничего не совпало
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}


//public class MainServlet extends HttpServlet {
//    private PostController controller;
//
//    @Override
//    public void init() {
//        final var repository = new PostRepository();
//        final var service = new PostService(repository);
//        controller = new PostController(service);
//    }
//
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) {
//        // если деплоились в root context, то достаточно этого
//        try {
//            final var path = req.getRequestURI();
//            final var method = req.getMethod();
//            // primitive routing
//            if (method.equals("GET") && path.equals("/api/posts")) {
//                controller.all(resp);
//                return;
//            }
//            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
//                // easy way
//                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
//                controller.getById(id, resp);
//                return;
//            }
//            if (method.equals("POST") && path.equals("/api/posts")) {
//                controller.save(req.getReader(), resp);
//                return;
//            }
//            if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
//                // easy way
//                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
//                controller.removeById(id, resp);
//                return;
//            }
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        }
//    }
//}
//
