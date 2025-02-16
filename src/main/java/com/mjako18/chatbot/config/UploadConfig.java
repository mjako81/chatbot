package com.mjako18.chatbot.config;

import com.github.tjake.jlama.model.llama.LlamaTokenizer;
import com.mjako18.chatbot.service.DocEmbedService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Configuration
public class UploadConfig {
    private Path tokenizerPath;

    @Bean
    EmbeddingStoreIngestor embeddingStoreIngestor(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore) {
        tokenizerPath = Paths.get("src/main/resources");
        DocumentSplitter docSplitter = DocumentSplitters.recursive(100, 0);
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(docSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
    }

    @Bean
    DocEmbedService docEmbedService(EmbeddingStoreIngestor embeddingStoreIngestor, FileSystemResourceLoader resourceLoader) {
        return new DocEmbedService(embeddingStoreIngestor, resourceLoader);
    }

    private DocumentParser getParserForDocument(Resource resource) throws IOException {
        return resource.getFile().toPath().toString().endsWith(".pdf") ? new ApachePdfBoxDocumentParser() : new TextDocumentParser();
    }

    private void loadEmbeddingForDocument(Resource resource, EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore) throws IOException {
        Document doc = loadDocument(resource.getFile().toPath(), getParserForDocument(resource));

        DocumentSplitter docSplitter = DocumentSplitters.recursive(100, 0, (Tokenizer) new LlamaTokenizer(tokenizerPath));
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(docSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(doc);
    }
}
