package com.hackathon.dto.request;

import com.hackathon.domain.*;

public record SaveFileRequest(
        Category category,
        FileType fileType,
        FileMediaType fileMediaType
) {

}
