package com.mjako18.chatbot.llm;

import com.mjako18.chatbot.service.ChatbotService;
import dev.langchain4j.agent.tool.Tool;

import java.util.List;

/**
 * Used to point to the methods the GPT can use to find information
 */
public class ChatbotServiceTools {
    private ChatbotService chatbotService;
    public ChatbotServiceTools(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }
    @Tool
    public List<String> findCustomer(String email, String correlationId) {
        return chatbotService.findCustomer(email, correlationId);
    }
}
