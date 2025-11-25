package com.Rootale.fcm.web.dto;

import lombok.*;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationRequest implements PushNotificationDataSource{

    private String token;
    private String topic; // topic이 없다면 topic = "" 형태의 빈 문자열이 아니라 생략하여 null 형태로 받아야합니다.
    private String title;
    private String body;

    private Map<String, String> data;
    private Map<String, String> android;
    private Map<String, String> apns;
    private Map<String, String> webPush;

    @Override
    public Long getUserId() {
        return null; // userId가 없으므로 null을 반환
    }
}
