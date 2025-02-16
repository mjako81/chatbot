package com.mjako18.chatbot;

import com.mjako18.chatbot.service.DocEmbedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;

@RestController()
@CrossOrigin(origins = "*")
public class UploadController {
    private final DocEmbedService docEmbedService;

    @Autowired
    public UploadController(DocEmbedService docEmbedService) {
        this.docEmbedService = docEmbedService;
    }

    @PostMapping(value = "/upload")
    public StatusResponse uploadDoc(@RequestPart("file")FilePart file) {
        String filename = file.filename();

        String uploadDir = "tmp";
        File newFile = new File(uploadDir, filename);

        Mono<Void> asyncStoreDoc = Mono.fromRunnable(() -> {
            try {
                docEmbedService.storeDoc(newFile.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        file.transferTo(newFile.toPath()).then(asyncStoreDoc).subscribe();

        return new StatusResponse("OK", "Successful");
    }
}

record StatusResponse(String status, String message) {}