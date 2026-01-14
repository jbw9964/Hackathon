package com.hackathon.domain;

import java.io.*;
import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileTagJoinKey implements Serializable {

    private long fileId;
    private long tagId;
}
