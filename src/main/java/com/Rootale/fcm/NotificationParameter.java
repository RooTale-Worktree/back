package com.Rootale.fcm;

public enum NotificationParameter {
    DEFAULT_SOUND("default"),
    DEFAULT_COLOR("#FFFF00"),
    DEFAULT_NOTIFICATION_CLICK("FLUTTER_NOTIFICATION_CLICK"),
    DEFAULT_ANDROID_ICON("ic_launcher"),
    ANDROID_CHANNEL_ID("default"),
    ;

    private String value;

    NotificationParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
