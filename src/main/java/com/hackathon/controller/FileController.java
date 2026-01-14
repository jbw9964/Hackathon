package com.hackathon.controller;

import com.hackathon.domain.*;
import com.hackathon.dto.*;
import com.hackathon.dto.request.*;
import com.hackathon.service.*;
import com.hackathon.util.*;
import com.hackathon.util.api.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springdoc.core.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final FileIO fileIO;
    private final UuidProvider uuidProvider;

    @GetMapping
    public ApiResponse<SimplePageResponse<File>> getFiles(
            @Valid @ParameterObject @ModelAttribute
            SimplePageRequest pageRequest,
            @RequestParam(required = false) String cat,
            @RequestParam(required = false) String type
    ) {
        int pageNo = pageRequest.pageNum();
        int pageSize = pageRequest.pageSize();

        Category category = Category.resolveOrNull(cat);
        FileType fileType = FileType.resolveOrNull(type);

        SimplePageResponse<File> resp = fileService.getFiles(
                category, fileType, pageNo, pageSize
        );

        return ApiResponse.success(resp);
    }

    @GetMapping("/{file-id:\\d+}")
    public ResponseEntity<byte[]> getFile(
            @PathVariable("file-id") Long fileId
    ) {

        FileInfo info = fileService.getFileInfo(fileId);
        String fileName = info.savedFileName();
        MediaType fileMediaType = info.fileMediaType();

        byte[] file = fileIO.getFileData(fileName);

        return ResponseEntity.ok()
                .contentType(fileMediaType)
                .body(file);
    }

    @RequestBody(
            content = @Content(encoding = @Encoding(
                    name = "request", contentType = MediaType.APPLICATION_JSON_VALUE
            ))
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Long> saveFile(
            @RequestPart MultipartFile multipartFile,
            @RequestPart(name = "request") SaveFileRequest request
    ) {
        String newFileName = uuidProvider.getRandomStringUUID();
        String originalFilename = multipartFile.getOriginalFilename();
        String fileMediaType = multipartFile.getContentType();

        fileIO.transferMultipartFile(multipartFile, newFileName);

        log.info("content type : {}", multipartFile.getContentType());

        Long resp = fileService.recordFilePath(new SaveFileDto(
                originalFilename, newFileName,
                fileMediaType, request
        ));

        return ApiResponse.created(resp);
    }
}
