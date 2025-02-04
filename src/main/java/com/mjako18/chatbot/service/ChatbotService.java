package com.mjako18.chatbot.service;

import opennlp.tools.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Used with RAG to define methods for the GPT to find information
 */
public class ChatbotService {
    public ChatbotService() {}

    public List<String> findCustomer(String email, String correlationId) {
        List<String> ls = new ArrayList<>();
        ls.add("Test");
        ls.add("Joke");
        return ls;
    }
}
