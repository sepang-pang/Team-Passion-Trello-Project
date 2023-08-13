package com.passion.teampassiontrelloproject.common.slacknotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SlackService {

    @Value("${SLACK_WEBHOOK.URL}")
    private String slackWebhookUrl;

    public void sendSlackNotification(String message) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSON 형식으로 메시지 구성
        String requestBody = "{\"text\": \"" + message + "\"}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        restTemplate.exchange(slackWebhookUrl, HttpMethod.POST, entity, String.class);
    }
}
