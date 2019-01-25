/*
 *  Created by ZhongWenjie on 2019-01-25 17:49
 */

package org.zhong.zschedule.component;

import org.zhong.zschedule.register.QueryTaskExecutorRegister;

public class DefaultQueryTaskExecutorComponent extends GeneralQueryTaskExecutorComponent {

    @Override
    public String getId() {
        return QueryTaskExecutorRegister.DEFAULT;
    }

}
