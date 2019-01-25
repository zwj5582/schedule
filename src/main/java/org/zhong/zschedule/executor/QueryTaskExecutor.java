/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.executor;

import java.util.Map;

public interface QueryTaskExecutor {

    void submit(Map<String, Object> queryParams);

}
