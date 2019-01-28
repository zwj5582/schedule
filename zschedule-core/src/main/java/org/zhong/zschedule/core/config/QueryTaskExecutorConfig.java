/*
 *  Created by ZhongWenjie on 2019-01-25 9:43
 */

package org.zhong.zschedule.core.config;

import java.util.concurrent.TimeUnit;

public interface QueryTaskExecutorConfig {

     Long getDelay();

     Long getTimeout();

     Boolean getNeedTimeout();

     TimeUnit getTimeUnit();

}
