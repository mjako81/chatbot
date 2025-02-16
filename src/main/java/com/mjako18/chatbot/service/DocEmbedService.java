package com.mjako18.chatbot.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Path;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

public class DocEmbedService {
    private final EmbeddingStoreIngestor embeddingStoreIngestor;
    private final ResourceLoader resourceLoader;

    public DocEmbedService(EmbeddingStoreIngestor embeddingStoreIngestor, FileSystemResourceLoader resourceLoader) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
        this.resourceLoader = resourceLoader;
    }

    public void storeDoc(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource(filePath);
        Document doc = loadDocument(Path.of("/", resource.getFile().toPath().toString()), getParserForDocument(resource));
        embeddingStoreIngestor.ingest(doc);
    }

    private DocumentParser getParserForDocument(Resource resource) throws IOException {
        return resource.getFile().toPath().toString().endsWith(".pdf") ? new ApachePdfBoxDocumentParser() : new TextDocumentParser();
    }
}
