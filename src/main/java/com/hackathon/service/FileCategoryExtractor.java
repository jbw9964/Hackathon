package com.hackathon.service;

import com.hackathon.domain.Category;
import com.hackathon.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.core.io.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileCategoryExtractor {
    private final ChatClient chatClient;
    private final FileIO fileIO;

    public List<Category> extractCategory(File file) {
        return chatClient.prompt()
                .system(s -> s.text("""
                        You are an expert at classifying documents into categories.
                        Given the content of a document, identify the most appropriate categories from the following list: {categories}.
                        """)
                        .param("categories", Category.values())
                )
                .user(u -> u.text("""
                        Here is the content of the document
                        Please provide the categories that best fit this document.
                        """)
                        .media(new Media(MimeType.valueOf("text/plain"), getFileResource(file)))
                )
                .call()
                .entity(new ParameterizedTypeReference<List<Category>>() {});
    }

    private Resource getFileResource(File file) {
        return new ByteArrayResource(fileIO.getFileData(file.getSavedFileName()));
    }

}
