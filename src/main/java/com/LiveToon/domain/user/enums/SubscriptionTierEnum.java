package com.LiveToon.domain.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum SubscriptionTierEnum{
    FREE("free"),

    PREMIUM("premium"),

    PRO("pro");

    @JsonValue
    private final String value;

    SubscriptionTierEnum(String value) {
        this.value = value;
    }

    @JsonCreator // Json -> Object, 역직렬화 수행하는 메서드
    public static SubscriptionTierEnum from(String param) {
        for (SubscriptionTierEnum SubscriptionTierEnum : SubscriptionTierEnum.values()) {
            if (SubscriptionTierEnum.getValue().equals(param)) {
                return SubscriptionTierEnum;
            }
        }
        log.error("SubscriptionTierEnum.from() exception occur param: {}", param);
        return null;
    }

    public static SubscriptionTierEnum fromValue(String value) {
        for (SubscriptionTierEnum tier : SubscriptionTierEnum.values()) {
            if (tier.value.equals(value)) {
                return tier;
            }
        }
        throw new IllegalArgumentException("Unknown tier: " + value);
    }
}
