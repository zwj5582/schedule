/*
 *  Created by ZhongWenjie on 2019-01-28 13:56
 */

package org.zhong.zschedule.core.executor;

import java.util.Map;

public interface Variable {

    long nextDelay(Map<String, Object> queryParams);

}
