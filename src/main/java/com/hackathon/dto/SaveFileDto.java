package com.hackathon.dto;

import com.hackathon.domain.*;
import com.hackathon.dto.request.*;

public record SaveFileDto(
        String fileOverview,
        String additionalInfo,
        Category category,
        FileType fileType,
        FileMediaType fileMediaType,
        String originalFileName,
        String savedFileName
) {

    public SaveFileDto(
            String originalFileName, String savedFileName,
            SaveFileRequest request
    ) {
        this(
                null, null,
                request.category(), request.fileType(), request.fileMediaType(),
                originalFileName, savedFileName
        );
    }

    public File toEntity() {
        return File.builder()
                .fileOverview(fileOverview)
                .additionalInfo(additionalInfo)
                .category(category)
                .fileType(fileType)
                .fileMediaType(fileMediaType)
                .originalFileName(originalFileName)
                .savedFileName(savedFileName)
                .build();
    }
}
