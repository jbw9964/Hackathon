package com.hackathon.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseTimeEntity implements EntityId<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileOverview;

    private String additionalInfo;

    @Enumerated(EnumType.STRING)
    private Category category = Category.UNCLASSIFIED;

    private String fileType;

    @Column(updatable = false)
    private String originalFileName;

    @Column(nullable = false, unique = true)
    private String savedFileName;

    @Builder
    public File(
            String fileOverview, String additionalInfo, Category category, String fileType,
            String originalFileName, String savedFileName
    ) {
        this.fileOverview = fileOverview;
        this.additionalInfo = additionalInfo;
        this.category = category;
        this.fileType = fileType;
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
    }
}
