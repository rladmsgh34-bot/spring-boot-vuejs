package com.eunho.vuejs.controller.rest;

import com.eunho.vuejs.dto.N8nWebhookRequest;
import com.eunho.vuejs.dto.N8nWebhookResponse;
import com.eunho.vuejs.service.N8nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * n8n ?Œí¬?Œë¡œ???°ë™???„í•œ REST API Controller
 */
@RestController
@RequestMapping("/api/n8n")
public class N8nWebhookController {

    private static final Logger log = LoggerFactory.getLogger(N8nWebhookController.class);
    private final N8nService n8nService;

    @Autowired
    public N8nWebhookController(N8nService n8nService) {
        this.n8nService = n8nService;
    }

    /**
     * n8n?¼ë¡œë¶€???¹í›…??ë°›ëŠ” ?”ë“œ?¬ì¸??
     *
     * @param request ?¹í›… ?”ì²­ ?°ì´??
     * @return ì²˜ë¦¬ ê²°ê³¼
     */
    @PostMapping("/webhook")
    public ResponseEntity<N8nWebhookResponse> receiveWebhook(@RequestBody N8nWebhookRequest request) {
        log.info("Received webhook from n8n - Workflow ID: {}, Event Type: {}",
                 request.getWorkflowId(), request.getEventType());

        try {
            Map<String, Object> result = n8nService.handleWebhook(
                request.getEventType(),
                request.getData()
            );

            N8nWebhookResponse response = N8nWebhookResponse.success(
                "Webhook processed successfully",
                result
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error processing n8n webhook: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(N8nWebhookResponse.error("Failed to process webhook: " + e.getMessage()));
        }
    }

    /**
     * n8n ?Œí¬?Œë¡œ?°ë? ?¸ë¦¬ê±°í•˜???”ë“œ?¬ì¸??
     *
     * @param data ?Œí¬?Œë¡œ?°ì— ?„ë‹¬???°ì´??
     * @return ?¤í–‰ ê²°ê³¼
     */
    @PostMapping("/trigger")
    public ResponseEntity<Map<String, Object>> triggerWorkflow(@RequestBody Map<String, Object> data) {
        log.info("Triggering n8n workflow with data: {}", data);

        try {
            Map<String, Object> result = n8nService.triggerDefaultWorkflow(data);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("Error triggering n8n workflow: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to trigger workflow: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * ì»¤ìŠ¤?€ ?¹í›… URLë¡??Œí¬?Œë¡œ?°ë? ?¸ë¦¬ê±°í•˜???”ë“œ?¬ì¸??
     *
     * @param webhookUrl n8n ?¹í›… URL
     * @param data ?Œí¬?Œë¡œ?°ì— ?„ë‹¬???°ì´??
     * @return ?¤í–‰ ê²°ê³¼
     */
    @PostMapping("/trigger-custom")
    public ResponseEntity<Map<String, Object>> triggerCustomWorkflow(
            @RequestParam String webhookUrl,
            @RequestBody Map<String, Object> data) {

        log.info("Triggering custom n8n workflow at: {}", webhookUrl);

        try {
            Map<String, Object> result = n8nService.triggerWorkflow(webhookUrl, data);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("Error triggering custom n8n workflow: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to trigger workflow: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * n8n ?°ë™ ?íƒœë¥??•ì¸?˜ëŠ” ?¬ìŠ¤ì²´í¬ ?”ë“œ?¬ì¸??
     *
     * @return ?íƒœ ?•ë³´
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        log.info("n8n integration health check");

        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "n8n-integration");
        health.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(health);
    }

}

