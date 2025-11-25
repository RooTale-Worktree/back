package com.Rootale.fcm.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

public interface PushNotificationDataSource {
    Long getUserId();
    String getTitle();
    String getBody();
    String getTopic();
    Map<String, String> getData();
    Map<String, String> getAndroid();
    Map<String, String> getApns();
    Map<String, String> getWebPush();

}
