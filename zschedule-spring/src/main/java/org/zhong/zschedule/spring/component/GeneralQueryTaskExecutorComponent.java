/*
 *  Created by ZhongWenjie on 2019-01-25 17:43
 */

package org.zhong.zschedule.spring.component;


import org.zhong.zschedule.core.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.spring.loader.DefaultScheduledExecutorServiceLoader;
import org.zhong.zschedule.core.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.core.task.QueryTask;
import org.zhong.zschedule.spring.task.DefaultQueryTask;

public abstract class GeneralQueryTaskExecutorComponent implements QueryTaskExecutorComponent {

    public abstract String getId();

    @Override
    public Class<? extends QueryTaskExecutorConfig> getQueryTaskExecutorConfigClass() {
        return QueryTaskExecutorConfig.class;
    }

    @Override
    public Class<? extends ScheduledExecutorServiceLoader> getScheduledExecutorServiceLoaderClass() {
        return DefaultScheduledExecutorServiceLoader.class;
    }

    @Override
    public Class<? extends QueryTask> getQueryTaskClass() {
        return DefaultQueryTask.class;
    }
    
}
