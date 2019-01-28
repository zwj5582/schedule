/*
 *  Created by ZhongWenjie on 2019-01-25 11:25
 */

package org.zhong.zschedule.spring.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.FieldRetrievingFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TimeUnitFieldRetrievingFactoryBean extends FieldRetrievingFactoryBean {

    @Value("${query.default.task.timeUnit:SECONDS}")
    private String field;

    @PostConstruct
    public void init(){
        setStaticField(field);
    }

    public void setStaticField(String staticField) {
        super.setStaticField("java.util.concurrent.TimeUnit." + org.springframework.util.StringUtils.trimAllWhitespace(staticField.toUpperCase()));
    }

}
