package org.smart4j.chapter2.util;

import org.apache.commons.lang3.StringUtils;

public final class StringUtil {

    public static boolean isEmpty(String string) {
        if (string != null) {
            string = string.trim();
        }
        return StringUtils.isEmpty(string);
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }
}
