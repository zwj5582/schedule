/*
 *  Created by ZhongWenjie on 2019-01-25 17:43
 */

package org.zhong.zschedule.component;

import com.meliora.manage120.web.struts.schedule.config.QueryTaskExecutorConfig;
import com.meliora.manage120.web.struts.schedule.loader.DefaultScheduledExecutorServiceLoader;
import com.meliora.manage120.web.struts.schedule.loader.ScheduledExecutorServiceLoader;
import com.meliora.manage120.web.struts.schedule.task.DefaultQueryTask;
import com.meliora.manage120.web.struts.schedule.task.QueryTask;

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
