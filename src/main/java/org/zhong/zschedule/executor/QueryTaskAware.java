/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.executor;

import com.google.common.collect.Maps;
import org.zhong.zschedule.task.QueryTask;

import java.util.Map;
import java.util.concurrent.ExecutorService;

class QueryTaskAware implements Runnable {

    private volatile boolean running = true;

    private volatile Boolean timeout = false;

    private ExecutorService executor;

    private QueryTask queryTask;

    private Map<String, Object> queryParams;

    private long timeoutTime = Long.MAX_VALUE;

    private Map<String,Object> context = Maps.newConcurrentMap();

    QueryTaskAware(ExecutorService executor, QueryTask queryTask, Map<String, Object> queryParams, long timeoutTime) {
        this.executor = executor;
        this.queryTask = queryTask;
        this.queryParams = queryParams;
        this.timeoutTime = timeoutTime;
    }

    QueryTaskAware(ExecutorService executor, QueryTask queryTask, Map<String, Object> queryParams) {
        this.executor = executor;
        this.queryTask = queryTask;
        this.queryParams = queryParams;
    }

    @Override
    public void run() {
        timeout = ( timeoutTime < System.currentTimeMillis() );
        if (timeout) {
            running = queryTask.doStop(queryParams, context);
            if (!running) executor.shutdown();
        }
        else {
            if (!running) executor.shutdown();
            if (running) running = queryTask.doQuery(queryParams, context);
        }
    }



}
