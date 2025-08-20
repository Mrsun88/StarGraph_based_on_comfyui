package cn.jiege.starGraph;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan("cn.jiege.starGraph.**.mapper")
@EnableScheduling

public class StarGraphApp {

    public static void main(String[] args) {
        SpringApplication.run(StarGraphApp.class, args);
    }
}
