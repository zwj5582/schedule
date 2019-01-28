/*
 *  Created by ZhongWenjie on 2019-01-28 11:21
 */

package org.zhong.zschedule.core.executor;

import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.core.task.QueryTask;

import java.util.concurrent.TimeUnit;

public final class QueryTaskExecutors {

    private QueryTaskExecutors() {
    }

    public static QueryTaskExecutor build(QueryTask queryTask, ScheduledExecutorServiceLoader loader, long delay, TimeUnit timeUnit) {
        return new DefaultQueryTaskExecutor(delay, timeUnit, loader, queryTask);

    }

    public static QueryTaskExecutor build(QueryTask queryTask, ScheduledExecutorServiceLoader loader, long delay, TimeUnit timeUnit, long timeout) {
        return new DefaultQueryTaskExecutor(delay, timeUnit, timeout, loader, queryTask);
    }

}
