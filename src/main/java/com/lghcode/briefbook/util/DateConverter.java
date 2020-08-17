package com.lghcode.briefbook.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日期转换类(String->Date 参数注入)
 * User: lghcode
 * Date: 2020/8/17
 */
public class DateConverter implements Converter<String, Date> {
    private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);
    private static final List<String> formarts = new ArrayList<>(4);

    static {
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        try {
            if (source.matches("^\\d{4}-\\d{1,2}$")) {
                return DateUtils.parseDate(source, formarts.get(0));
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                return DateUtils.parseDate(source, formarts.get(1));
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
                return DateUtils.parseDate(source, formarts.get(2));
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                return DateUtils.parseDate(source, formarts.get(3));
            }
        } catch (Exception e) {
            logger.error("date convert error!", e);
        }
        throw new IllegalArgumentException(String.format("Invalid date value '%s'", source));
    }
}