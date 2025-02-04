package com.mjako18.chatbot;

import com.mjako18.chatbot.llm.ChatbotServiceAgent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private ChatbotServiceAgent agent;

    public ChatController(ChatbotServiceAgent agent) {
        this.agent = agent;
    }

    @GetMapping("/chat")
    public String getResponse(@RequestParam String question) {
        return agent.chat(question);
    }
}
