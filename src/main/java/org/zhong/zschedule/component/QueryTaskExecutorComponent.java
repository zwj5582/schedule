/*
 *  Created by ZhongWenjie on 2019-01-25 15:36
 */

package org.zhong.zschedule.component;

import org.zhong.zschedule.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.task.QueryTask;

public interface QueryTaskExecutorComponent {

    String getId();

    Class<? extends QueryTaskExecutorConfig> getQueryTaskExecutorConfigClass();

    Class<? extends ScheduledExecutorServiceLoader> getScheduledExecutorServiceLoaderClass();

    Class<? extends QueryTask> getQueryTaskClass();
}
