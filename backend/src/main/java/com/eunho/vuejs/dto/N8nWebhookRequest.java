package com.eunho.vuejs.dto;

import java.util.Map;

public class N8nWebhookRequest {
    
    private String workflowId;
    private String eventType;
    private Map<String, Object> data;
    private Long timestamp;

    public N8nWebhookRequest() {
    }

    public N8nWebhookRequest(String workflowId, String eventType, Map<String, Object> data, Long timestamp) {
        this.workflowId = workflowId;
        this.eventType = eventType;
        this.data = data;
        this.timestamp = timestamp;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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
}

