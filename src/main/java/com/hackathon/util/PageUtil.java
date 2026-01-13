package com.hackathon.util;

import com.hackathon.util.api.*;
import java.util.*;
import java.util.function.*;
import org.springframework.data.domain.*;

public class PageUtil {

    public static <E, I> SimplePageResponse<I> toSimplePageResponse(
            Page<E> find, Function<E, I> mapperFunc
    ) {
        Pageable pageable = find.getPageable();
        int pageNo = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        long numOfTotalElements = find.getTotalElements();
        boolean hasNext = find.hasNext();

        List<I> infos = find.map(mapperFunc).toList();

        return new SimplePageResponse<>(pageNo, pageSize, numOfTotalElements, hasNext, infos);
    }
}
