package com.tk.outer.coffee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

//  // ========= for war =========
//  @Override
//  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//    return application.sources(Application.class);
//  }
//  // ===========================

  public static void main(String[] args) {
    log.info("Coffee Server Start...");
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    // 添加程序结束的钩子
    context.registerShutdownHook();
    context.start();
  }
}