/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.core.executor;

import com.google.common.collect.Maps;
import org.zhong.zschedule.core.common.MonitorObject;
import org.zhong.zschedule.core.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.core.task.QueryTask;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DefaultQueryTaskExecutor implements QueryTaskExecutor {

    private QueryTaskExecutorConfig queryTaskExecutorConfig;
    private ScheduledExecutorServiceLoader scheduledExecutorServiceLoader;
    private QueryTask queryTask;

    private Map<Object, Future> futureMap = Maps.newConcurrentMap();

    public DefaultQueryTaskExecutor(
            QueryTaskExecutorConfig queryTaskExecutorConfig,
            ScheduledExecutorServiceLoader scheduledExecutorServiceLoader,
            QueryTask queryTask) {
        this.queryTaskExecutorConfig = queryTaskExecutorConfig;
        this.scheduledExecutorServiceLoader = scheduledExecutorServiceLoader;
        this.queryTask = queryTask;
    }

    public DefaultQueryTaskExecutor(
            long delay, TimeUnit timeUnit,
            ScheduledExecutorServiceLoader scheduledExecutorServiceLoader,
            QueryTask queryTask) {
        this.queryTaskExecutorConfig = new QueryTaskExecutorConfig() {
            @Override
            public Long getDelay() {
                return delay;
            }

            @Override
            public Long getTimeout() {
                return null;
            }

            @Override
            public Boolean getNeedTimeout() {
                return false;
            }

            @Override
            public TimeUnit getTimeUnit() {
                return timeUnit;
            }
        };
        this.scheduledExecutorServiceLoader = scheduledExecutorServiceLoader;
        this.queryTask = queryTask;
    }

    public DefaultQueryTaskExecutor(
            long delay, TimeUnit timeUnit, long timeout,
            ScheduledExecutorServiceLoader scheduledExecutorServiceLoader,
            QueryTask queryTask) {
        this.queryTaskExecutorConfig = new QueryTaskExecutorConfig() {
            @Override
            public Long getDelay() {
                return delay;
            }

            @Override
            public Long getTimeout() {
                return timeout;
            }

            @Override
            public Boolean getNeedTimeout() {
                return true;
            }

            @Override
            public TimeUnit getTimeUnit() {
                return timeUnit;
            }
        };
        this.scheduledExecutorServiceLoader = scheduledExecutorServiceLoader;
        this.queryTask = queryTask;
    }

    @Override
    public void submit(Map<String, Object> queryParams) {
        final ScheduledExecutorService executorService =
                scheduledExecutorServiceLoader.getScheduledExecutorService();

        MonitorObject monitorObject = new MonitorObject();

        QueryTaskAware queryTaskAware =
                (queryTaskExecutorConfig.getNeedTimeout()) ?
                        new QueryTaskAware
                                (futureMap, monitorObject, queryTask, queryParams, queryTaskExecutorConfig.getTimeout(), queryTaskExecutorConfig.getTimeUnit()) :
                        new QueryTaskAware
                                (futureMap, monitorObject, queryTask, queryParams);

        ScheduledFuture<?> scheduledFuture = executorService.scheduleWithFixedDelay(
                queryTaskAware,
                0L,
                queryTaskExecutorConfig.getDelay(),
                queryTaskExecutorConfig.getTimeUnit());

        synchronized (monitorObject.getSource()) {
            futureMap.put(monitorObject.getSource(), scheduledFuture);
            monitorObject.getSource().notifyAll();
        }


    }

}
