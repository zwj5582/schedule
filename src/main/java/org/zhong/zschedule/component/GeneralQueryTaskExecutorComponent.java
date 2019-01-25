/*
 *  Created by ZhongWenjie on 2019-01-25 17:43
 */

package org.zhong.zschedule.component;


import org.zhong.zschedule.config.QueryTaskExecutorConfig;
import org.zhong.zschedule.loader.DefaultScheduledExecutorServiceLoader;
import org.zhong.zschedule.loader.ScheduledExecutorServiceLoader;
import org.zhong.zschedule.task.DefaultQueryTask;
import org.zhong.zschedule.task.QueryTask;

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
