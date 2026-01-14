package com.hackathon.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@IdClass(FileTagJoinKey.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileTagJoin extends AuditingCreation {

    @Id
    @Column(name = "file_id")
    private Long fileId;

    @Id
    @Column(name = "tag_id")
    private Long tagId;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
