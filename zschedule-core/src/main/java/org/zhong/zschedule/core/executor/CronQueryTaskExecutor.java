/*
 *  Created by ZhongWenjie on 2019-01-28 14:57
 */

package org.zhong.zschedule.core.executor;

import org.quartz.CronExpression;
import org.zhong.zschedule.core.Util;
import org.zhong.zschedule.core.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.core.task.QueryTask;
import org.zhong.zschedule.core.task.VariableQueryTask;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CronQueryTaskExecutor extends DefaultQueryTaskVariableExecutor {

    public CronQueryTaskExecutor(String cron,
                                 QueryTaskExecutorConfig queryTaskExecutorConfig,
                                 ScheduledExecutorServiceLoader scheduledExecutorServiceLoader,
                                 QueryTask queryTask) {
        super(queryTaskExecutorConfig, scheduledExecutorServiceLoader);
        this.setVariableQueryTask(new CronQueryTask(cron, queryTask));
    }

    public CronQueryTaskExecutor(String cron,
                                 ScheduledExecutorServiceLoader scheduledExecutorServiceLoader,
                                 QueryTask queryTask) {
        super(new QueryTaskExecutorConfig() {
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
        }, scheduledExecutorServiceLoader);
        this.setVariableQueryTask(new CronQueryTask(cron, queryTask));
    }

    public CronQueryTaskExecutor(String cron, long timeout, TimeUnit timeUnit, ScheduledExecutorServiceLoader loader, QueryTask queryTask) {
        super(new QueryTaskExecutorConfig() {
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
        },loader);
        this.setVariableQueryTask(new CronQueryTask(cron, queryTask));
    }

    private class CronQueryTask implements VariableQueryTask {
        private String cron;
        private QueryTask queryTask;

        private CronQueryTask(String cron, QueryTask queryTask) {
            this.cron = cron;
            this.queryTask = queryTask;
        }

        @Override
        public long nextDelay(Map<String, Object> queryParams) {
            CronExpression expression = null;
            try {
                expression = new CronExpression(cron);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (Util.valid(expression)) {
                long currentTimeMillis = System.currentTimeMillis();
                return expression.getNextValidTimeAfter(new Date(currentTimeMillis)).getTime() - currentTimeMillis;
            }
            return 0;
        }

        @Override
        public boolean doQuery(Map<String, Object> queryParams) {
            return queryTask.doQuery(queryParams);
        }

        @Override
        public boolean doStop(Map<String, Object> queryParams) {
            return queryTask.doStop(queryParams);
        }

        @Override
        public boolean doTimeoutStop(Map<String, Object> queryParams) {
            return queryTask.doTimeoutStop(queryParams);
        }
    }

}
