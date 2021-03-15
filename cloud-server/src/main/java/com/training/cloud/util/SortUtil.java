package com.training.cloud.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

public class SortUtil {

    private static final String DELIMITER = ",";

    public static Sort createSort(final String sort, final String defaultProperty) {
        if (StringUtils.isNotEmpty(sort) && sort.split(DELIMITER).length > 1) {
            final String properties = StringUtils.substringBeforeLast(sort, DELIMITER);
            final String direction = StringUtils.substringAfterLast(sort, DELIMITER);
            return Sort.by(Sort.Direction.fromString(direction), properties.split(DELIMITER));
        }
        return Sort.by(Sort.Direction.ASC, defaultProperty);
    }
}
