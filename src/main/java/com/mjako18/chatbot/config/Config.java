package com.mjako18.chatbot.config;

import com.mjako18.chatbot.llm.ChatbotServiceAgent;
import com.mjako18.chatbot.llm.ChatbotServiceTools;
import com.mjako18.chatbot.service.ChatbotService;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.jlama.JlamaChatModel;
import dev.langchain4j.model.jlama.JlamaStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
public class Config {
    @Bean
    ChatbotServiceAgent chatbotServiceAgent(StreamingChatLanguageModel streamingChatLanguageModel,
                                            ChatLanguageModel chatLanguageModel,
                                            ContentRetriever contentRetriever,
                                            ChatbotServiceTools chatbotService) {
         return AiServices.builder(ChatbotServiceAgent.class)
                 .chatLanguageModel(chatLanguageModel)
                 .streamingChatLanguageModel(streamingChatLanguageModel)
                 .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                 .tools(chatbotService)
                 .contentRetriever(contentRetriever)
                 .build();
    }

    @Bean
    ChatbotServiceTools chatbotServiceTools() {
        return new ChatbotServiceTools(new ChatbotService());
    }

    @Bean
    StreamingChatLanguageModel streamingModel() {
        return JlamaStreamingChatModel.builder()
                .modelName("tjake/Llama-3.2-1B-Instruct-JQ4")
                .temperature(0.0f)
                .build();
    }

    @Bean
    ChatLanguageModel chatLanguageModel() {
        return JlamaChatModel.builder()
                .modelName("tjake/Llama-3.2-1B-Instruct-JQ4")
                .temperature(0.0f)
                .build();
    }

    @Bean
    ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        int maxResult = 1;
        double minScore = 0.6;

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(maxResult)
                .minScore(minScore)
                .build();
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    EmbeddingStore<TextSegment> embeddingStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader) throws IOException {
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        return embeddingStore;
    }

    @Bean
    FileSystemResourceLoader resourceLoader() {
        return new FileSystemResourceLoader();
    }
}
