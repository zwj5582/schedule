/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.executor;

import org.zhong.zschedule.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.task.QueryTask;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultQueryTaskExecutor implements QueryTaskExecutor {

    private QueryTaskExecutorConfig queryTaskExecutorConfig;
    private ScheduledExecutorServiceLoader scheduledExecutorServiceLoader;
    private QueryTask queryTask;

    public DefaultQueryTaskExecutor(
            QueryTaskExecutorConfig queryTaskExecutorConfig,
            ScheduledExecutorServiceLoader scheduledExecutorServiceLoader,
            QueryTask queryTask) {
        this.queryTaskExecutorConfig = queryTaskExecutorConfig;
        this.scheduledExecutorServiceLoader = scheduledExecutorServiceLoader;
        this.queryTask = queryTask;
    }

    @Override
    public void submit(Map<String, Object> queryParams) {
        long nowTimeMillis = System.currentTimeMillis();
        final ScheduledExecutorService executorService =
                scheduledExecutorServiceLoader.getScheduledExecutorService();
        executorService.scheduleWithFixedDelay(
                (queryTaskExecutorConfig.getNeedTimeout())
                        ? new QueryTaskAware(
                                executorService,
                                queryTask,
                                queryParams,
                                nowTimeMillis
                                        + TimeUnit.MILLISECONDS.convert(queryTaskExecutorConfig.getTimeout(), queryTaskExecutorConfig.getTimeUnit()))
                        : new QueryTaskAware(executorService, queryTask, queryParams),
                0L,
                queryTaskExecutorConfig.getDelay(),
                queryTaskExecutorConfig.getTimeUnit());
    }

}
