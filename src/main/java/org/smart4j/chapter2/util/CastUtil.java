package org.smart4j.chapter2.util;

public final class CastUtil {

    /*转为string类型*/
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }
    /*转为string类型（提供默认值）*/
    public static String castString(Object obj,String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /*转为Double类型*/
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    /*转为Double类型（提供默认值）*/
    public static double castDouble(Object obj ,double defauleValue) {
        double value = defauleValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    value = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    value = defauleValue;
                }
            }
        }
        return value;
    }

    /*转为LOng类型*/
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /*转为LOng类型（提供默认值）*/
    public static long castLong(Object obj ,long defauleValue) {
        long value = defauleValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    value = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    value = defauleValue;
                }
            }
        }
        return value;
    }

    /*转为int类型*/
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /*转为int类型（提供默认值）*/
    public static int castInt(Object obj ,int defauleValue) {
        int value = defauleValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    value = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    value = defauleValue;
                }
            }
        }
        return value;
    }

    /*转为boolean类型*/
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /*转为boolean类型*/
    public static boolean castBoolean(Object obj ,boolean defauleValue) {
        boolean value = defauleValue;
        if (obj != null) {
            value = Boolean.parseBoolean(castString(obj));
        }
        return value;
    }
}
