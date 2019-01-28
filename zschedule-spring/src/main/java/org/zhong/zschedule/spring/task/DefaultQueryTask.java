/*
 *  Created by ZhongWenjie on 2019-01-24 15:22
 */

package org.zhong.zschedule.spring.task;

import org.springframework.stereotype.Component;
import org.zhong.zschedule.core.task.QueryTask;

import java.util.Map;

@Component
public class DefaultQueryTask implements QueryTask {

    @Override
    public boolean doQuery(Map<String, Object> queryParams) {
        // do something
        return false;
    }

    @Override
    public boolean doStop(Map<String, Object> queryParams) {
        // do something
        return false;
    }

    @Override
    public boolean doTimeoutStop(Map<String, Object> queryParams) {
        // do something
        return false;
    }
}
