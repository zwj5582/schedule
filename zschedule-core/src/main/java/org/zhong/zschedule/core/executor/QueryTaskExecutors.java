/*
 *  Created by ZhongWenjie on 2019-01-28 11:21
 */

package org.zhong.zschedule.core.executor;

import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.core.task.QueryTask;
import org.zhong.zschedule.core.task.VariableQueryTask;

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

    public static QueryTaskExecutor build(VariableQueryTask variableQueryTask, ScheduledExecutorServiceLoader loader) {
        return new DefaultQueryTaskVariableExecutor(loader, variableQueryTask);
    }

    public static QueryTaskExecutor build(VariableQueryTask variableQueryTask, ScheduledExecutorServiceLoader loader, TimeUnit timeUnit, long timeout) {
        return new DefaultQueryTaskVariableExecutor(timeUnit, timeout, loader, variableQueryTask);
    }

    public static QueryTaskExecutor build(String cron, QueryTask queryTask, ScheduledExecutorServiceLoader loader) {
        return new CronQueryTaskExecutor(cron, loader, queryTask);
    }

    public static QueryTaskExecutor build(String cron, TimeUnit timeUnit, long timeout, QueryTask queryTask, ScheduledExecutorServiceLoader loader) {
        return new CronQueryTaskExecutor(cron, timeout, timeUnit, loader, queryTask);
    }

}
