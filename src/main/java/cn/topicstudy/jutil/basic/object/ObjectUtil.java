package cn.topicstudy.jutil.basic.object;

import cn.topicstudy.jutil.basic.text.StringUtil;

public class ObjectUtil {
    public static boolean isBlank(Object v) {
        if (v instanceof String) return StringUtil.isBlank((String) v);
        return v == null;
    }

}
