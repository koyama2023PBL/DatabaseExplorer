package jp.ac.databaseexplorer.api.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000") // Reactが稼働しているポートを許可します
        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS") // 許可するHTTPメソッドを指定します
        .allowCredentials(true);
  }
}