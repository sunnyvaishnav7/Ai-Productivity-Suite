package com.ai.gemini_chat;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AIController {

    private static final Logger logger = LoggerFactory.getLogger(AIController.class);

    private final QnAService qnAService;

    // Constructor injection
    public AIController(QnAService qnAService) {
        this.qnAService = qnAService;
        logger.info("AIController created with QnAService: {}", qnAService != null ? "SUCCESS" : "NULL");
    }

    @PostMapping(value = "/ask", consumes = "application/json")
    public ResponseEntity<String> askQuestion(@RequestBody Map<String, String> payload) {
        try {
            String question = payload.get("question");
            logger.info("Received question: {}", question);
            
            if (question == null || question.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Question cannot be empty");
            }
            
            if (qnAService == null) {
                logger.error("QnAService is null!");
                return ResponseEntity.internalServerError().body("Service not available");
            }
            
            String answer = qnAService.getAnswer(question);
            logger.info("Generated answer successfully");
            return ResponseEntity.ok(answer);
            
        } catch (Exception e) {
            logger.error("Error processing request", e);
            return ResponseEntity.internalServerError()
                .body("Error processing request: " + e.getMessage());
        }
    }
}