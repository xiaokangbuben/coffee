package com.tk.outer.coffee.configuration;

import com.tk.outer.coffee.Application;
import com.tk.outer.coffee.util.ConfigUtil;
import java.io.File;
import java.io.FileNotFoundException;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

  public static String getImageUploadPath() {
    // 这个路径是不带"file:"开头，正常使用的路径，且上传路径是大图所在路径
    ApplicationHome home = new ApplicationHome(Application.class);
    return home.getDir().getPath() + File.separator + "logistics" + File.separator + ConfigUtil.IMAGE_UPLOAD_FOLDER + File.separator + ConfigUtil.IMAGE_BIG_FOLDER + File.separator;
  }

  @Bean
  public WebMvcConfigurer resourceConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File file = null;
        try {
          file = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
        assert file != null;
        // 这个路径是带"file:"开头的
        String path = file.getParentFile().getParentFile().getParent() + File.separator + "logistics" + File.separator + ConfigUtil.IMAGE_UPLOAD_FOLDER + File.separator;
        registry.addResourceHandler("/upload/**").addResourceLocations(path);
        registry.addResourceHandler("/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
      }
    };
  }
}
