package com.passion.teampassiontrelloproject;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class TeamPassionTrelloProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamPassionTrelloProjectApplication.class, args);
    }
    @PostConstruct
    public void init() {
        //timezone 설정
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
