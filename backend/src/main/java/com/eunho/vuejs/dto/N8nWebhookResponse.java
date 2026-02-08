package com.eunho.vuejs.dto;

import java.util.Map;

public class N8nWebhookResponse {
    
    private boolean success;
    private String message;
    private Map<String, Object> data;
    private Long timestamp;

    public N8nWebhookResponse() {
    }

    public N8nWebhookResponse(boolean success, String message, Map<String, Object> data, Long timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public static N8nWebhookResponse success(String message, Map<String, Object> data) {
        return new N8nWebhookResponse(true, message, data, System.currentTimeMillis());
    }
    
    public static N8nWebhookResponse error(String message) {
        return new N8nWebhookResponse(false, message, null, System.currentTimeMillis());
    }

}

