/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.spring.loader;

import org.springframework.stereotype.Component;
import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class DefaultScheduledExecutorServiceLoader implements ScheduledExecutorServiceLoader {

    @Override
    public ScheduledExecutorService getScheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
