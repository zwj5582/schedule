/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.core.task;

import java.util.Map;

@FunctionalInterface
public interface QueryTask {

    boolean doQuery(Map<String, Object> queryParams);

    default boolean doStop(Map<String, Object> queryParams) {
        return false;
    }

    default boolean doTimeoutStop(Map<String, Object> queryParams) {
        return false;
    }

}
