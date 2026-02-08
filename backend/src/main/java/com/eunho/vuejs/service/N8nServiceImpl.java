package com.eunho.vuejs.service;

import com.eunho.vuejs.config.N8nConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class N8nServiceImpl implements N8nService {

    private static final Logger log = LoggerFactory.getLogger(N8nServiceImpl.class);
    private final N8nConfig n8nConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public N8nServiceImpl(N8nConfig n8nConfig) {
        this.n8nConfig = n8nConfig;
    }

    @Override
    public Map<String, Object> triggerWorkflow(String webhookUrl, Map<String, Object> data) {
        if (!n8nConfig.isEnabled()) {
            log.warn("n8n integration is disabled");
            return createErrorResponse("n8n integration is disabled");
        }

        try {
            log.info("Triggering n8n workflow at: {}", webhookUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(data, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                webhookUrl,
                HttpMethod.POST,
                request,
                Map.class
            );

            log.info("n8n workflow triggered successfully. Status: {}", response.getStatusCode());

            return createSuccessResponse("Workflow triggered successfully", response.getBody());

        } catch (Exception e) {
            log.error("Error triggering n8n workflow: {}", e.getMessage(), e);
            return createErrorResponse("Failed to trigger workflow: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> triggerDefaultWorkflow(Map<String, Object> data) {
        String webhookUrl = n8nConfig.getWebhookUrl();

        if (webhookUrl == null || webhookUrl.isEmpty()) {
            log.error("Default n8n webhook URL is not configured");
            return createErrorResponse("Default webhook URL is not configured");
        }

        return triggerWorkflow(webhookUrl, data);
    }

    @Override
    public Map<String, Object> handleWebhook(String eventType, Map<String, Object> data) {
        log.info("Handling n8n webhook - Event Type: {}, Data: {}", eventType, data);

        try {
            // ?¥Î≤§???Ä?ÖÏóê ?∞Îùº ?§Î•∏ Ï≤òÎ¶¨Î•??òÌñâ?????àÏäµ?àÎã§
            switch (eventType) {
                case "user.created":
                    log.info("Processing user.created event");
                    // ?¨Ïö©???ùÏÑ± ?¥Î≤§??Ï≤òÎ¶¨
                    break;

                case "user.updated":
                    log.info("Processing user.updated event");
                    // ?¨Ïö©???ÖÎç∞?¥Ìä∏ ?¥Î≤§??Ï≤òÎ¶¨
                    break;

                case "notification":
                    log.info("Processing notification event");
                    // ?åÎ¶º ?¥Î≤§??Ï≤òÎ¶¨
                    break;

                default:
                    log.info("Processing generic event: {}", eventType);
                    break;
            }

            return createSuccessResponse("Webhook processed successfully", data);

        } catch (Exception e) {
            log.error("Error processing webhook: {}", e.getMessage(), e);
            return createErrorResponse("Failed to process webhook: " + e.getMessage());
        }
    }

    private Map<String, Object> createSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

}

