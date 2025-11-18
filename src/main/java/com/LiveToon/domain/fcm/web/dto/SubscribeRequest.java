package com.LiveToon.domain.fcm.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeRequest {
    private Long userId;
    private String topic;
}
