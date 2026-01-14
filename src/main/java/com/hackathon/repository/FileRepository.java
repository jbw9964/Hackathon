package com.hackathon.repository;

import com.hackathon.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface FileRepository extends JpaRepository<File, Long> {

    Page<File> findAllBy(Pageable pageable);

    Page<File> findByCategory(Category category, Pageable pageable);

    Page<File> findByFileType(FileType fileType, Pageable pageable);

    Page<File> findByCategoryAndFileType(Category category, FileType fileType, Pageable pageable);
}
