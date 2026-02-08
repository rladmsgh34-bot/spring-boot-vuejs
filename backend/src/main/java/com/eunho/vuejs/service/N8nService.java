package com.eunho.vuejs.service;

import java.util.Map;

public interface N8nService {
    
    /**
     * n8n ?岉伂?岆?办棎 ?办澊?半? ?勳啞?╇媹??
     * 
     * @param webhookUrl n8n ?鬼泤 URL
     * @param data ?勳啞???办澊??
     * @return ?戨嫷 ?办澊??
     */
    Map<String, Object> triggerWorkflow(String webhookUrl, Map<String, Object> data);
    
    /**
     * 旮半掣 ?れ爼??n8n ?岉伂?岆?办棎 ?办澊?半? ?勳啞?╇媹??
     * 
     * @param data ?勳啞???办澊??
     * @return ?戨嫷 ?办澊??
     */
    Map<String, Object> triggerDefaultWorkflow(Map<String, Object> data);
    
    /**
     * n8n?愳劀 氚涭? ?鬼泤 ?办澊?半? 觳橂Μ?╇媹??
     * 
     * @param eventType ?措菠???�??
     * @param data 氚涭? ?办澊??
     * @return 觳橂Μ 瓴瓣臣
     */
    Map<String, Object> handleWebhook(String eventType, Map<String, Object> data);
    
}

