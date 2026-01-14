package com.hackathon.service;

import com.hackathon.domain.*;
import com.hackathon.domain.exception.*;
import com.hackathon.dto.*;
import com.hackathon.repository.*;
import com.hackathon.util.*;
import com.hackathon.util.api.*;
import java.util.function.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.dao.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final UuidProvider uuidProvider;
    private final FileRepository fileRepository;

    public SimplePageResponse<File> getFiles(
            Category category, FileType fileType, int pageNo, int pageSize
    ) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<File> find;

        if (category == null && fileType == null) {
            find = fileRepository.findAllBy(pageable);
        } else if (fileType == null) {
            find = fileRepository.findByCategory(category, pageable);
        } else if (category == null) {
            find = fileRepository.findByFileType(fileType, pageable);
        } else {
            find = fileRepository.findByCategoryAndFileType(category, fileType, pageable);
        }

        return PageUtil.toSimplePageResponse(find, Function.identity());
    }

    public FileInfo getFileInfo(Long fileId) {
        File find = fileRepository.findById(fileId)
                .orElseThrow(FileNotFoundException::new);

        return new FileInfo(find.getId(), find.getFileMediaType(), find.getSavedFileName());
    }

    @Transactional
    public Long recordFilePath(SaveFileDto saveFileDto) {
        File newFile = saveFileDto.toEntity();

        try {
            return fileRepository.save(newFile).getId();
        } catch (DataIntegrityViolationException e) {
            String errMsg = String.format(
                    "Failed to record entity due to ex : %s",
                    e.getClass().getSimpleName()
            );

            FailedToRecordFileException ex = new FailedToRecordFileException(errMsg, e);
            log.warn(errMsg, ex);
            throw ex;
        }
    }

}
