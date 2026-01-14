package com.hackathon.dto;

import com.hackathon.domain.*;

public record FileInfo(
        Long fileId,
        FileMediaType fileMediaType,
        String savedFileName
) {

}
