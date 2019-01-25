/*
 *  Created by ZhongWenjie on 2019-01-25 15:36
 */

package org.zhong.zschedule.component;

import com.meliora.manage120.web.struts.schedule.config.QueryTaskExecutorConfig;
import com.meliora.manage120.web.struts.schedule.loader.ScheduledExecutorServiceLoader;
import com.meliora.manage120.web.struts.schedule.task.QueryTask;

public interface QueryTaskExecutorComponent {

    String getId();

    Class<? extends QueryTaskExecutorConfig> getQueryTaskExecutorConfigClass();

    Class<? extends ScheduledExecutorServiceLoader> getScheduledExecutorServiceLoaderClass();

    Class<? extends QueryTask> getQueryTaskClass();
}
