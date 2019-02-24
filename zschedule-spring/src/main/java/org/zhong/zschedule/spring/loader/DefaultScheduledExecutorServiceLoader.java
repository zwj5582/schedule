/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.spring.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zhong.zschedule.core.Util;
import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class DefaultScheduledExecutorServiceLoader implements ScheduledExecutorServiceLoader {

    private ScheduledExecutorService scheduledExecutorService;

    @Value("${z.schedule.corePoolSize}")
    private Integer corePoolSize;

    @PostConstruct
    public void init() {
        if (!Util.valid(corePoolSize))
            corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
    }

    @Override
    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
