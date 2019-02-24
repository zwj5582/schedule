/*
 *  Created by ZhongWenjie on 2019-01-28 14:09
 */

package org.zhong.zschedule.core.executor;

import org.zhong.zschedule.core.task.VariableQueryTask;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

final class VariableQueryTaskAware implements Runnable {

    private ScheduledExecutorService scheduledExecutorService;

    private VariableQueryTask variableQueryTask;

    private Map<String, Object> queryParams;

    private volatile long timeoutTime;

    private volatile boolean running = true;

    private volatile long beginTime = 0;

    VariableQueryTaskAware(ScheduledExecutorService scheduledExecutorService, VariableQueryTask variableQueryTask, Map<String, Object> queryParams, TimeUnit timeUnit ,Long timeout) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.variableQueryTask = variableQueryTask;
        this.queryParams = queryParams;
        this.timeoutTime = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
    }

    VariableQueryTaskAware(ScheduledExecutorService scheduledExecutorService, VariableQueryTask variableQueryTask, Map<String, Object> queryParams) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.variableQueryTask = variableQueryTask;
        this.queryParams = queryParams;
        this.timeoutTime = Long.MAX_VALUE;
    }

    @Override
    public void run() {
        if (timeoutTime != Long.MAX_VALUE && beginTime == 0) beginTime = System.currentTimeMillis();
        Boolean timeout = ((beginTime + timeoutTime) < System.currentTimeMillis());
        if (timeout) {
            running = variableQueryTask.doTimeoutStop(queryParams);
        } else {
            if (running) running = variableQueryTask.doQuery(queryParams);
        }
        if ( !timeout && running ) {
            long nextDelay = variableQueryTask.nextDelay(queryParams);
            if (nextDelay != 0)
                scheduledExecutorService.schedule(this, nextDelay, TimeUnit.MILLISECONDS);
            else {
                variableQueryTask.doStop(queryParams);
            }
        }else {
            variableQueryTask.doStop(queryParams);
        }
    }

}
