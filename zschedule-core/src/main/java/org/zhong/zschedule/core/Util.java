package org.zhong.zschedule.core;

import com.google.common.collect.ImmutableMap;
import org.zhong.zschedule.core.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.core.executor.CronQueryTaskExecutor;
import org.zhong.zschedule.core.executor.QueryTaskExecutor;
import org.zhong.zschedule.core.executor.QueryTaskExecutors;
import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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