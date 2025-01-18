package org.example.config;

import org.example.controller.PostController;
import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Указываем, что это класс конфигурации
public class AppConfig {

    @Bean  // Определяем бин для PostRepository
    public PostRepository postRepository() {
        return new PostRepository();  // Создаем объект вручную
    }

    @Bean  // Определяем бин для PostService
    public PostService postService(PostRepository postRepository) {
        return new PostService(postRepository);  // Внедряем зависимость
    }

    @Bean  // Определяем бин для PostController
    public PostController postController(PostService postService) {
        return new PostController(postService);  // Внедряем зависимость
    }
}
