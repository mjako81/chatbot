package com.mjako18.chatbot.llm;

import dev.langchain4j.service.SystemMessage;

public interface ChatbotServiceAgent {
    @SystemMessage({
            "You are a customer service agent for an economy program."
            //"You MUST always check: ",
            //"UserID" // User must be from the same customer as the documents used for answering. RAG not implemented yet.
    })
    String chat(String userMessage);
}
