/*
 *  Created by ZhongWenjie on 2019-01-28 13:59
 */

package org.zhong.zschedule.core.executor;

import org.zhong.zschedule.core.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.core.task.VariableQueryTask;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultQueryTaskVariableExecutor implements QueryTaskExecutor {

    private QueryTaskExecutorConfig queryTaskExecutorConfig;
    private ScheduledExecutorServiceLoader scheduledExecutorServiceLoader;
    private VariableQueryTask variableQueryTask;

    public DefaultQueryTaskVariableExecutor(QueryTaskExecutorConfig queryTaskExecutorConfig, ScheduledExecutorServiceLoader scheduledExecutorServiceLoader, VariableQueryTask variableQueryTask) {
        this.queryTaskExecutorConfig = queryTaskExecutorConfig;
        this.scheduledExecutorServiceLoader = scheduledExecutorServiceLoader;
        this.variableQueryTask = variableQueryTask;
    }

    DefaultQueryTaskVariableExecutor(QueryTaskExecutorConfig queryTaskExecutorConfig, ScheduledExecutorServiceLoader scheduledExecutorServiceLoader) {
        this.queryTaskExecutorConfig = queryTaskExecutorConfig;
        this.scheduledExecutorServiceLoader = scheduledExecutorServiceLoader;
    }

    public DefaultQueryTaskVariableExecutor(ScheduledExecutorServiceLoader scheduledExecutorServiceLoader, VariableQueryTask variableQueryTask) {
        this.queryTaskExecutorConfig = new QueryTaskExecutorConfig() {
            @Override
            public Long getDelay() {
                return null;
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
                return null;
            }
        };
        this.scheduledExecutorServiceLoader = scheduledExecutorServiceLoader;
        this.variableQueryTask = variableQueryTask;
    }

    public DefaultQueryTaskVariableExecutor(TimeUnit timeUnit, long timeout, ScheduledExecutorServiceLoader scheduledExecutorServiceLoader, VariableQueryTask variableQueryTask) {
        this.queryTaskExecutorConfig = new QueryTaskExecutorConfig() {
            @Override
            public Long getDelay() {
                return null;
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
        this.variableQueryTask = variableQueryTask;
    }

    void setVariableQueryTask(VariableQueryTask variableQueryTask) {
        this.variableQueryTask = variableQueryTask;
    }

    @Override
    public void submit(Map<String, Object> queryParams) {
        ScheduledExecutorService scheduledExecutorService = scheduledExecutorServiceLoader.getScheduledExecutorService();

        VariableQueryTaskAware variableQueryTaskAware =
                (queryTaskExecutorConfig.getNeedTimeout()) ?
                        new VariableQueryTaskAware(
                                scheduledExecutorService,
                                variableQueryTask,
                                queryParams,
                                queryTaskExecutorConfig.getTimeUnit(),
                                queryTaskExecutorConfig.getTimeout()
                        ) :
                        new VariableQueryTaskAware(
                                scheduledExecutorService,
                                variableQueryTask,
                                queryParams
                        );

        scheduledExecutorService.submit(variableQueryTaskAware);

    }
}
