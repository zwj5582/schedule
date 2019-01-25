/*
 *  Created by ZhongWenjie on 2019-01-25 9:46
 */

package org.zhong.zschedule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DefaultQueryTaskExecutorConfig implements QueryTaskExecutorConfig  {

    @Value("${query.default.task.delay:10}")
    private Long delay = 10L;

    @Value("${query.default.task.timeout:10000}")
    private Long timeout;

    @Value("${query.default.task.timeout.enable:false}")
    private Boolean needTimeout;

    @Autowired
    private TimeUnit timeUnit;

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
        return needTimeout;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
