/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.task;

import java.util.Map;

public interface QueryTask {

    boolean doQuery(Map<String, Object> queryParams, Map<String, Object> context);

    boolean doStop(Map<String, Object> queryParams, Map<String, Object> context);

}
