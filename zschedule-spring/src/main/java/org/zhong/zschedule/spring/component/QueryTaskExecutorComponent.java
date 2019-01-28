/*
 *  Created by ZhongWenjie on 2019-01-25 15:36
 */

package org.zhong.zschedule.spring.component;

import org.zhong.zschedule.core.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.core.task.QueryTask;

public interface QueryTaskExecutorComponent {

    String getId();

    Class<? extends QueryTaskExecutorConfig> getQueryTaskExecutorConfigClass();

    Class<? extends ScheduledExecutorServiceLoader> getScheduledExecutorServiceLoaderClass();

    Class<? extends QueryTask> getQueryTaskClass();
}
