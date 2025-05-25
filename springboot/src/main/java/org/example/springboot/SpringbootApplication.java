package org.example.springboot;

import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
@MapperScan("org.example.springboot.mapper")
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void checkXml() {
        try {
            Resource resource = resourceLoader.getResource("classpath:mapper/TaskMapper.xml");
            System.out.println("Resource exists: " + resource.exists());
            System.out.println("Resource path: " + resource.getURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
