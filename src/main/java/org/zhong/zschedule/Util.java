package org.zhong.zschedule;

import java.util.*;

public class Util {

    public static boolean valid(Boolean bool) {
        if (bool != null && bool) {
            return true;
        }
        return false;
    }

    public static boolean valid(String string) {
        if (string != null && !"".equals(string.trim())) {
            return true;
        }
        return false;
    }

    public static boolean valid(Integer integer) {
        if (integer != null && integer > 0) {
            return true;
        }
        return false;
    }

    public static boolean valid(Object[] objects) {
        if (objects != null && objects.length > 0) {
            return true;
        }
        return false;
    }

    public static boolean valid(Collection<Object> collection) {
        if (collection != null && !collection.isEmpty()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static boolean valid(Object object) {
        if (object != null) {
            if (object instanceof Object[]) {
                return valid((Object[]) object);
            } else if (object instanceof Collection<?>) {
                return valid((Collection<Object>) object);
            } else if (object instanceof String) {
                return valid((String) object);
            } else if (object instanceof Integer) {
                return valid((Integer) object);
            } else if (object instanceof Boolean) {
                return valid((Boolean) object);
            }
            return true;
        }
        return false;
    }
}