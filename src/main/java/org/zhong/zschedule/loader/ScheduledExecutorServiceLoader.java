/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.loader;

import java.util.concurrent.ScheduledExecutorService;

public interface ScheduledExecutorServiceLoader {

    ScheduledExecutorService getScheduledExecutorService();

}
