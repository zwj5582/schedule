/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.core.loader;

import java.util.concurrent.ScheduledExecutorService;

@FunctionalInterface
public interface ScheduledExecutorServiceLoader {

    ScheduledExecutorService getScheduledExecutorService();

}
