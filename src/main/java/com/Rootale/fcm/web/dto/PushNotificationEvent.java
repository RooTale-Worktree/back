package com.Rootale.fcm.web.dto;
import lombok.*;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationEvent implements PushNotificationDataSource {
    private Long userId;
    private String title;
    private String body;

    private Map<String, String> data;
    private Map<String, String> android;
    private Map<String, String> apns;
    private Map<String, String> webPush;

    @Override
    public String getTopic() {
        return null; // topic이 없으므로 null을 반환
    }
}

