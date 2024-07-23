package org.gyh.forestry.enums;

import java.util.function.Function;

/**
 * create by GYH on 2024/7/22
 */
public enum DictTypeEnum {
    Num(str -> {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }),
    Str(str -> true),
    Arr(str -> str.startsWith("[") && str.endsWith("]")),
    Bol(str -> str.equals("true") || str.equals("false")),
;
    public final Function<String, Boolean> parse;

    DictTypeEnum(Function<String, Boolean> parse) {
        this.parse = parse;
    }

    public boolean parse(String str) {
        return parse.apply(str);
    }
}
