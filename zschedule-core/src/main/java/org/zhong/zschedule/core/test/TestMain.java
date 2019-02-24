/*
 *  Created by ZhongWenjie on 2019-02-23 15:41
 */

package org.zhong.zschedule.core.test;

import com.google.common.collect.Maps;
import org.zhong.zschedule.core.executor.CronQueryTaskExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class TestMain {

    public static void main(String[] args) throws InterruptedException {
        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);
        AtomicLong atomicLong = new AtomicLong(0);
        CronQueryTaskExecutor cronQueryTaskExecutor = new CronQueryTaskExecutor(
                "*/5 * * * * ?",
                60,
                TimeUnit.SECONDS,
                () -> executorService,
                (queryParams) -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "============= " + atomicLong.getAndIncrement());
                    return true;
                }
        );
        LongStream.range(1,100).forEach(
                value -> cronQueryTaskExecutor.submit(Maps.newHashMap())
        );

        cronQueryTaskExecutor.wait();

    }
}
