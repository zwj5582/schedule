/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.core.executor;

import org.zhong.zschedule.core.Util;
import org.zhong.zschedule.core.common.MonitorObject;
import org.zhong.zschedule.core.task.QueryTask;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

final class QueryTaskAware implements Runnable {

    private Map<Object, Future> futureMap;

    private final Object monitor;

    private QueryTask queryTask;

    private Map<String, Object> queryParams;

    private volatile long timeoutTime;

    private Future currFuture;

    private volatile boolean running = true;

    private volatile long beginTime = 0;

    QueryTaskAware(Map<Object, Future> futureMap, MonitorObject monitorObject, QueryTask queryTask, Map<String, Object> queryParams, Long timeout, TimeUnit timeUnit) {
        this.futureMap = futureMap;
        this.monitor = monitorObject.getSource();
        this.queryTask = queryTask;
        this.queryParams = queryParams;
        this.timeoutTime = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
    }

    QueryTaskAware(Map<Object, Future> futureMap, MonitorObject monitorObject, QueryTask queryTask, Map<String, Object> queryParams) {
        this.futureMap = futureMap;
        this.monitor = monitorObject.getSource();
        this.queryTask = queryTask;
        this.queryParams = queryParams;
        this.timeoutTime = Long.MAX_VALUE;
    }

    @Override
    public void run() {
        while (!Util.valid(currFuture)) {
            synchronized (monitor) {
                if (!futureMap.containsKey(monitor)) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                currFuture = futureMap.remove(monitor);
            }
        }
        if (timeoutTime != Long.MAX_VALUE && beginTime == 0) beginTime = System.currentTimeMillis();
        doTask();
    }


    private void doTask() {
        Boolean timeout = ((beginTime + timeoutTime) < System.currentTimeMillis());
        if (timeout) {
            running = queryTask.doTimeoutStop(queryParams);
            if (!running) currFuture.cancel(false);
        } else {
            if (!running) {
                running = queryTask.doStop(queryParams);
                if (!running) currFuture.cancel(false);
            }
            if (running) running = queryTask.doQuery(queryParams);
        }
    }


}
